(function ($) {
    $.fn.InitDataTable = function (opts) {
        var options = {
            paging: true,
            processing: false,
            lengthChange: false,
            ordering: true,
            autoWidth: false,
            info: true,
            serverSide: false,
            fixedHeader: true,
            searching: false,
            aLengthMenu: [10],
            ajax: {url: opts.url, dataSrc: 'data'},
            language: {url: '/lang/datatable.chs.json'}
        };

        var _columns = [], columns = opts.columns;
        for (var i = 0; i < columns.length; i++) {
            var column = columns[i];
            if (toRawType(column) === 'Object') {
                _columns[i] = column
            } else if (toRawType(column) === 'String') {
                _columns[i] = {data: column}
            }
        }
        options.columns = _columns;
        var _thead = opts.thead;
        if (_thead) {
            var _$thead = this.find("thead");
            var _$tr = $("<tr></tr>");

            for (var idx = 0; idx < _thead.length; idx++) {
                _$tr.append($("<th></th>").text(_thead[idx]));
            }
            _$thead.append(_$tr)
        }
        return this.DataTable(options);
    };

    var httpRequest = {};
    httpRequest._toRawType = toRawType;

    httpRequest._request = function requestBody(url, method, params, callback, loading, async, contentType, options) {
        if (loading) {
            $(".preloader").attr("style", "opacity:0.5").show();
        }

        if (async === undefined) {
            async = true;
        }
        var urlencoded = "application/x-www-form-urlencoded";
        var utf8 = "application/json;charset=utf-8";

        var content_type = contentType !== null && contentType !== undefined ? contentType : urlencoded;
        if (content_type && toRawType(params) === 'String') {
            content_type = utf8;
        }
        console.log("param:" + params);
        var httpGetStart = new Date().getTime();
        var _default = {
            url: url,
            type: method,
            data: params,
            async: async,
            contentType: content_type,
            success: function (data) {
                if (loading !== null && loading !== undefined && loading) {
                    $('.preloader').hide();
                }
                console.log("url:" + url + "   响应时间： " + (new Date().getTime() - httpGetStart) / 1000 + "s");
                callback(data);
            },
            complete: function (XMLHttpRequest, status) {
                if (status === 'timeout') {
                    if (loading !== null && loading !== undefined && loading) {
                        $('.preloader').hide();
                    }
                    callback(null);
                }
            },
            error: function () {
                if (loading !== null && loading !== undefined && loading) {
                    $('.preloader').hide();
                }
                callback(null);
            }
        };
        $.ajax($.extend(true, _default, options));
    };

    httpRequest.get = function (url, params, callback, loading, async) {
        this._request(url, "GET", params, callback, loading, async);
    };

    httpRequest.post = function (url, params, callback, loading) {
        this._request(url, "POST", params, callback, loading);
    };
    httpRequest.put = function (url, params, callback, loading) {
        this._request(url, "PUT", params, callback, loading);

    };
    httpRequest.delete = function (url, params, callback, loading) {
        this._request(url, "DELETE", params, callback, loading);
    };

    httpRequest.upload = function (url, params, callback, loading, async) {
        this._request(url, "POST", params, callback, loading, async, false, {cache: false, processData: false});
    };

    $.httpRequest = httpRequest;


    function Dialog(selector, options) {
        this.dialog = selector;
        this.items = selector.find(".form-group");
        this.submitBtn = selector.find(".modal-footer button[data-action='submit']");
        this.cancelBtn = selector.find(".modal-footer button[data-action='cancel']");
        this.submitEvent = options ? options.submitEvent : undefined;
        this.cancelEvent = options ? options.cancelEvent : undefined;
        this.uploader = options ? options.uploader : function (file) {
            var filename = "";
            var formData = new FormData();
            formData.append("file", file);
            $.httpRequest.upload("/api/v1/image/upload", formData, function (res) {
                filename = res.name;
            }, false, false);
            return filename;
        };
        this.hasSubmitEventListener = false;
        this.hasCancelEventListener = false;
        this.requestType = options ? options.requestType : undefined;
        this.data = null;

        if (this.submitEvent) {
            this.registerSubmitEvent(this.submitEvent)
        }

        if (this.cancelEvent) {
            this.registerCancelEvent(this.cancelEvent)
        }
    }

    Dialog.prototype.write = function (obj) {
        this.data = obj;
        this.items.each(function (idx, item) {
            var tags = $(item).find("input,textarea,select");
            tags.each(function (i, tag) {
                var tagName = tag.tagName.toLowerCase();
                tag_write(tagName, tag, obj)
            })
        })
    };

    Dialog.prototype.read = function (obj) {
        var uploader = this.uploader;
        this.items.each(function (idx, item) {
            var tags = $(item).find("input,textarea,select");
            tags.each(function (i, tag) {
                var tagName = tag.tagName.toLowerCase();
                tag_read(tagName, tag, obj, uploader)
            })
        })
    };

    Dialog.prototype.clear = function () {
        this.data = null;
        this.items.each(function (idx, item) {
            var tags = $(item).find("input,textarea,select");
            tags.each(function (i, tag) {
                var tagName = tag.tagName.toLowerCase();

                tag_clear(tagName, tag);
            })
        })
    };

    Dialog.prototype.show = function (option) {
        if (option && option.title) {
            $(this.dialog.find(".modal-title")).text(option.title)
        }
        if (option && option.requestType) {
            this.requestType = option.requestType;

            this.items.each(function (idx, item) {
                var hide = $(item).attr("data-hide");
                if (hide) {
                    var array = hide.split(",");
                    var has = array.includes(option.requestType);
                    if (has) {
                        $(item).attr("hidden", true)
                    } else {
                        $(item).attr("hidden", false)
                    }
                }

                var tags = $(item).find("input,textarea,select");
                tags.each(function (i, tag) {
                    var tagName = tag.tagName.toLowerCase();
                    var readonly = $(tag).attr("data-readonly");
                    if (tagName === "input" && readonly) {
                        var array = readonly.split(",");
                        var has = array.includes(option.requestType);
                        if (has) {
                            $(tag).attr("readonly", true)
                        } else {
                            $(tag).attr("readonly", false)
                        }
                    }
                    tag_clear(tagName, tag);
                })
            })
        }
        this.dialog.modal('show');
    };

    Dialog.prototype.hide = function () {

        $(this.dialog.find(".modal-title")).text("");

        this.requestType = null;

        this.dialog.modal('hide');
        this.clear();
    };

    Dialog.prototype.registerSubmitEvent = function (callback) {
        if (!this.submitBtn || this.submitBtn.length === 0) {
            console.warn("not found submit btn : button[data-action='submit']");
            return;
        }
        var submit_btn = this.submitBtn[0];
        if (this.hasSubmitEventListener) {
            return;
        }
        var _this = this;
        submit_btn.addEventListener("click", function () {
            var data = {};
            _this.read(data);
            callback($.extend(true, _this.data, data), _this.requestType);
        }, true);

        this.hasSubmitEventListener = true;
    };

    Dialog.prototype.registerCancelEvent = function (callback) {
        if (!this.cancelBtn || this.cancelBtn.length === 0) {
            console.warn("not found submit btn : button[data-action='cancel']");
            return;
        }
        var cancel_btn = this.cancelBtn[0];
        if (this.hasCancelEventListener) {
            return;
        }

        cancel_btn.addEventListener("click", function () {
            callback();
        }, true);

        this.hasCancelEventListener = true;
    };

    $.fn.dialog = function (options) {
        var $dialog = new Dialog(this, options);

        $dialog.registerCancelEvent(function () {
            $dialog.clear();
        });

        return $dialog;
    };

    var _tags = {
        input: {
            read: function ($tag, obj, uploader) {
                var type = $tag.attr("type");
                var name = $tag.attr("name");
                if (!type || type === "password" || type === "text") {
                    obj[name] = $tag.val();
                } else if (type === "file" && $tag.hasClass("dropify")) {
                    var files = $tag.prop("files");
                    if (files && files.length !== 0 && uploader) {
                        obj[name] = uploader(files[0]);
                    }
                } else if (type === "radio") {
                    obj[name] = $('input[name=' + name + ']:checked').val();
                } else if (type === "checkbox") {
                    var _arr = [];
                    $('input[name=' + name + ']:checked').each(function (i, v) {
                        _arr[i] = $(v).val()
                    });
                    obj[name] = _arr;
                    console.log(_arr)
                }
            },
            write: function ($tag, obj) {
                var type = $tag.attr("type");
                var name = $tag.attr("name");
                var val = obj[name];
                if (!type || type === "password" || type === "text") {
                    $tag.val(val);
                } else if (type === "file" && $tag.hasClass("dropify")) {
                    var src = $tag.attr("data-default-file");
                    if (val) {
                        src = "/api/v1/image/get?name=" + val
                    } else {
                        val = src;
                    }
                    $tag.parent().find(".dropify-preview").find("img").attr("src", src);
                    $tag.parent().find(".dropify-preview .dropify-infos .dropify-filename-inner").text(subFileName(val))
                } else if (type === "radio") {
                    var $radio = $('input[name=' + name + '][value=' + val + ']');
                    $radio.attr("checked", true);
                    $radio.parent().addClass("checked");
                } else if (type === "checkbox") {
                    if (val && val.length > 0) {
                        for (var i = 0; i < val.length; i++) {
                            var v = val[i];
                            var $checkbox = $('input[name=' + name + '][value=' + v + ']');
                            $checkbox.attr("checked", true);
                            $checkbox.parent().addClass("checked");
                        }
                    }
                }
            },
            clear: function ($tag) {
                var type = $tag.attr("type");
                if (!type || type === "password" || type === "text") {
                    $tag.val("");
                } else if (type === "file" && $tag.hasClass("dropify")) {
                    var val = $tag.attr("data-default-file");
                    $tag.parent().find(".dropify-preview").find("img").attr("src", val);
                    $tag.parent().find(".dropify-preview .dropify-infos .dropify-filename-inner").text(subFileName(val))
                } else if (type === "radio") {
                    $tag.attr("checked", false);
                    $tag.parent().removeClass("checked");
                } else if (type === "checkbox") {
                    $tag.attr("checked", false);
                    $tag.parent().removeClass("checked");
                }
            }
        },
        select: {
            read: function ($tag, obj) {
                obj[$tag.attr("name")] = $($tag.find("option:selected")).attr("value");
            },
            write: function ($tag, obj) {
                var val = obj[$tag.attr("name")];
                $($tag.find("option[value=" + val + "]")).attr("selected", true);
            },
            clear: function ($tag) {
                $tag.find("option:selected").attr("selected", false);
            }
        }
    };

    function tag_write(tagName, tag, obj) {
        _tags[tagName].write($(tag), obj);
    }

    function tag_read(tagName, tag, obj, uploader) {
        _tags[tagName].read($(tag), obj, uploader);
    }

    function tag_clear(tagName, tag) {
        _tags[tagName].clear($(tag));
    }

    function subFileName(filename) {
        var start = filename.lastIndexOf("/") + 1;
        return filename.substr(start)
    }

    function toRawType(value) {
        var _toString = Object.prototype.toString;
        return _toString.call(value).slice(8, -1)
    }

    $.loadBtnOnPermission = function (html) {
        var $tags = $(html);
        var $_html = $("<div></div>");

        for (var i = 0; i < $tags.length; i++) {
            var $tag = $($tags[i]);
            var permissions = $tag.attr("data-permission");
            if (!permissions) {
                $_html.append($tag);
                continue;
            }

            var hide = $tag.attr("data-hidden");
            if (hide && hide==="true") {
                $tag.addClass("hidden");
                continue;
            }
            var roles = JSON.parse(sessionStorage.getItem("SPRING_SECURITY_ROLES"));
            var hasPermission = _hasPermission(permissions.split(","), roles);

            if (hasPermission) {
                $_html.append($tag);
            }
        }
        return $_html.html();
    };

    function _hasPermission(roles, haveRoles) {
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

    $.fn.readData = function(){
        var data = {};
        $(this).find(".form-group").each(function (idx, item) {
            var tags = $(item).find("input,textarea,select");
            tags.each(function (i, tag) {
                var tagName = tag.tagName.toLowerCase();
                tag_read(tagName, tag, data, null)
            })
        });
        return data;
    };

    $.appendParam = function(url,data){
      if (!data){
          return url;
      }
      var params =[];
      var keys = Object.keys(data);
      for (var i = 0 ;i< keys.length;i++){
          params[i] = keys[i] + "=" + data[keys[i]];
      }

      return url+"?" + params.join("&");

    };

    $.acquireParamFromUrl = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    };

    $.MYToast = function (options) {
        var _default = {
            text: '操作成功！',
            position: 'top-right',
            loaderBg: '#ff6849',
            icon: 'info',
            hideAfter: 1500,
            stack: 6
        };
        if (toRawType(options) === 'String'){
            _default.text = options;
            options = {};
        }
        $.toast($.extend(true, _default, options));
    };

    $.MYAlert = function (options) {
        var _default = {
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: '取消',
            closeOnConfirm: false
        };

        swal($.extend(true, _default, options), function () {
            swal({title: "", text: "", timer: 1, showConfirmButton: false});
            if (options.success){
                options.success();
            }
        });
    }
})(jQuery);
