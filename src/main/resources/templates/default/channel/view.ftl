<#include "/default/inc/layout.ftl"/>

<#assign title = view.title + ' - ' + options['site_name'] />
<#assign keywords = view.keywords?default(options['site_keywords']) />
<#assign description = view.description?default(options['site_description']) />

<#--文章展示页面-->
<@layout title keywords description>
    <div class="row">

        <div class="col-md-12 col-sm-12">
                <div class="post-view-title">
                    <ul class="list-inline view-title">
                        <li class="breadcrumb-item">
                            <i class="fa fa-home mr-1"></i><a href="/">首页</a>
                        </li>
                        <li class="breadcrumb-item">
                            <a href="${base}/?channelId=${view.channel.id}">${view.channel.name}</a>
                        </li>
                        <li class="breadcrumb-item">
                            ${view.title}
                        </li>
                    </ul>
                </div>

                <div class="post-view-content">
                        <h1 class="text-info">${view.title}</h1><hr>

                        <div class="extra">
                            <span>${view.created}</span>
                            <span>阅读 ${view.views}</span>
                            <a style="color: red;">评论 ${view.comments}</a>
                        </div>

                        <div class="markdown-body">
                            ${view.content}
                        </div>

                        <div class="tag-cloud">
                                <#list tags as tag>
                                            <a class="tags" href="${base}/?tagId=${tag.id}">${tag.name}</a>
                                </#list>
                        </div>
                </div>
        </div>





        <style type="text/css">

            .chats {
                margin: 20px 0px;
                background-color: #fff;
                /* border: 1px solid #ebebeb; */
                padding: 10px;
            }

            .chat_head {
                border-bottom: 1px solid #e5e5e5;
            }

            .chat_head h4{
                font-size: 15px;
            }


            .cbox-title{
                font-size: 14px;
                padding: 14px 0px 10px 0px;
            }

            .cbox-post {
                border: 1px solid #ccc;
            }

            .cbox-input #chat_text {
                border-style: none;
                display: block;
                width: 100%;
                padding: 6px;
                font-size: 14px;
                line-height: 1.42857143;
                color: #555;
                overflow-y: auto;
                overflow-x: hidden;
                resize: none;
            }

            .cbox-ats.clearfix {
                padding: 0;
                /*justify-content: space-between;*/
                /*display: flex;*/
                border-top: 1px solid #ccc;
            }

            .ats-func{
                float: left;
            }

            .ats-issue{
                float: right;
            }
        </style>


        <#--comment module-->
        <div class="col-md-8">
            <div class="chats">
                <div class="chat_head">
                    <h4>全部评论: <i id="chat_count">0</i> 条</h4>
                </div>

                <ul id="chat_container">

                </ul>

                <div class="chat_post">
                    <div class="cbox-title">
                        我有话说:
                        <span id="chat_reply" style="display:none;">@<i id="chat_to"></i></span>
                    </div>

                    <div class="cbox-post">
                        <div class="cbox-input">
                            <textarea id="chat_text" rows="3" placeholder="请输入评论内容"></textarea>
                            <input type="hidden" value="0" name="chat_pid" id="chat_pid"/>
                        </div>

                        <div class="cbox-ats clearfix">
                            <div class="ats-func">
                                <div class="OwO" id="face-btn"></div>
                            </div>
                            <div class="ats-issue">
                                <button id="btn-chat" class="btn btn-success btn-sm bt">发送</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <style type="text/css">

        #chat_container{
            padding: 0px;
            list-style: none;
            margin-bottom: 20px;
        }

        #chat_container>.chat{
            display: flex;
            flex-direction: row;
            padding: 10px 0px;
            border-bottom: 0.5px solid #ededed;
        }

        .chat .chat_head_img{
            width: 35px;
            height: 35px;
            flex:1;
        }

        .chat .chat_body{
            flex:1;
            margin-left: 20px;
        }

        .chat_body .chat_body_head{
            display: flex;
            justify-content: space-between;
        }

        .chat_body_head span:first-child{
            color: #1f8902;
            font-size: 14px;
        }

        .chat_body_head span:last-child{
            font-size: 15px;
            color: #b4b4b4;
            margin-left: 5px;
        }

        .chat_body_head>a{
            color: #ff8a00;
            font-size: 14px;
            height: 20px;
            cursor: pointer;
            text-decoration: none;
            background-color: transparent;
        }

        /*.chat_body .chat_line {*/
            /*border: 0.5px solid #dedede;*/
            /*margin-top: 10px;*/
        /*}*/

        .chat_p .quoto{
            background: #f4f4f4;
            padding: 10px;
        }

        .chat_p .quoto span:first-child{
            color: #1f8902;
            font-size: 14px;

        }

    </style>


    <script type="text/javascript" id="template_chat">
        <li class="chat">
                <a><img class="chat_head_img" src="{1}"></a>
                <div class="chat_body">
                        <div class="chat_body_head">
                            <div>
                                <span>{2}</span>
                                <span>{3}</span>
                            </div>
                            <a href="javascript:void(0);" onclick="goto('{5}','{2}')">[回复]</a>
                        </div>

                        <div class="chat_p">
                            <div class="chat_pct">{4}</div>
                            {6}
                        </div>

                </div>
        </li>
    </script>



    <script type="text/javascript">
        function goto(pid,user) {
            //scrollTO 评论框
            document.getElementById('chat_text').scrollIntoView();

            //清空评论框
            $('#chat_text').focus();
            $('#chat_text').val('');


            $('#chat_to').text(user);
            $('#chat_pid').val(pid);

            $('#chat_reply').show();
        }



        var template=$('#template_chat')[0].text;

        seajs.use('comment',function (comment) {
            comment.init({
                load:true,
                load_url:'${base}/comment/list/${view.id}',    //加载评论列表
                post_url:'${base}/comment/submit',            //提交评论
                toId:'${view.id}',
                onload:function (index,element) {
                    var content=element.content;
                    var quoto='';
                    if(element.pid>0 && !(element.parent===null)){
                        var parent=element.parent;
                        var pContent=parent.content;
                        quoto=
                                "<div class='quoto'><span class='quoto_name'>"+'@'+parent.author.name+':'+"</span><span>"+pContent+"</span></div>";
                    }

                    var item=jQuery.formatblog(template,
                            element.author.id,          //发评论本人的id
                            element.author.avatar,
                            element.author.name,        //发评论本人的name
                            element.created,
                            content,
                            element.id,                //评论id
                            quoto
                    );

                    return item;
                }
            });
        });
    </script>


</@layout>