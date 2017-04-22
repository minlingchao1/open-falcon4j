$(function () {
   Host.init()
})

var Host = {
    init: function () {
        Host.bind()
    },
    bind: function () {

        $("#confirmAddHost").click(Host.addHost)
        $("#confirmModifyHost").click(Host.modifyHost)
        $(".modify").click(Host.showModifyModal)
        $(".del").click(Host.delHost)

    },
    showModifyModal: function () {
        var name = $(this).attr("name");
        var id = $(this).attr("host");

        $("#name").val(name);
        $("#id").val(id)
        $("#modify-host").modal("show")
    },
    delHost: function () {
        var id = $(this).attr("host")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/host/delete",
                           dataType:"json",
                           data:{
                               id:id,
                           },
                           success:function (data) {
                               if(data.success){
                                   swal({
                                            title: "删除成功",
                                            confirmButtonColor: "#66BB6A",
                                            type: "success"
                                        });
                                   window.location.reload();
                               }else {
                                   swal({
                                            title: "删除失败",
                                            text:data.msg,
                                            confirmButtonColor: "#EF5350",
                                            type: "error"
                                        });
                               }
                           }
                       })
            }
        });
    },
    addHost: function () {
        var name=$("#add-name").val()

        if(name==""){
            swal({
                     title: "请输入Host名称",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/host/add",
                   dataType:"json",
                   data:{
                       hostName:name,
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-host").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-host").modal("hide");
                           swal({
                                    title: "修改失败",
                                    text:data.msg,
                                    confirmButtonColor: "#EF5350",
                                    type: "error"
                                });
                       }
                   }
               })


    },
    modifyHost: function () {
        var name=$("#name").val();
        var id=$("#id").val();

        if(name==""){
            swal({
                     title: "请输入Host名称",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
           type:"post",
           url:"/host/modify",
           dataType:"json",
           data:{
               id:id,
               hostName:name,
           },
           success:function (data) {
               if(data.success){
                   $("#modify-host").modal("hide");
                   swal({
                            title: "修改成功",
                            confirmButtonColor: "#66BB6A",
                            type: "success"
                        });

                   window.location.reload();
               }else {
                   $("#modify-host").modal("hide");
                   swal({
                            title: "修改失败",
                            text:data.msg,
                            confirmButtonColor: "#EF5350",
                            type: "error"
                        });
               }
           }
       })
    }

}