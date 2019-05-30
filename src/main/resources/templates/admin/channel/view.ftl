<#include "/admin/inc/layout.ftl"/>

<@layout>

<section class="content-header">
    <h1>
        栏目管理
    </h1>
    <ol class="breadcrumb">
        <li><a href="${base}/admin">首页</a></li>
        <li><a href="${base}/admin/channel/list">栏目管理</a></li>
        <li class="active">修改栏目</li>
    </ol>
</section>
<section class="content container-fluid">
    <div class="row">
        <div class="col-md-12">
            <form id="qForm" class="form-horizontal form-label-left" method="post" action="update">
                <#if view??>
                    <input type="hidden" name="id" value="${view.id}" />
                </#if>
                <input type="hidden" name="amount" value="${view.amount!0}">
                <input type="hidden" name="weight" value="${view.weight!0}">
                <input type="hidden" id="thumbnail" name="thumbnail" value="${view.thumbnail}">

                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">修改栏目</h3>
                    </div>


                    <div class="box-body">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">名称</label>
                            <div class="col-lg-3">
                                <input type="text" name="name" class="form-control" value="${view.name}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">唯一标识</label>
                            <div class="col-lg-3">
                                <input type="text" name="key" class="form-control" value="${view.key}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">导航栏状态</label>
                            <div class="col-lg-3">
                                <select name="status" class="form-control" data-select="${view.status}">
                                    <option value="0"
                                        <#if view.status??&&view.status==0>
                                            selected
                                        </#if>
                                    >隐藏</option>
                                    <option
                                        <#if view.status??&&view.status==1>
                                                selected
                                        </#if>
                                            value="1">显示</option>
                                </select>
                            </div>
                        </div>


                        <style type="text/css">
                            .upload-head-img{
                                background-image: url("/dist/images/btn_add_image.png");
                                background-repeat: no-repeat;
                                background-position: center;
                                background-attachment: scroll;/*背景图相对位置*/

                                border: 1px dashed #E6E6E6;
                                position: relative;
                                text-align: center;
                                height:150px;
                                margin: 0px 0px 20px;
                            }


                            .upload-btn{
                                background-color: #62c162;
                                line-height: 20px;
                                margin: 110px 20px 0px 20px;
                                font-weight: bold;
                                color: white;
                                position: relative;
                                border-radius: 10px;
                            }

                            .upload-btn:hover{
                                background-color: #5cb85c;
                            }

                            .upload-btn .upload-input{
                                position: absolute;
                                top: 0;
                                left: 0;
                                margin: 0;
                                border: solid transparent;
                                opacity: .0;
                                direction: ltr;
                                cursor: pointer;
                            }
                        </style>


                        <div class="form-group">
                            <label class="col-lg-2 control-label">缩略图</label>

                            <div class="col-lg-3">
                                <div class="upload-head-img" id="thumbnail_image"
                                    <#if view.thumbnail?? && view.thumbnail?trim?length gt 1>
                                        style="background: url(${base+view.thumbnail}) no-repeat scroll center"
                                    </#if>>
                                    <div class="upload-btn">
                                        <label>
                                            <span>点击选择一张图片</span>
                                            <input class="upload-input" type="file" name="file" accept="image/*" title="点击添加图片">
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="box-footer">
                        <button type="submit" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</section>



<script type="text/javascript">


    $('.upload-input').on("change",function () {
        var file=$(this)[0].files[0];
        if(!file){
            return false;
        }
        var form=new FormData();
        form.append("file",file);

        $.ajax({
            url: _MTONS.BASE_PATH + "/post/upload?crop=thumbnail_channel_size",
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





</script>




</@layout>