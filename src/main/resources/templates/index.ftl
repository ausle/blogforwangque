<#include "/inc/layout.ftl"/>
<@layout>

    <div class="row">
        <div class="col-xs-12 col-md-8">

            <div id="myCarousel" class="carousel slide">
                <!-- 轮播（Carousel）指标 -->
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>

                <!-- 轮播（Carousel）项目 -->
                <div class="carousel-inner">
                    <div class="item active carousel_img">
                        <img src="${base}/dist/images/girl.jpg" alt="First slide">
                    </div>
                    <div class="item carousel_img">
                        <img src="${base}/dist/images/girl.jpg" alt="Second slide">
                    </div>
                    <div class="item carousel_img">
                        <img src="${base}/dist/images/girl.jpg" alt="Third slide">
                    </div>
                </div>
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


    <#--class="time-sort"class="hot-sort"-->


            <div class="post-select">
                <div class="tab-sort">
                    <a <#if order == 'newest'> class="active"</#if>  href="?order=newest"><i class="fa fa-bars mr-1"></i> 时间排序</a>
                    <a  <#if order == 'hottest'> class="active"</#if> href="?order=hottest"><i class="fa fa-fire mr-1"></i> 热度排序</a>
                </div>

                <@post_contents channelId=channelId order=order pageNo=pageNo>
                    <#list results.content as result>

                        <div class="post-list">

                            <div class="post-item">
                                <div class="post-item-img">
                                    <a href="#" target="_blank"><img src="${result.thumbnail}?"></a>
                                </div>

                                <div class="post-item-body">
                                    <div class="head">
                                        <img class="avatar" src="${base}/dist/images/default_head.png">
                                        <span>ASULE</span>
                                        <span><i class="fa fa-calendar-times-o ml-2 mr-1"></i>${timeAgo(result.created)}</span>
                                    </div>

                                    <h2 class="title">
                                        <a href="#" target="_blank">
                                        ${result.title}
                                        </a>
                                    </h2>

                                    <p class="content">
                                         ${result.summary}
                                    </p>

                                    <div class="foot">
                                        <a href="#" target="_blank" class="tag">
                                            <i class="fa fa-book mr-1"></i>
                                            ${result.tags}
                                        </a>
                                        <span><i class="fa fa-eye ml-2 mr-1"></i>${result.views}</span>

                                        <a target="_blank" title="查看文章评论">
                                            <i class="fa fa-comments ml-2 mr-1"></i>${result.comments}</a>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </#list>
                </@post_contents>


                <div>
                    <@utils.pager requestURI,results/>
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
                    <div>
                        <img src="/dist/images/csdn.png">
                        <a href="https://blog.csdn.net/u012834186" target="_blank">CSDN</a>
                    </div>
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


            <#--标签-->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="fa fa-bars"></i>
                        标签
                    </h3>
                </div>

                <div class="panel-body">
                    <ul>
                        <li>CCC</li>
                        <li>DDD</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>





</@layout>

