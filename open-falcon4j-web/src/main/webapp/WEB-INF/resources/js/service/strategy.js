/**
 * Created by lingchaomin on 2017/4/13.
 */
$(function () {
   Strategy.init()
})
var Strategy={
    init:function () {
        Strategy.getAllTpl()
        Strategy.bind();
    },
    bind:function () {
        $("#confirmAddStrategy").click(Strategy.addStrategy)
        $("#confirmModifyStrategy").click(Strategy.modifyStrategy)
        $(".modify").click(Strategy.showModifyModal)
        $(".del").click(Strategy.delExpression)
    },
    showModifyModal:function () {
        var metric=$(this).attr("metric")
        var tag=$(this).attr("tag")
        var func=$(this).attr("func")
        var operator=$(this).attr("operator")
        var priority=$(this).attr("priority")
        var rightValue=$(this).attr("rightValue")
        var maxStep=$(this).attr("maxStep")
        var tplId=$(this).attr("tplId")
        var id=$(this).attr("expression")

        $("#metric").val(metric)
        $("#tag").val(tag)
        $("#func").val(func)
        $("#operator").val(operator)
        $("#priority").val(priority)
        $("#rightValue").val(rightValue)
        $("#maxStep").val(maxStep)
        $("#tplId").val(tplId).trigger("change")
        $("#id").val(id)

        $("#modify-expression").modal("show")

    },
    delStrategy:function () {
        var id=$(this).attr("expression")
        bootbox.confirm("确定删除?", function(result) {
            if(result){
                $.ajax({
                           type:"post",
                           url:"/strategy/delete",
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
    addStrategy:function () {
        var metric=$("#add-metric").val()
        var tag=$("#add-tag").val()
        var func=$("#add-func").val()
        var operator=$("#add-operator").val()
        var priority=$("#add-priority").val()
        var rightValue=$("#add-rightValue").val()
        var maxStep=$("#add-maxStep").val()
        var tplId=$("#add-tplId").val()
        var note=$("#add-note").val()

        if(metric==""){
            swal({
                     title: "请输入采集指标名称",
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

        if(tplId==""){
            swal({
                     title: "请选择模版",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/strategy/add",
                   dataType:"json",
                   data:{
                       metric:metric,
                       tag:tag,
                       func:func,
                       operator:operator,
                       rightValue:rightValue,
                       maxStep:maxStep,
                       tplId:tplId,
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
    modifyStrategy:function () {
        var metric=$("#metric").val()
        var tag=$("#tag").val()
        var func=$("#func").val()
        var operator=$("#operator").val()
        var priority=$("#priority").val()
        var rightValue=$("#rightValue").val()
        var maxStep=$("#maxStep").val()
        var tplId=$("#tplId").val()
        var id=$("#id").val()
        var note=$("#note").val()


        if(metric==""){
            swal({
                     title: "请输入采集指标名称",
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

        if(tplId==""){
            swal({
                     title: "请选择模版",
                     confirmButtonColor: "#EF5350",
                     type: "error"
                 });
            return;
        }

        $.ajax({
                   type:"post",
                   url:"/strategy/modify",
                   dataType:"json",
                   data:{
                       id:id,
                       metric:metric,
                       tag:tag,
                       func:func,
                       operator:operator,
                       rightValue:rightValue,
                       maxStep:maxStep,
                       priority:priority,
                       tplId:tplId,
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
    getAllTpl:function () {
        $.ajax({
                   type:"get",
                   url:"/tpl/getAll",
                   dataType:"json",
                   success:function (data) {
                       if(data.success){
                           $("#tplId").select2({
                              language: 'zh-CN',
                              data: data.result,
                              placeholder:'请选择模版',

                          })
                           $("#add-tplId").select2({
                              language: 'zh-CN',
                              data: data.result,
                              placeholder:'请选择模版',

                          })
                       }
                   }
               })
    }
}