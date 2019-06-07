<#include "/admin/inc/layout.ftl"/>
<@layout>

    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            仪表盘
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i>首页</a></li>
            <li class="active">仪表盘</li>
        </ol>
    </section>


    <section class="content">
        <div class="row">
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-aqua">
                    <div class="inner">
                        <h3>${channels}</h3>
                        <p>栏目</p>
                    </div>
                    <div class="icon">
                        <i class="fa fa-bars"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>

            <div class="col-lg-3 col-xs-6">
                <div class="small-box bg-green">
                    <div class="inner">
                        <h3>${posts}</h3>
                        <p>文章</p>
                    </div>
                    <div class="icon">
                        <i class="fa fa-clone"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>

            <#--<div class="col-lg-3 col-xs-6">-->
                <#--<div class="small-box bg-yellow">-->
                    <#--<div class="inner">-->
                        <#--<h3>150</h3>-->
                        <#--<p>评论</p>-->
                    <#--</div>-->
                    <#--<div class="icon">-->
                        <#--<i class="fa fa-comments-o"></i>-->
                    <#--</div>-->
                    <#--<a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>-->
                <#--</div>-->
            <#--</div>-->

            <div class="col-lg-3 col-xs-6">
                <div class="small-box bg-red">
                    <div class="inner">
                        <h3>${users}</h3>
                        <p>用户</p>
                    </div>
                    <div class="icon">
                        <i class="fa fa-user"></i>
                    </div>
                    <a href="#" class="small-box-footer">更多<i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
        </div>
    </section>



</@layout>