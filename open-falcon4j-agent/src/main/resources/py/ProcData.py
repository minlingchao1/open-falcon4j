# -*- coding:utf-8 -*-
from Base import Base
from collections import OrderedDict
import commands
import socket


class ProcData(Base):
    '''
    监控服务器各项数据
    '''

    proc_dir="/proc/"

    def __init__(self):
        pass

    def getCpuInfo(self):
        '''
        获取cpu核心数量以及型号
        :return: 
        '''
        cpu_info=OrderedDict()
        cpu_num=0
        cpu_info['cpu0']=OrderedDict()

        #获取cpu信息
        try:
            with open(self.proc_dir+"cpuinfo")as fp:
                for line in fp:
                    if line.strip():
                        tmp=line.split(":")
                        if len(tmp)==2:
                            cpu_info['cpu'+str(cpu_num)][tmp[0].strip()]=tmp[1].strip()
                        else:
                            if line=="\n":
                                cpu_num+=1
                                cpu_info['cpu'+str(cpu_num)]
        except Exception as e:
            return False

        #提取module name

        model_name=[]

        for numKey in cpu_info.keys():
            for infoKey in cpu_info[numKey].keys():
                if infoKey=='entity name':
                    model_name.append(cpu_info[numKey])
        return model_name

    def getLoadAvg(self):
        '''
        获取负载均衡
        :return: 
        '''
        try:
            with open(self.proc_dir+"loadavg") as fp:
                load_avg=fp.read()
        except Exception as e:
            return False
        return load_avg.split("\n")[0].split(' ')

    def getMemInfo(self):
        '''
        获取服务器内存使用情况
        :return: 
        '''
        mem={}
        mem_info={}
        try:
            with open(self.proc_dir+"meminfo") as fp:
                for line in fp:
                    if line.strip():
                        tmp=line.split(':')
                        if len(tmp)==2:
                            mem[tmp[0].strip()]=tmp[1].strip()
                        else:
                            mem[tmp[0].strip()]=''
        except Exception as e:
            return False

        mem_info['MemTotal'] = mem['MemTotal']
        mem_info['MemFree'] = mem['MemFree']
        mem_info['Buffers'] = mem['Buffers']
        mem_info['Cached'] = mem['Cached']
        mem_info['﻿SwapCached'] = mem['SwapCached']
        mem_info['use'] = str(int(mem['MemTotal'].split(' ')[0]) - int(mem['MemFree'].split(' ')[0])) + ' kB'
        return mem_info

    def getMemUsage(self):

        '''
        获取内存使用率
        :return: 
        '''
        mem_info=self.getMemInfo()
        if mem_info:
            useage = float(mem_info['use'].split(' ')[0]) / float(mem_info['MemTotal'].split(' ')[0])
            return useage
        return False

    def getNetIO(self):
        '''
        获取网络IO信息
        :return: 
        '''
        try:
            with open(self.proc_dir+"net/dev") as fp:
                for line in fp:
                    if 'eth0' in line:
                        data=line.split(' ')
        except Exception as e:
            return  False

        while '' in data:
            data.remove('')

        # 换算成MB
        in_net = round(float(data[1])/1024/1024, 3)
        out_net = round(float(data[9])/1024/1024, 3)

        net_data = {}
        net_data['in_net'] = in_net
        net_data['out_net'] = out_net
        return net_data

    def getStat(self):
        '''
        获取CPU利用率,该方法需要两次采样,并计算利用率
        idle为列表中第四个元素
        公式:usage=(idle2-idle1)/(cpu2-cpu1)*100
        :return:
        '''
        try:
            with open(self.proc_dir + 'stat') as fp:
                data = fp.read().split('\n')
        except Exception as e:
            return False

        cpuData = {}
        idle = {}
        for line in data:
            if 'cpu' in line:
                tmp = line.split(' ')
                # print tmp
                while '' in tmp:
                    tmp.remove('')
                cpuData[tmp[0]] = tmp[1:]
                idle[tmp[0]] = tmp[4]
        cpuTotalTime = {}
        for key in cpuData.keys():
            total = 0
            for num in cpuData[key]:
                total += int(num)
            cpuTotalTime[key] = total
        data = {'idle':idle, 'total':cpuTotalTime}
        return data

    def getDiskInfo(self):
        '''
        获取磁盘使用信息
        :return: 
        '''
        disks=OrderedDict()
        free=commands.getoutput('df -h|grep dev|egrep -v "tmp|var|shm"')
        list=free.split('\n')


        for line in list:
            tmp=line.split(' ')
            disks[tmp[0]]=OrderedDict()
            disks[tmp[0]]["total"]=tmp[8]
            disks[tmp[0]]["used"]=tmp[10]
            disks[tmp[0]]["left"]=tmp[13]
            disks[tmp[0]]["useper"]=tmp[15]
        return disks

    def getHostName(self):
        '''
        获取计算机名称
        :return: 
        '''
        hostname=socket.getfqdn(socket.gethostname())
        return hostname



