# -*- coding:utf-8 -*-
from Base import Base
from ProcData import ProcData
from SendData import SendData
from MetricValue import MetricValue
from MetricType import MetricType
import Queue,json,threading,time


class ProcessData(Base):
    '''
    数据处理类
    '''
    number=5
    sleep=60
    endPoint=""
    exception = ['__init__', 'number', 'sleep', 'exception', '__doc__', '__module__','start']

    def __init__(self,queue=Queue.Queue):
        self.dataQueue=queue
        self.proc=ProcData()
        self.endPoint=self.proc.getHostName()

    def putLoadAvg(self):
        '''
        load avg put into queue
        :return: 
        '''
        while True:
            load=self.proc.getLoadAvg()
            loadData=MetricValue(self.endPoint,MetricType.LOAD_AVG,load[0],None,self.getTime())
            self.dataQueue.put_nowait(loadData.convert2Dict(loadData))
            time.sleep(self.sleep)

    def puyMemUsage(self):
        '''
        mem usage put into queue
        :return: 
        '''
        while True:
            memUsage=self.proc.getMemUsage()
            memData=MetricValue(self.endPoint,MetricType.MEM,memUsage,None,self.getTime())
            self.dataQueue.put_nowait(memData.convert2Dict(memData))
            time.sleep(self.sleep)

    def putNetIO(self):
        '''
        net io put into queue
        :return: 
        '''
        while True:
            in_net=[]
            out_net=[]
            netIO=self.proc.getNetIO()

            inNetData=MetricValue(self.endPoint,MetricType.NET_IO,netIO['in_net'],"net=in_net",self.getTime())
            outNetData=MetricValue(self.endPoint,MetricType.NET_IO,netIO['out_net'],"net=out_net",self.getTime())

            self.dataQueue.put_nowait(inNetData.convert2Dict(inNetData))
            self.dataQueue.put_nowait(outNetData.convert2Dict(outNetData))

            time.sleep(self.sleep)

    def putStat(self):
        '''
        stat put into queue
        :return: 
        '''
        while True:
            stat1 = self.proc.getStat()
            time.sleep(0.1)
            stat2 = self.proc.getStat()
            time.sleep(0.1)
            usage = (float(stat2['idle']['cpu']) - float(stat1['idle']['cpu'])) / (float(stat2['total']['cpu']) - float(stat1['total']['cpu']))

            usageData=MetricValue(self.endPoint,MetricType.CPU_USAGE,usage,None,self.getTime())
            self.dataQueue.put_nowait(usageData.convert2Dict(usageData))
            time.sleep(self.sleep)

    def putDiskInfo(self):

        while True:
            diskData=self.proc.getDiskInfo()

            for k,v in diskData.items():
                tmp=MetricValue(self.endPoint,MetricType.DISK_INFO,diskData[k]['useper'],k,self.getTime())
                self.dataQueue.put_nowait(tmp.convert2Dict(tmp))

            time.sleep(self.sleep)

    def get(self):
        '''
        read queue
        :return: 
        '''
        send=SendData()
        while True:
            data=self.dataQueue.get()
            data=json.dumps(data)
            params={"msg":data}
            send.sendData(params)

    def start(self):
        dict=self.__class__.__dict__

        for function in dict:
            if function not  in self.exception:
                target=function
                t=threading.Thread(target=getattr(self,target))
                t.start()
