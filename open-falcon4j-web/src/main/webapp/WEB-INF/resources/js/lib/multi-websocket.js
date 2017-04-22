function MultiWebsocket() {
    this.websockets=[];
}

MultiWebsocket.prototype.addWebSocket=function (key,websocket) {
    var obj={
        key:key,
        websocket:websocket
    }
    this.websockets.push(obj);
}

MultiWebsocket.prototype.disconnect=function (key) {

    var l=this.websockets.length;

    for (var i=0;i<l;i++){
        if(this.websockets[i]["key"]==key){
            var  websocket=this.websockets[i]["websocket"];

            //console.log(websocket)
            websocket.close();
        }
    }

}