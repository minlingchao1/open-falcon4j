define(function (require, exports, module){
    var $=require("jquery");

    var socket=require("socket");



    var Global={

        s4:function () {
            return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
        },
        //产生唯一id
        guid:function () {
            return (Global.s4()+Global.s4()+"-"+Global.s4()+"-"+Global.s4()+"-"+Global.s4()+"-"+Global.s4()+Global.s4()+Global.s4());
        },

        /**
         *
         * @returns {*}
         */
        connectWebsocket:function (url,msg,id,fun) {
            var sock=new SockJS(url);

            //开启
            sock.onopen=function () {
                sock.send(msg);
               // console.log("open")
            };

            //接收消息处理
            sock.onmessage=function (e) {
               // console.log(e);
               fun(id,e.data);
            };

            // //关闭
            sock.onclose=function () {
                console.log("close");
            };


            return sock;
        },
        createJsonObject:function (name,value) {

           var jsonArr=[];

            if (typeof value == 'string'){
                jsonArr.push("\""+name+"\":"+"\""+value+"\"");
            }else{
                jsonArr.push("\""+name+"\":"+value);
            }
            var str = "{"+jsonArr.join(',')+"}";
            return $.parseJSON(str);
        },

        getMeessageType:function (messageType) {

            var MessageType={
                "upload":1,
                "showLog":2,
                "start":3,
                "stop":4,
                "deploy":5
            }

            return MessageType[messageType]
        },

        getDealStatus:function (status) {
            var DealStatus={
                1:"upload_success",
                2:"deal_exception",
                3:"wait_deal",
                4:"file_exist"
            }

            return DealStatus[status];
        }
    }

    module.exports=Global;
})