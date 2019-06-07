



define(function(require, exports, module) {

    var J = jQuery, _BATH = _MTONS.BASE_PATH;

    var _configs = {
        errorElement: "p",
        errorPlacement: function (error, element) {
            error.addClass("help-block");
            if ( element.prop( "name" ) === "email" ) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        highlight: function (element, errorClass, validClass) {
            J(element).closest("div").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            J(element).closest("div").addClass("has-success").removeClass("has-error");
        }
    };


    var _bind_validate = function (formId, configs) {
        var options = J.extend({}, _configs, configs);

        require.async(['validation', 'validation-additional'], function () {
            J(formId).validate(options);
        });
    };


    var Validate={

        register:function (formId) {

            _bind_validate(formId,{
                rules: {
                    username: {
                        required: true,
                        check_username: true,
                        minlength:4
                    },
                    password: {
                        required: true
                    },
                    password2: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {
                    username: {
                        required: '请输入用户名',
                        check_username: '只能是字母/字母+数字,不少于4位'
                    },
                    password: {
                        required: '请输入密码'
                    },
                    password2: {
                        required: '请输入确认密码',
                        equalTo: '两次输入的密码不一致'
                    }
                }
            })

        }
    };

    module.exports=Validate;
});