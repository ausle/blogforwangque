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
                        <img class="img-circle" src="${base}/dist/images/default-head.png">
                        <span>${profile.name}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a>我的主页</a></li>
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
    $('.site-header').autoHidingNavbar();
</script>

