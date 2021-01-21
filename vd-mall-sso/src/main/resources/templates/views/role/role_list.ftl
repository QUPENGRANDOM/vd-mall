<#assign title="角色列表页">
<#include "../../include/header.ftl">
<div id="wrapper">
    <#include "../../include/navbar.ftl">
    <#include "../../include/sidebar.ftl">
    <div id="page-wrapper">
        <div class="container-fluid">
            <div class="row bg-title">
                <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                    <h4 class="page-title">角色列表</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12">
                    <div class="white-box">
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
</div>
<#include "../../include/footer.ftl">
<script>
    var $dataTable = $("#table").InitDataTable({
        url: "/api/v1/roles/paging",
        thead: ["角色名", "描述", "创建时间"],
        columns: [
           "roleName",
            "description",
            {
                data: "createTime",
                render: function (data) {
                    return  data.replace('T', " ").replace(".000+0000","");
                }
            }
        ]
    });
</script>
