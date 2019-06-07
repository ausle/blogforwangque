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
                                <a class="btn btn-default btn-sm" href="${base}/post/editing">新建</a>
                                <a class="btn btn-default btn-sm" href="javascript:;" data-action="batch_del">批量删除</a>
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
                                            <th width="30"><input type="checkbox" class="checkall" onclick="selectAll(this.checked)"></th>
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
                                                    <#if row.thumbnail?trim?length gt 1>
                                                        <img src="${row.thumbnail}" style="width: 90px; height: 50px;">
                                                    <#else>
                                                        <img src="${base}/dist/images/default_post.jpg" style="width: 90px; height: 50px;">
                                                    </#if>
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

                        <div class="box-footer">

                                <@utils.pager pageURI,page/>

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


    var selectAll=function(selectAllStatus) {
        if(selectAllStatus){
            $('input[type=checkbox][name=id]').each(function (index,element) {
                element.checked=true;
            })
        }else{
            $('input[type=checkbox][name=id]').each(function (index,element) {
                element.checked=false;
            })
        }
    }


    /*

        serialize创建以标准的URL编码表示的文本字符串。
        jquery.param方法创建数组或对象的序列化表示形式。

        ids=["18","19"]
        $.param({ids:ids})  会对参数转换为序列化的字符串。 id%5B%5D=18&id%5B%5D=17
        $.param({ids:ids},true) 规定使用浅层序列化，只进行参数序列化  id=18&id=17

     */

//    $(function () {
//        $('#qForm').submit(function () {
//            alert($(this).serialize());
//            return false;
//        });
//    })


    function doDelete(ids) {
        $.get('${base}/admin/post/delete',$.param({id:ids},true),reload);
    }


    $(function () {
        $("#dataGrid a[rel='delete']").bind("click",function () {
            var id=$(this).attr("data-id");
            layer.confirm('确定删除选择的文章吗?',{
                btn:['确定','取消']
            },function () {
                doDelete(id);
            },function () {
            });
        });


        $("a[data-action='batch_del']").bind("click",function () {

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