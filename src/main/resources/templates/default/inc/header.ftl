<#--login modal-->
<div class="modal fade" id="login_alert" tabindex="-1" role="dialog"
     <#--引用模态窗口标题-->aria-labelledby="myModalLabel"
     <#--保持modal窗口不可见-->aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">请登录</h4>
            </div>

            <div class="modal-body">
                    <div class="form-group">
                        <label class="control-label" for="username">账号</label>
                        <input id="ajax_login_username" class="form-control" name="username" type="text" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="password">密码</label>
                        <input id="ajax_login_password" class="form-control" name="password" type="password" required>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success btn-block" id="ajax_login_submit">
                            登录 Use it
                        </button>
                    </div>
                    <div class="form-group">
                        <div id="ajax_login_message" class="text-danger"></div>
                    </div>
            </div>
            <#--<div class="modal-footer">-->
                <#--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>-->
                <#--<button type="button" class="btn btn-primary">登录</button>-->
            <#--</div>-->
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>














<nav class="navbar navbar-default navbar-dark bg-primary site-header justify-content-center">

    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#example-navbar-collapse">
            <span class="sr-only">切换导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <div class="navbar-brand">
            <a href="${base}/" class="site_name">${options['site_name']}</a>
        </div>
    </div>

    <div class="collapse navbar-collapse" id="example-navbar-collapse">

        <ul class="nav navbar-nav navbar-right">
            <li class="hidden-xs hidden-sm">
                <#--navbar-form有一些padding值，会使其垂直居中-->
                <form class="navbar-form navbar-left" method="get">
                    <div class="form-group">
                        <input class="form-control search-input" placeholder="搜索" name="kw" type="text" value="${kw}">
                    </div>
                </form>
            </li>

            <#if profile??>
                <li>
                    <a href="${base}/post/editing"><i class="icon-note icon">写文章</i></a>
                </li>

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">
                        <img class="img-circle" src="${base}/dist/images/default_head.png">
                        <span>${profile.name}</span>
                    </a>
                    <ul class="dropdown-menu">

                         <@shiro.hasAdminPermission name="admin">
                             <li><a href="${base}/admin">后台管理</a></li>
                         </@shiro.hasAdminPermission>


                        <li><a href="${base}/logout">退出</a></li>
                    </ul>
                </li>

                <#else>
                <li><a href="${base}/login" class="btn-default btn-sm">登录</a></li>
                <li><a href="${base}/register" class="btn-default btn-sm">注册</a></li>
            </#if>
        </ul>
    </div>
</nav>

<script type="text/javascript">
    seajs.use('main',function () {
        $('.site-header').autoHidingNavbar();
    });
</script>

