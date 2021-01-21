<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
        <ul class="nav" id="side-menu">
            <li>
                <a href="javascript:void(0)" class="waves-effect">
                    <i data-icon="F" class="linea-icon linea-software fa-fw"></i>
                    <span class="hide-menu">Multi level<span class="fa arrow"></span>
                        </span>
                </a>
                <ul class="nav nav-second-level">
                    <li> <a href="/view/user/list">用户管理</a> </li>
                    <li> <a href="javascript:void(0)">角色管理</a> </li>
                    <li> <a href="javascript:void(0)">菜单管理</a> </li>
                    <li> <a href="javascript:void(0)" class="waves-effect">Third Level <span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level">
                            <li> <a href="javascript:void(0)">Third Level Item</a> </li>
                            <li> <a href="javascript:void(0)">Third Level Item</a> </li>
                            <li> <a href="javascript:void(0)">Third Level Item</a> </li>
                            <li> <a href="javascript:void(0)">Third Level Item</a> </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<script src="/custom/menu.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#side-menu").InitSidebar(
            [
                {
                    "href": "/home",
                    "icon": "fa fa-dashboard",
                    "title": "首&emsp;&emsp;页"
                },
                {
                    "href": "/view/user",
                    "icon": "icon-user",
                    "title": "用户管理",
                    "children": [
                        {
                            "href": "/view/user/list",
                            "title": "人员列表",
                            "roles": ["admin"]

                        },
                        {
                            "href": "/view/user/roles",
                            "title": "角色列表",
                            "roles": ["admin"]
                        }
                    ]
                },
                // {
                //     "href": "/view/apply/list",
                //     "icon": "fa fa-paper-plane-o",
                //     "title": "申请管理"
                // },
                {
                    "href": "/view/appointmentRecord/list",
                    "icon": "fa fa-umbrella",
                    "title": "预约管理"
                },
                {
                    "href": "/view/course/list",
                    "icon": "fa fa-file-text-o",
                    "title": "课表管理"
                },
                {
                    "href": "/view/rooms",
                    "icon": "fa fa-home",
                    "title": "实验室预约",
                    "roles": ["admin","teacher"]
                },
                {
                    "href": "/view/devices",
                    "icon": "fa fa-desktop",
                    "title": "设备管理",
                    "roles": ["admin"]
                },
                {
                    "href": "/view/software/list",
                    "icon": "fa fa-th",
                    "title": "软件管理",
                    "roles": ["admin"]
                },{
                    "href": "/view/settings/list",
                    "icon": "ti-settings",
                    "title": "系统配置",
                    "roles": ["admin"]
                },
                {
                    "href": "/view/my",
                    "icon": "ti-user",
                    "title": "个人中心",
                    "children": [
                        {
                            "href": "/view/my/info",
                            "title": "我的信息"

                        },
                        {
                            "href": "/view/my/password",
                            "title": "修改密码"
                        }
                    ]
                },
                {
                    "href": "/logout",
                    "icon": "icon-logout",
                    "title": "退出登录"
                }
            ]
        );
    })
</script>
