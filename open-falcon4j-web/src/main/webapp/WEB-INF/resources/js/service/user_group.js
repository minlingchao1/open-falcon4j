/**
 * Created by lingchaomin on 2017/4/12.
 */
$(function () {
    UserGroup.init();
})

var UserGroup={
    init:function () {
        UserGroup.getAllUsers()
        UserGroup.bind()

    },
    bind:function () {
        $("#confirmAddUserGroup").click(UserGroup.addUserGroup)
        $("#confirmModifyUserGroup").click(UserGroup.modifyUserGroup)
        $("#confirmBindUser").click(UserGroup.bindUser)
        $(".modify").click(UserGroup.showUserGroupModal)
        $(".del").click(UserGroup.delUserGroup)
        $(".bind").click(UserGroup.showBindUserModal)
    },
    addUserGroup:function () {
        var name=$("#add-name").val()
        var note=$("#add-note").val()

        if(name==""){
            swal({
                     title: "请输入用户组名",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/usergroup/add",
                   dataType:"json",
                   data:{
                       name:name,
                       note:note,
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-user").modal("hide");
                           swal({
                                    title: "添加成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-user").modal("hide");
                           swal({
                                    title: "添加失败",
                                    text:data.msg,
                                    confirmButtonColor: "#EF5350",
                                    type: "error"
                                });
                       }
                   }
               })

    },
    showUserGroupModal:function () {
        var name=$(this).attr("name");
        var note=$(this).attr("note");
        var id=$(this).attr("user");

        $("#id").val(id);
        $("#name").val(name)
        $("#note").val(note)
        $("#modify-user").modal("show")
    },
    modifyUserGroup:function () {
        var id=$("#id").val()
        var name=$("#name").val()
        var note=$("#note").val()

        if(name==""){
            swal({
                     title: "请输入用户组名",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/usergroup/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       name:name,
                       note:note,
                   },
                   success:function (data) {
                       if(data.success){
                           $("#modify-user").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#modify-user").modal("hide");
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
    delUserGroup:function () {
        var id=$(this).attr("user")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/usergroup/delete",
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

    getAllUsers:function () {
        $.ajax({
                   type:"get",
                   url:"/monitoruser/selectAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#users").select2({
                              tags: true,
                              language: 'zh-CN',
                              data: data.result,
                              placeholder:'请选择用户',

                           })
                       }else {
                       }
                   }
               })
    },

    showBindUserModal:function () {
        var id=$(this).attr("user");
        $("#id").val(id);
        $("#bind-user").modal("show")
    },
    bindUser:function () {
        var id=$("#id").val();
        var userIds=$("#users").val()
        $.ajax({
                   type:"post",
                   url:"/usergroup/bindUser",
                   dataType:"json",
                   data:{
                       userGrpId:id,
                       userIds:userIds
                   },
                   success:function (data) {
                       if(data.success){
                           swal({
                                title: "绑定成功",
                                confirmButtonColor: "#66BB6A",
                                type: "success"
                            });
                       }else {
                           swal({
                                title: "绑定失败",
                                text:data.msg,
                                confirmButtonColor: "#EF5350",
                                type: "error"
                            });
                       }

                   }
               })
    }
}
