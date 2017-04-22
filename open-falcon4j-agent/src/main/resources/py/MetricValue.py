# -*- coding:utf-8 -*-
class MetricValue(object):
    endPoint=""
    metric=""
    value=""
    tag=""
    timestamp=0

    def __init__(self,endPoint,metric,value,tag,timestamp):
        self.endPoint=endPoint
        self.metric=metric
        self.value=value
        self.tag=tag
        self.timestamp=timestamp

    def convert2Dict(self,obj):
        dict={}
        dict.update(obj.__dict__)
        return dict

    def convert2Dicts(self,obj):
        obj_arr=[]

        for o in obj:
            dict={}
            dict.update(o.__dict__)
            obj_arr.append(dict)

        return obj_arr

