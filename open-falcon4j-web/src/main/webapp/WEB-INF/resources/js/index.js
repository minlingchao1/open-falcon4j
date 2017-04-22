
$(function () {

    //getMenuList()
    //初始化控件
    var app=new App();
    app.init();
    bootbox.setDefaults("locale","zh_CN");

    $(".select").select2();

    getUserInfo();

    //路由
    // $(".subMenu").on("click",function () {
    //     $("#ifr").attr("src",$(this).find("a").attr("url"));
    // })

})

function getUserInfo() {
    $.ajax({
       type: "get",
       url: "/user/getUserInfo",
       dataType: "json",

       success: function (data) {
           if(data.result){
               $(".userNick").text(data.result.nick);
               $(".figureUrl").attr("src",data.result.figureUrl)
           }

       }
   })
}

//菜单
function getMenuList() {
    $.ajax({
               async:false,
               type: "get",
               url: "/menu/list",
               dataType: "json",
               success: function(data) {
                   if(data.result){
                       var html=[];
                       $.each(data.result,function (i,v) {
                           html.push('<li class="headerMenu">');

                           html.push(' <a href="#"><span>'+v.name+'</span></a>');

                           if(v.children.list){
                               html.push('<ul>');
                               $.each(v.children.list,function (k,l) {
                                   html.push('<li class="subMenu" >')
                                   html.push('<a href="'+l.url+'">');
                                   html.push('<span>'+l.name+'</span>');
                                   html.push('</a>');
                                   html.push('</li>');
                               })
                               html.push('</ul>');
                           }

                           html.push('</li>');
                       })
                       console.log(html.join(""))
                       $("#side-menu").append(html.join(""));
                   }
               }
           });
}