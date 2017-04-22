/**
 * Created by lingchaomin on 2017/4/13.
 */
$(function () {
    Expression.init()
})
var Expression={
    init:function () {
        Expression.getAllAction()
        Expression.bind();
    },
    bind:function () {
        $("#confirmAddExpression").click(Expression.addExpression)
        $("#confirmModifyExpression").click(Expression.modifyExpression)
        $(".modify").click(Expression.showModifyModal)
        $(".del").click(Expression.delExpression)
    },
    showModifyModal:function () {
        var expression=$(this).attr("expressionName")
        var func=$(this).attr("func")
        var operator=$(this).attr("operator")
        var priority=$(this).attr("priority")
        var rightValue=$(this).attr("rightValue")
        var maxStep=$(this).attr("maxStep")
        var actionId=$(this).attr("actionId")
        var id=$(this).attr("expression")
        var note=$(this).attr("note")

        $("#expression").val(expression)
        $("#func").val(func)
        $("#operator").val(operator)
        $("#priority").val(priority)
        $("#rightValue").val(rightValue)
        $("#maxStep").val(maxStep)
        $("#actionId").val(actionId).trigger("change")
        $("#id").val(id)
        $("#note").val(note)

        $("#modify-expression").modal("show")

    },
    delExpression:function () {
        var id=$(this).attr("expression")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/expression/delete",
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
    addExpression:function () {
        var expression=$("#add-expressionId ").val()
        var func=$("#add-func").val()
        var operator=$("#add-operator").val()
        var priority=$("#add-priority").val()
        var rightValue=$("#add-rightValue").val()
        var maxStep=$("#add-maxStep").val()
        var actionId=$("#add-actionId").val()
        var note=$("#add-note").val()

        if(expression==""){
            swal({
                     title: "请输入表达式",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(func==""){
            swal({
                     title: "请输入判断函数",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(operator==""){
            swal({
                     title: "请输入操作符",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(priority==""){
            swal({
                     title: "请输入优先级",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(rightValue==""){
            swal({
                     title: "请输入阀值",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(maxStep==""){
            swal({
                     title: "请输入最大发送次数",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(actionId==""){
            swal({
                     title: "请选择模版",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/expression/add",
                   dataType:"json",
                   data:{
                       express:expression,
                       func:func,
                       operator :operator,
                       rightValue:rightValue,
                       maxStep:maxStep,
                       actionId:actionId,
                       priority:priority,
                       note:note
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-expression").modal("hide");
                           swal({
                                    title: "添加成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-expression").modal("hide");
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
    modifyExpression:function () {
        var expression=$("#expression").val()
        var func=$("#func").val()
        var operator=$("#operator").val()
        var priority=$("#priority").val()
        var rightValue=$("#rightValue").val()
        var maxStep=$("#maxStep").val()
        var actionId=$("#actionId").val()
        var id=$("#id").val()
        var note=$("#note").val()


        if(expression==""){
            swal({
                     title: "请输入表达式",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(func==""){
            swal({
                     title: "请输入判断函数",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(operator==""){
            swal({
                     title: "请输入操作符",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(priority==""){
            swal({
                     title: "请输入优先级",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(rightValue==""){
            swal({
                     title: "请输入阀值",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }


        if(maxStep==""){
            swal({
                     title: "请输入最大发送次数",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        if(actionId==""){
            swal({
                     title: "请选择模版",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/expression/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       express:expression,
                       func:func,
                       operator:operator,
                       rightValue:rightValue,
                       maxStep:maxStep,
                       priority:priority,
                       actionId:actionId,
                       note:note
                   },
                   success:function (data) {
                       if(data.success){
                           $("#add-expression").modal("hide");
                           swal({
                                    title: "修改成功",
                                    confirmButtonColor: "#66BB6A",
                                    type: "success"
                                });

                           window.location.reload();
                       }else {
                           $("#add-expression").modal("hide");
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
                               placeholder:'请选择模版',

                           })
                           $("#add-actionId").select2({
                               language: 'zh-CN',
                               data: data.result,
                               placeholder:'请选择模版',

                           })
                       }
                   }
               })
    }
}