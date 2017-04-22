$(function () {
   Action.init()
})

var Action={
    init:function () {
        Action.getAllUserGroup()
        Action.bind()
    },
    bind:function () {
        $("#confirmAddAction").click(Action.addAction)
        $("#confirmModifyAction").click(Action.modifyAction)
        $(".modify").click(Action.showModifyModal)
        $(".del").click(Action.delAction)
    },
    showModifyModal:function () {
        var name=$(this).attr("name");
        var url=$(this).attr("url");
        var afterCallbackmail=$(this).attr("afterCallbackmail")=="true"?1:0;
        var afterCallbackSms=$(this).attr("afterCallbackSms")=="true"?1:0;
        var beforeCallbackMail=$(this).attr("beforeCallbackMail")=="true"?1:0;
        var beforeCallbackSms=$(this).attr("beforeCallbackSms")=="true"?1:0;
        var callback=$(this).attr("callback")=="true"?1:0;
        var id=$(this).attr("action")

        var usrGrpId=$(this).attr("userGrpId");

        $("#name").val(name);
        $("#url").val(url);
        $("#afterCallbackmail").val(afterCallbackmail).trigger("change")
        $("#afterCallbackSms").val(afterCallbackSms).trigger("change")
        $("#beforeCallbackMail").val(beforeCallbackMail).trigger("change")
        $("#beforeCallbackSms").val(beforeCallbackSms).trigger("change")
        $("#callback").val(callback).trigger("change");
        $("#userGrpId").val(usrGrpId).trigger("change");
        $("#id").val(id)
        $("#modify-action").modal("show");

    },
    delAction:function () {

        var id=$(this).attr("action")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/action/delete",
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
    addAction:function () {
        var name=$("#add-name").val()
        var url=$("#add-url").val()
        var callback=$("#add-callback").val()
        var afterCallbackmail=$("#add-afterCallbackmail").val();
        var afterCallbackSms=$("#add-afterCallbackSms").val()
        var beforeCallbackMail=$("#add-beforeCallbackMail").val()
        var beforeCallbackSms=$("#add-beforeCallbackSms").val()
        var usrGrpId=$("#add-userGrpId").val();

        if(name==""){
            swal({
                     title: "请输入名称",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/action/add",
                   dataType:"json",
                   data:{
                       name:name,
                       userGrpId:usrGrpId,
                       url:url,
                       callback:callback,
                       beforeCallbackSms:beforeCallbackSms,
                       beforeCallbackMail:beforeCallbackMail,
                       afterCallbackSms:afterCallbackSms,
                       afterCallbackmail:afterCallbackmail
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-action").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-action").modal("hide");
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
    modifyAction:function () {
        var name=$("#name").val()
        var url=$("#url").val()
        var callback=$("#callback").val()
        var afterCallbackmail=$("#afterCallbackmail").val();
        var afterCallbackSms=$("#afterCallbackSms").val()
        var beforeCallbackMail=$("#beforeCallbackMail").val()
        var beforeCallbackSms=$("#beforeCallbackSms").val()
        var usrGrpId=$("#userGrpId").val()
        var id=$("#id").val();


        if(name==""){
            swal({
                     title: "请输入名称",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/action/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       name:name,
                       userGrpId:usrGrpId,
                       url:url,
                       callback:callback,
                       beforeCallbackSms:beforeCallbackSms,
                       beforeCallbackMail:beforeCallbackMail,
                       afterCallbackSms:afterCallbackSms,
                       afterCallbackmail:afterCallbackmail
                   },
                   success:function (data) {
                       if(data.success){
                           $("#modify-action").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#modify-action").modal("hide");
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
    getAllUserGroup:function () {
        $.ajax({
                   type:"get",
                   url:"/usergroup/getAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#add-userGrpId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择用户组',

                           })
                           $("#userGrpId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择用户组',

                           })
                       }
                   }
               })
    }
}