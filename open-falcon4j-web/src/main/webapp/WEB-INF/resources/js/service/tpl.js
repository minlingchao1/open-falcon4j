$(function () {

    Tpl.init()

})

var Tpl={
    init:function () {
        Tpl.getAllAction();
        Tpl.getAllTpl();
        Tpl.bind();
    },
    bind:function () {
        $("#confirmAddTpl").click(Tpl.addTpl)
        $("#confirmModifyTpl").click(Tpl.modifyTpl)
        $(".modify").click(Tpl.showModifyModel)
        $(".del").click(Tpl.delTpl)
    },
    showModifyModel:function () {
        var name=$(this).attr("name")
        var parentId=$(this).attr("parentId")
        var actionId=$(this).attr("action")
        var id=$(this).attr("tpl")

        $("#id").val(id);
        $("#name").val(name);
        $("#parentId").val(parentId).trigger("change")
        $("#actionId").val(actionId).trigger("change")

        $("#modify-tpl").modal("show")

    },
    delTpl:function () {
        var id=$(this).attr("tpl")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/tpl/delete",
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
    addTpl:function () {
        var name=$("#add-name").val()
        var parentId=$("#add-parentId").val()
        var actionId=$("#add-actionId").val()

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
                   url:"/tpl/add",
                   dataType:"json",
                   data:{
                       tplName:name,
                       parentId:parentId,
                       actionId:actionId
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-tpl").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-tpl").modal("hide");
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
    modifyTpl:function () {
        var id=$("#id").val()
        var name=$("#name").val()
        var parentId=$("#parentId").val()
        var actionId=$("#actionId").val()


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
                   url:"/tpl/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       tplName:name,
                       parentId:parentId,
                       actionId:actionId
                   },
                   success:function (data) {
                       if(data.success){
                           $("#modify-tpl").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#modify-tpl").modal("hide");
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
    getAllTpl:function () {
        $.ajax({
                   type:"get",
                   url:"/tpl/getAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#parentId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择父模版',

                           })
                           $("#add-parentId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择父模版',

                           })
                       }
                   }
               })
    },
    getAllAction:function () {
        $.ajax({
                   type:"get",
                   url:"/action/getAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#actionId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择回调事件',

                           })
                           $("#add-actionId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择回调事件',

                           })
                       }
                   }
               })
    },

}