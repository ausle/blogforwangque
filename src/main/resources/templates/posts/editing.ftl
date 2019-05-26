
<#include "/inc/layout.ftl"/>


<@layout "编辑文章">


    <form id="submitForm" action="${base}/post/submit" method="post" enctype="multipart/form-data">

        <input type="hidden" name="status" value="${view.status!0}">
        <#if view??>
            <input type="hidden" name="id" value="${view.id}"/>
            <input type="hidden" name="authorId" value="${view.authorId}"/>
        </#if>
        <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}"/>


        <div class="row">
            <div class="col-md-8 col-sm-8">
                <div class="form-group">
                    <input type="text" class="form-control" name="title" maxlength="128" value="${view.title}"
                           placeholder="请输入标题" required>
                </div>

                <#include "/posts/markdown.ftl"/>
            </div>

            <div class="col-md-4 col-sm-4">
                <div class="upload-head-img" id="thumbnail_image">
                    <div class="upload-btn">
                        <label>
                            <span>点击选择一张图片</span>
                            <input class="upload-input" type="file" name="file" accept="image/*" title="点击添加图片">
                        </label>
                    </div>
                </div>


                <div class="form-group">
                    <select class="form-control" name="channelId" required>
                        <option value="">请选择文章类型</option>
                        <#list channels as row>
                            <option value="${row.id}" <#if (row.id==view.channelId)>selected</#if>>${row.name}</option>
                        </#list>
                    </select>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">标签(用逗号或空格分隔)</h3>
                    </div>
                    <div class="panel-body">
                        <input type="text" id="tags" name="tags" class="form-control" value="${view.tags}"
                               style="width: 100%"
                               placeholder="添加标签(最多4个)">
                    </div>
                </div>


                <div class="form-group">
                    <div class="text-right">
                        <button type="button" data-status="0" class="btn btn-primary" event="post_submit"
                                style="padding-left: 30px; padding-right: 30px;">发布</button>
                    </div>
                </div>

            </div>

        </div>
    </form>


    <script type="text/javascript">
        seajs.use('post',function (post) {
            post.init();
        });
    </script>
</@layout>