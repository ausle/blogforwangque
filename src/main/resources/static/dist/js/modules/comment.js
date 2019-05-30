






define(function (require,exports,module) {

    J = jQuery;
    require('plugins');
    require('owo-css');
    require('owo');

    var Authc = require('authc');

    var Comment={
        name:'comment',
        init:function (options) {
            this.options = J.extend({}, this.defaults, options);
            if (!this.options.load) {
                return false;
            }
            this.bindEvents();
        },
        defaults:{
            load: true,
            load_url : null,
            post_url : null,
            toId : 0,
            pageSize :6,
            // callback
            onLoad : function (i, data) {

            }
        },

        bindEvents:function () {
            var that=this;
            //获取评论列表
            that.pageCallback(1);

            //提交评论
            $('#btn-chat').click(function () {
                var text = $('#chat_text').val();
                var pid = $('#chat_pid').val();
                that.post(that.options.toId, pid, text);
            });


            new OwO({
                logo: '<i class="fa fa-smile-o fa-2"></i>',
                container: document.getElementById('face-btn'),//包含容器
                target: document.getElementById('chat_text'),//显示在哪里
                api: _MTONS.BASE_PATH + '/dist/vendors/owo/OwO.json',//表情包数据源
                position: 'down',	//文字符号点击后出现的位置
                width: '600px',
                maxHeight: '250px'
            });
        },


        post:function (toId, pid, text) {
            var opts = this.options;
            var that = this;

            if(!Authc.isAuthced()){
                Authc.showLogin();
                return false;
            }

            if (text.length == 0) {
                layer.msg('请输入内容再提交!', {icon: 2});
                return false;
            }
            if (text.length > 255) {
                layer.msg('内容过长，请输入140以内个字符', {icon: 2});
                return false;
            }

            J.ajax({
                url:opts.post_url,
                data:{"toId":toId,"pid":pid,'text':text},
                dataType:'json',//预期服务器返回的数据类型
                type :  "POST",
                cache : false,
                async: false,
                error:function () {

                },
                success:function (result) {
                    if(result){
                        if(result.code>=0){
                            layer.msg(result.message, {icon: 1});
                            $('#chat_text').val('');
                            $('#chat_reply').hide();
                            $('#chat_pid').val('0');

                            that.pageCallback(1);
                        }else{
                            layer.msg(result.message, {icon: 5});
                        }
                    }
                }
            });
        },


        pageCallback:function (pn) {
            var options=this.options;
            var that=this;

            var $list=$('#chat_container');
            var html='';

            J.getJSON(options.load_url,{pageSize:options.pageSize,pageNo:pn},function (result) {
                $('#chat_count').html(result.totalElements);

                //对评论列表数据轮询，
                J.each(result.content,function (index,element) {
                    var item=options.onload.call(that,index,element);
                    html+=item;
                });

                $list.empty().append(html);

                if(result.content.length==0){
                    $list.append('<li><p>还没有评论, 快来占沙发吧!</p></li>');
                }

                // if(result.size<1){
                //     $list.append('<li><p>还没有评论, 快来占沙发吧!</p></li>');
                // }

            });
        }
    };

    exports.init=function (options) {
        Comment.init(options);
    }

});


