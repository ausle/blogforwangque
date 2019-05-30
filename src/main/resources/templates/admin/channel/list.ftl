<#include "/admin/inc/layout.ftl"/>
<@layout>


    <section class="content-header">
        <h1>
            栏目管理
        </h1>
        <ol class="breadcrumb">
            <li><a href="${base}/admin">首页</a></li>
            <li><a class="active">栏目管理</a></li>
        </ol>
    </section>

    <section class="content">

        <div class="row">
            <div class="col-md-12">
                <div class="box">

                    <div class="box-header with-border">
                        <h3 class="box-title">栏目列表</h3>
                        <div class="box-tools">
                            <a class="btn btn-default btn-sm" href="${base}/admin/channel/view">添加栏目</a>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="dataGrid">

                            <thead>
                                <tr>
                                    <th width="80">#</th>
                                    <th>名称</th>
                                    <th>Key</th>
                                    <th>状态</th>
                                    <th width="140">操作</th>
                                </tr>
                            </thead>

                            <tbody>
                                <#list channels as channel>

                                    <tr>
                                        <td>${channel.id}</td>
                                        <td>${channel.name}</td>
                                        <td>${channel.key}</td>

                                        <td>
                                            <#if (channel.status == 0)>
                                                隐藏
                                            <#else>
                                                显示
                                            </#if>
                                        </td>

                                        <td>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-default" data-id="${channel.id}" data-action="weight">置顶</a>
                                            <a href="view?id=${channel.id}" class="btn btn-xs btn-success">修改</a>
                                            <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${channel.id}"
                                               data-action="delete">删除</a>
                                        </td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>


    <script type="text/javascript">

        function reload(result) {
            if(result.code==0){
                if(result.message!=null&&result.message!=''){
                    layer.msg(result.message,{icon:1});
                    window.location.reload();
                }
            }else{
                layer.msg(result.message,{icon:2});
            }
        }

        //这里的回调函数直接写函数名。不要写成reload()
        function doUpdateWeight(channelId) {
            $.get('${base}/admin/channel/weight',{channelId:channelId},reload);
        }

        function doDeleteChannel(channelId) {
            $.get('${base}/admin/channel/delete',{id:channelId},reload);
        }



        $(function () {
            $("#dataGrid a[data-action='weight']").bind("click",function () {

                var that=$(this);

                layer.confirm('确定将该类别排在第一位吗?',{
                    btn:['确定','取消']
                },function () {
                    doUpdateWeight(that.attr('data-id'));

                },function () {
                });

            })


            $("#dataGrid a[data-action='delete']").bind("click",function () {

                var that=$(this);

                layer.confirm('确定删除该类别吗?',{
                    btn:['确定','取消']
                },function () {
                    doDeleteChannel(that.attr('data-id'));

                },function () {
                });
            })
        });
    </script>



</@layout>