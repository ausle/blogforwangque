<#include "/default/inc/layout.ftl"/>
<@layout>

    <div class="row">
        <div class="col-xs-12 col-md-8">

            <div id="myCarousel" class="carousel slide">



                <#--759*416-->




                    <@carousels>

                        <!-- 轮播（Carousel）指标 -->
                        <ol class="carousel-indicators">
                            <#list carousels.content as carousel>

                                <li data-target="#myCarousel" data-slide-to=${carousel_index}
                                    <#if carousel_index==0>
                                        class="active"
                                    </#if>></li>
                            </#list>
                        </ol>

                        <!-- 轮播（Carousel）项目 -->
                            <div class="carousel-inner">
                                <#list carousels.content as carousel>
                                    <div
                                        <#if carousel_index==0>
                                                class="item carousel_img active"
                                        <#else>
                                                class="item carousel_img"
                                        </#if>>
                                        <a href="${base}/post/${carousel.id}" target="_blank">
                                            <#if carousel.thumbnail?trim?length gt 1>
                                                <img src="${carousel.thumbnail}" style="width: 100%;height: 416px">
                                            <#else>
                                                <img src="${base}/dist/images/default_post.jpg" style="width: 100%;height: 416px">
                                            </#if>
                                        </a>
                                    </div>
                                </#list>
                            </div>
                    </@carousels>

                <script type="text/javascript">
                    /*开始轮播*/
                    $(function () {
                        $("#myCarousel").carousel('cycle');
                    });

                </script>

                <!-- 轮播（Carousel）导航 -->
                <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>


            <div class="post-select">
                <div class="tab-sort">
                    <a <#if order == 'newest'> class="active"</#if>
                            href=${sortURI}&order=newest><i class="fa fa-bars mr-1"></i> 时间排序</a>
                    <a  <#if order == 'hottest'> class="active"</#if> href=${sortURI}&order=hottest
                    ><i class="fa fa-fire mr-1"></i> 热度排序</a>
                </div>

                <@post_contents channelId=channelId order=order pageNo=pageNo tagId=tagId>
                    <#list results.content as result>

                        <div class="post-list">

                            <div class="post-item">
                                <div class="post-item-img">
                                    <a href="${base}/post/${result.id}" target="_blank">
                                        <#if result.thumbnail?trim?length gt 1>
                                            <img src="${result.thumbnail}">
                                            <#else>
                                            <img src="${base}/dist/images/default_post.jpg">
                                        </#if>
                                      </a>
                                </div>

                                <div class="post-item-body">
                                    <div class="head">
                                        <img class="avatar" src="${base}/dist/images/default_head.png">
                                        <span>${result.author.username}</span>
                                        <span><i class="fa fa-calendar-times-o ml-2 mr-1"></i>${timeAgo(result.created)}</span>
                                    </div>

                                    <h2 class="title">
                                        <a href="${base}/post/${result.id}" target="_blank">
                                            ${result.title}
                                        </a>
                                    </h2>

                                    <p class="content">
                                         ${result.summary}
                                    </p>

                                    <div class="foot">
                                        <a class="tag">
                                            <i class="fa fa-book mr-1"></i>
                                            ${result.tags}
                                        </a>
                                        <span><i class="fa fa-eye ml-2 mr-1"></i>${result.views}</span>

                                        <a title="查看文章评论" class="comment">
                                            <i class="fa fa-comments ml-2 mr-1"></i>${result.comments}</a>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </#list>
                </@post_contents>


                <div>
                    <@utils.pager pageURI,results/>
                </div>


            </div>
        </div>

        <div class="col-xs-12 col-md-4">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="fa fa-paper-plane"></i>
                        个人空间
                    </h3>
                </div>

                <div class="panel-body manual-site">
                    <div>
                        <img src="/dist/images/github.png">
                        <a href="https://github.com/ausle" target="_blank">Github</a>
                    </div>
                    <div>
                        <img src="/dist/images/weibo.png">
                        <a href="https://weibo.com/2378583930/profile?topnav=1&wvr=6" target="_blank">微博</a>
                    </div>
                    <#--<div>-->
                        <#--<img src="/dist/images/csdn.png">-->
                        <#--<a href="https://blog.csdn.net/u012834186" target="_blank">CSDN</a>-->
                    <#--</div>-->
                </div>
            </div>

            <div class="panel panel-default">

                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="fa fa-book"></i>
                        文章分类
                    </h3>
                </div>

                <div class="post-channel">
                    <ul class="list-channel">
                        <@channel_contents>
                            <#list channels as  channel >
                                <li class="list-channel-item">
                                    <a class="channel-name" href="?channelId=${channel.id}">${ channel.name}</a>
                                    <span class="channel-count">${channel.amount}</span>
                                </li>

                            </#list>

                        </@channel_contents>
                    </ul>
                </div>
            </div>

            <style type="text/css">
                .tag-right {
                    padding: 10px 15px;
                }
            </style>


            <#--标签-->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="fa fa-tags"></i>
                        标签
                    </h3>
                </div>

                <@tag_contents>

                    <div class="tag-cloud tag-right">
                        <#list tags as tag>
                            <a class="tags" href="?tagId=${tag.id}">${tag.name}</a>
                        </#list>
                    </div>

                </@tag_contents>


            </div>
        </div>
    </div>
</@layout>

