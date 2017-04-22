# -*- coding:utf-8 -*-
from ProcessData import  ProcessData
import Global

if __name__=='__main__':
    print "正在连接服务端并向服务端发送数据======>"
    process=ProcessData(Global.QUEUE)
    process.start()