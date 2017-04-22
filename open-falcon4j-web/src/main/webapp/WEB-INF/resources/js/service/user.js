$(function () {
    User.init();
})

var User={
    init:function () {
        User.bind();
    },
    bind:function () {
        $("#confirmAddUser").click(User.addUser)
        $("#confirmModifyUser").click(User.modifyUser)
        $(".modify").click(User.showModifyModal)
        $(".del").click(User.delUser)

    },
    showModifyModal:function () {
        var name=$(this).attr("name");
        var id=$(this).attr("user");
        var phone=$(this).attr("phone");
        var email=$(this).attr("email");

        $("#name").val(name);
        $("#phone").val(phone);
        $("#email").val(email);
        $("#id").val(id);
        $("#modify-user").modal("show");

    },

    delUser:function () {
        var id=$(this).attr("user")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/monitoruser/delete",
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

    addUser:function () {
        var name=$("#add-name").val();
        var phone=$("#add-phone").val();
        var email=$("#add-email").val();

        if(name==""){
            swal({
                     title: "请输入用户名",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(phone==""){
            swal({
                     title: "请输入手机号",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(email==""){
            swal({
                     title: "请输入邮箱",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/monitoruser/add",
                   dataType:"json",
                   data:{
                       name:name,
                       phone:phone,
                       email:email
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-user").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-user").modal("hide");
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
    modifyUser:function () {
      var name=$("#name").val();
      var phone=$("#phone").val();
      var email=$("#email").val();
      var id=$("#id").val();

      if(name==""){
          swal({
                   title: "请输入用户名",
                   confirmButtonColor: "#EF5350",
                   type: "error"
               });
          return;
      }

      if(phone==""){
          swal({
                   title: "请输入手机号",
                   confirmButtonColor: "#EF5350",
                   type: "error"
               });
          return;
      }

      if(email==""){
          swal({
                   title: "请输入邮箱",
                   confirmButtonColor: "#EF5350",
                   type: "error"
               });
          return;
      }

        $.ajax({
                   type:"post",
                   url:"/monitoruser/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       name:name,
                       phone:phone,
                       email:email
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

    }
}