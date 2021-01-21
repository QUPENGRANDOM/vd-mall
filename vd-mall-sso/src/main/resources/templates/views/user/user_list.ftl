<#assign title="用户列表页">
<#include "../../include/header.ftl">
<div id="wrapper">
    <#include "../../include/navbar.ftl">
    <#include "../../include/sidebar.ftl">
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">人员列表</h4>
                </div>
                <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
                    <button class="btn btn-success waves-effect waves-light pull-right" onclick="_save()">
                        <i class="fa fa-plus m-r-5"></i><span>新建</span>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="white-box">
                        <div class="form-horizontal" id="form-horizontal">
                            <div class="row">
                                <div class="col-md-4 form-group" style="margin-bottom: 10px;">
                                    <label class="col-md-3 control-label" style="text-align: center">用户名：</label>
                                    <div class="col-md-6">
                                        <input type="text" name="username" placeholder="将用户名模糊匹配..." class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-4 form-group" style="margin-bottom: 10px;">
                                </div>
                                <div id="searchKey" class="col-md-3 form-group" style="margin-bottom: 10px;"></div>

                                <div class="col-md-1 form-group" style="text-align:right;margin-bottom: 10px;">
                                    <button type="submit" class="btn btn-info" id="btn-advanced-search" onclick="_custom_query()">
                                        <i class="fa fa-search"></i>查询
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="table-responsive">
                            <table id="table" class="table table-striped">
                                <thead></thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="saveOrUpdateDialog" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">网络</h4>
                </div>
                <div class="modal-body">
                    <div>
                        <div class="form-group">
                            <label for="dialog_username" class="control-label">用户名:</label>
                            <input type="text" data-readonly="update" class="form-control" name="username"
                                   id="dialog_username">
                        </div>
                        <div class="form-group" data-hide="update">
                            <label for="dialog_password" class="control-label">密码:</label>
                            <input type="password" class="form-control" name="password" id="dialog_password">
                        </div>
                        <div class="form-group">
                            <label for="dialog_nickname" class="control-label">昵称:</label>
                            <input type="text" class="form-control" name="nickname" id="dialog_nickname">
                        </div>
                        <div class="form-group">
                            <label for="dialog_sex" class="control-label">性别:</label>
                            <div class="row icheck-group">
                                <div class="col-md-2">
                                    <input type="radio" class="check" id="square-radio-0" value="MEN" name="sex"
                                           data-radio="iradio_square-blue">
                                    <label for="square-radio-0">男</label>
                                </div>
                                <div class="col-md-2">
                                    <input type="radio" class="check" id="square-radio-1" value="WOMEN" name="sex"
                                           data-radio="iradio_square-blue">
                                    <label for="square-radio-1">女</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="dialog_mail" class="control-label">邮箱:</label>
                            <input type="text" class="form-control" name="mail" id="dialog_mail">
                        </div>

                        <div class="form-group" id="roles-wrapper">
                            <label for="dialog_roles" class="control-label">角色:</label>
                            <div class="row icheck-group">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" data-action="cancel">取消</button>
                    <button type="button" class="btn btn-primary" data-action="submit">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../../include/footer.ftl">
<script>
    var $dropify = $('.dropify').dropify();
    var $dialog = $("#saveOrUpdateDialog").dialog();
    var saveOrUpdate = {
        save: function (data) {
            if (!$.isArray(data.roles)) {
                data.roles = [data.roles];
            }
            $.httpRequest.post("/api/users", JSON.stringify(data), function (res) {
                $dialog.hide();
                $dataTable.ajax.reload();
            })
        },
        update: function (data) {
            if (!$.isArray(data.roles)) {
                data.roles = [data.roles];
            }
            $.httpRequest.put("/api/users", JSON.stringify(data), function (res) {
                $dialog.hide();
                $dataTable.ajax.reload();
            })
        }
    };

    $dialog.registerSubmitEvent(function (data, type) {
        saveOrUpdate[type](data);
    });

    var $dataTable = $("#table").InitDataTable({
        url: "/api/v1/users/paging",
        thead: ["用户名", "昵称", "性别", "邮箱", "创建时间", "操作"],
        columns: [
            "username",
            "nickname",
            {
                data: "sex",
                render: function (data) {
                    if (data === "MEN") {
                        return "男";
                    } else if (data === "WOMEN") {
                        return "女";
                    } else {
                        return "未知";
                    }
                }
            },
            "mail",
            {
                data: "createTime",
                render: function (data, type, full, meta) {
                    return data.replace('T', " ").replace(".000+0000","");
                }
            },
            {
                data: "id",
                render: function (data, type, full, meta) {
                    return '<a href="#" data-type="edit" onclick="_edit(' + data + ')"><i class="fa fa-edit text-warning m-r-10"></i></a>\n' +
                        '<a href="#" onclick="_delete(' + data + ')"><i class="fa fa-trash-o text-danger m-r-10"></i></a>' +
                        '<a href="#" onclick="_resetPassword(' + data + ')"><i class="fa fa-key text-uppercase"></i></a>';
                }
            }
        ]
    });

    var _save = function () {
        $dialog.show({title: "添加用户", requestType: "save"});
    };

    var _edit = function (id) {
        $dialog.show({title: "编辑用户", requestType: "update"});

        $.httpRequest.get("/api/v1/users/" + id, null, function (res) {
            $dialog.write(res.data);
        }, true)
    };

    var _delete = function (id) {
        $.MYAlert({
            title: "确定要删除此用户?",
            success: function () {
                $.httpRequest.delete("/api/users/" + id, null, function (res) {
                    $.MYToast("删除成功！");
                    $dataTable.ajax.reload();
                })
            }
        });
    };

    var _resetPassword = function (id) {
        $.MYAlert({
            title: "确定要重置此用户的密码?",
            success: function () {
                $.httpRequest.post("/api/users/" + id + "/reset", null, function (res) {
                    $.MYToast("重置密码成功！");
                    $dataTable.ajax.reload();
                });
            }
        });
    };

    var _custom_query = function () {
        var data = $("#form-horizontal").readData();
        var url = $.appendParam("/api/v1/users/paging",data);
        console.log(url);
        $dataTable.ajax.url(url).load();
    }
</script>
