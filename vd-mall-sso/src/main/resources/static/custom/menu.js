(function ($) {
    $.fn.InitSidebar = function (menus) {
        var roles = sessionStorage.getItem("SPRING_SECURITY_ROLES");
        if (roles === null || roles === undefined) {
            $.ajax({
                url: "/api/users/loginInfo", type: "get", async: false, success: function (data) {
                    if (data.result === "SUCCESS") {
                        sessionStorage.setItem("SPRING_SECURITY_USER", JSON.stringify(data["user"]));
                        sessionStorage.setItem("SPRING_SECURITY_ROLES", roles = JSON.stringify(data["authorities"]))
                    }
                }
            })
        }
        roles = JSON.parse(roles);
        for (var idx = 0; idx < menus.length; idx++) {
            var menu = menus[idx];
            var children = menu["children"];
            var hasChildren = children !== null && children !== undefined && children.length !== 0;
            var parentPermission = hasPermission(menu["roles"], roles);
            var childPermission = !hasChildren;
            var $item = $("<li></li>");
            var $arrow = $('<span class="hide-menu"></span>').append($.parseHTML(menu["title"]));
            if (hasChildren) {
                $arrow.append($('<span class="fa arrow"></span>'))
            }
            var $fnav = $('<a class="waves-effect"></a>').attr({href: menu["href"]}).append($('<i class="fa-fw"></i>').addClass(menu["icon"])).append($arrow);
            $item.append($fnav);
            if (children !== null && children !== undefined && children.length !== 0) {
                var $snav = $('<ul class="nav nav-second-level"></ul>');
                for (var i = 0; i < children.length; i++) {
                    var item = children[i];
                    var permission = hasPermission(item["roles"], roles);
                    if (permission) {
                        childPermission = permission;
                        parentPermission = permission;
                        $snav.append($("<li></li>").append($("<a></a>").attr({href: item["href"]}).append($.parseHTML(item["title"]))))
                    }
                }
                $item.append($snav)
            }
            if (parentPermission && childPermission) {
                this.append($item)
            }
        }
    };

    function hasPermission(roles, haveRoles) {
        if (roles === null || roles === undefined || roles.length === 0) {
            return true
        }
        for (var i = 0; i < haveRoles.length; i++) {
            var haveRole = haveRoles[i];
            if ($.inArray(haveRole, roles) >= 0) {
                return true
            }
        }
        return false
    }
})(jQuery);
