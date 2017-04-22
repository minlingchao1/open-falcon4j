# -*- coding:utf-8 -*-
from Base import Base
import urllib
import urllib2
import json


class SendData(Base):

    def sendData(self,data=None):
        '''
        发送数据
        '''
        url="http://114.55.15.198:30003/monitor/receiveMonitorMsg"

        if data is None:
            return False

        try:
            data=urllib.urlencode(data)
            req=urllib2.Request(url,data)
            res=urllib2.urlopen(req)
        except Exception as e:
            return False



