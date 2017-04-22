$(function () {
    HostGroup.init();
})

var HostGroup = {
    init: function () {
        HostGroup.getAllTpl()
        HostGroup.getAllHost()
        HostGroup.bind();
    },
    bind: function () {
        $("#confirmAddHostGroup").click(HostGroup.addHostGroup)
        $("#confirmModifyHostGroup").click(HostGroup.modifyHostGroup)
        $("#confirmBindTpl").click(HostGroup.bindTpl)
        $("#confirmBindHost").click(HostGroup.bindHost)
        $(".modify").click(HostGroup.showModifyModal)
        $(".del").click(HostGroup.delHostGroup)
        $(".bindHost").click(HostGroup.showBindHostModal)
        $(".bindTpl").click(HostGroup.showBindTplModal)
    },
    showModifyModal: function () {
        var name = $(this).attr("name");
        var id = $(this).attr("host");

        $("#name").val(name);
        $("#id").val(id)
        $("#modify-hostGroup").modal("show")
    },

    showBindTplModal: function () {
        var id = $(this).attr("host");

        $("#tplGrpId").val(id);
        $("#bind-tpl").modal("show")
    },
    showBindHostModal: function () {
        var id = $(this).attr("host");

        $("#hostGrpId").val(id)
        $("#bind-host").modal("show")
    },
    delHostGroup: function () {
        var id = $(this).attr("host")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/hostgroup/delete",
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
    addHostGroup: function () {
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
                   url:"/hostgroup/add",
                   dataType:"json",
                   data:{
                       grpName:name,
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-hostGroup").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-hostGroup").modal("hide");
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
    modifyHostGroup: function () {
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
                   url:"/hostgroup/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       grpName:name,
                   },
                   success:function (data) {
                       if(data.success){
                           $("#modify-hostGroup").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#modify-hostGroup").modal("hide");
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
    bindTpl: function () {
        var id=$("#tplGrpId").val()
        var tplId=$("#tpl").val()

        if(tplId==""){
            swal({
                     title: "请输入模版名称",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }
        $.ajax({
                   type:"post",
                   url:"/hostgroup/bindTpl",
                   dataType:"json",
                   data:{
                       tplId:tplId,
                       hostGrpId:id,
                   },
                   success:function (data) {
                       if(data.success){
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
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
    bindHost: function () {
        var id=$("#hostGrpId").val()
        var hostId=$("#hosts").val()

        if(hostId==""){
            swal({
                     title: "请选择host",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }
        $.ajax({
                   type:"post",
                   url:"/hostgroup/bindHost",
                   dataType:"json",
                   data:{
                       hostIds:hostId,
                       hostGrpId:id,
                   },
                   success:function (data) {
                       if(data.success){
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
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

    getAllHost:function () {
        $.ajax({
                   type:"get",
                   url:"/host/getAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#hosts").select2({
                              tags:true,
                              language: 'zh-CN',
                              data: data.result,
                              placeholder:'请选择回调事件',

                           })

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
                           $("#tpl").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择回调事件',

                           })

                       }
                   }
               })
    }
}