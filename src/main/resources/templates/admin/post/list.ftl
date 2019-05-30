<#include "/admin/inc/layout.ftl"/>


<@layout>

    <section class="content-header">
        <h1>
            文章管理
        </h1>
        <ol class="breadcrumb">
            <li><a href="${base}/admin">首页</a></li>
            <li><a class="active">文章管理</a></li>
        </ol>
    </section>


    <section  class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">

                        <div class="box-header with-border">
                            <h3 class="box-title">文章列表</h3>
                            <div class="box-tools">
                                <a class="btn btn-default btn-sm" href="${base}/admin/channel/view">新建</a>
                                <a class="btn btn-default btn-sm" href="${base}/admin/channel/view">批量删除</a>
                            </div>
                        </div>


                        <div class="box-body">

                            <form id="qForm" class="form-inline search-row">
                                <input type="hidden" name="pageNo" value="${page.number + 1}"/>
                                <div class="form-group">
                                    <select class="form-control" name="channelId" data-select="${channelId}">
                                        <option value="0">查询所有栏目</option>
                                        <#list channels as row>
                                            <option value="${row.id}">${row.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="title" class="form-control" value="${title}" placeholder="请输入标题关键字">
                                </div>
                                <button type="submit" class="btn btn-default">查询</button>
                            </form>

                            <div class="table-responsive">
                                <table class="table table-bordered table-striped" id="dataGrid">

                                    <thead>
                                        <tr>
                                            <th width="30"><input type="checkbox" class="checkall"></th>
                                            <th width="80">#</th>
                                            <th>文章标题</th>
                                            <th width="120">作者</th>
                                            <th width="100">发表日期</th>
                                            <th width="60">访问数</th>
                                                <#--<th width="80">状态</th>-->
                                            <th width="80">发布</th>
                                            <th width="180">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <#list page.content as row>
                                            <tr>
                                                <td>
                                                    <input type="checkbox" name="id" value="${row.id}">
                                                </td>

                                                <td>
                                                    <img src="${base}${row.thumbnail}" style="width: 90px; height: 50px;">
                                                </td>

                                                <td>
                                                    <a href="${base}/post/${row.id}" target="_blank">${row.title}</a>
                                                </td>
                                                <td>${row.author.username}</td>
                                                <td>${row.created}</td>
                                                <td><span class="label label-default">${row.views}</span></td>

                                                <td>
                                                    <#if (row.status = 0)>
                                                        <span class="label label-default">已发布</span>
                                                    </#if>
                                                    <#if (row.status = 1)>
                                                        <span class="label label-warning">草稿</span>
                                                    </#if>
                                                </td>

                                                <td>
                                                    <a href="${base}/post/editing?id=${row.id}" class="btn btn-xs btn-info" target="_blank">修改</a>
                                                    <a href="javascript:void(0);" class="btn btn-xs btn-primary" data-id="${row.id}" rel="delete">删除</a>
                                                </td>
                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
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


    function doDelete(ids) {
        $.get('${base}/admin/post/delete',{id:ids},reload);
    }



    $(function () {
        $("#dataGrid a[rel='delete']").bind("click",function () {

            var check_length=$("input[type=checkbox][name=id]:checked").length;

            if (check_length == 0) {
                layer.msg("请至少选择一项", {icon: 2});
                return false;
            }


            var ids = [];
            $("input[type=checkbox][name=id]:checked").each(function(){
                ids.push($(this).val());
            });


            layer.confirm('确定删除选择的文章吗?',{
                btn:['确定','取消']
            },function () {
                doDelete(ids);
            },function () {
            });
        });

    });
</script>





</@layout>