


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

        bindUpload: function () {
            // $('.upload-input').on('click',function () {
            //     console.log("正在上传头图....");
            // });

            $('.upload-input').on('change',function () {
                console.log("正在上传头图....");
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