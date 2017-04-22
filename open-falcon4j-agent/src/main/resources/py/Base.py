# -*- coding:utf-8 -*-
import time
class Base:
    def getTime(self):
        '''
        获取时间戳
        :return: 
        '''
        return str(time.time()).split('.')[0]