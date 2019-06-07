


define(function(require, exports, module){

    J = jQuery;
    require('tagsinput');

    var PostView = function () {};


    PostView.prototype = {
        name: 'PostView',
        init: function () {
            this.bindEvents();
        },

        bindEvents: function () {
            var that = this;
            that.bindTagit();
            that.bindValidate();
            that.bindUpload();

            $('button[event="post_submit"]').click(function () {
                var status = $(this).data('status');
                $("input[name='status']").val(status);
                $("#submitForm").submit();
            });
        },

        bindTagit: function () {
            $('#tags').tagsinput({
                maxTags: 4,
                trimValue: true
            });
        },

        bindValidate: function () {
            require.async(['validation', 'validation-additional'], function () {
                $("#submitForm").validate({
                    ignore: "",     /*某些元素不验证*/
                    rules: {
                        title: 'required',
                        channelId: 'required',
                        tags:'required',
                        content: {
                            required: true,
                            check_editor: true
                        }
                    },
                    messages: {
                        title: '请输入标题',
                        channelId: '请选择栏目',
                        content: {
                            required: '内容不能为空',
                            check_editor: '内容不能为空'
                        },
                        tags:"请填写标签"
                    },


                    //用什么标签来显示错误信息
                    errorElement: "p",


                    //指明错误信息的位置，默认把错误信息放在验证的元素后面。
                    errorPlacement: function (error, element) {

                        error.addClass("help-block");
                        if (element.prop("type") === "checkbox") {
                            error.insertAfter(element.parent("label"));
                        } else if (element.is("textarea")) {
                            //closet会向上搜索DOM树，直到找到第一个匹配的祖先元素。该判断针对textarea文本区
                            error.insertAfter(element.closest(".md-editor"));
                        } else {
                            error.insertAfter(element);
                        }
                    },

                    //可以给认证通过的元素加效果。错误信息高亮
                    highlight: function (element, errorClass, validClass) {
                        $(element).closest("div").addClass("has-error").removeClass("has-success");
                    },
                    unhighlight: function (element, errorClass, validClass) {
                        $(element).closest("div").addClass("has-success").removeClass("has-error");
                    }
                });
            });
        },

        bindUpload: function () {
            $('.upload-input').on('change',function () {
                var file=$(this)[0].files[0];
                if(!file){
                    return false;
                }
                var form=new FormData();
                form.append("file",file);

                $.ajax({
                    url: _MTONS.BASE_PATH + "/post/upload?crop=thumbnail_post_size",
                    data: form,
                    type: "POST",
                    cache: false, //上传文件无需缓存
                    processData: false,
                    contentType: false,
                    success:function (result) {
                        if(result.status===200){
                            layer.alert(result.message);
                            var path=result.path;
                            $("#thumbnail_image").css("background", "url(" + path + ") no-repeat scroll center 0 rgba(0, 0, 0, 0)");
                            $("#thumbnail").val(path);

                        }else {
                            layer.alert(result.message);
                        }
                    }
                });
            });
        }
    };

    exports.init = function () {
        new PostView().init();
    }



});