<#macro layout title keywords description>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'/>
    <![endif]-->

    <#--开发调试时禁用缓存-->
<#--
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
-->


    <meta name="keywords" content="mtons, ${keywords?default(options['site_keywords'])}">
    <meta name="description" content="${description?default(options['site_description'])}">
    <title>${title?default(options['site_name'])}</title>


    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>

<#--
    <link href="${base}/dist/css/style.css" rel="stylesheet"/>
-->

    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>


    <#--theme style-->
    <link rel="stylesheet" href="${base}/dist/css/AdminLTE.min.css">
    <#--skin-->
    <link rel="stylesheet" href="${base}/dist/css/skins/_all-skins.min.css">


    <script src="${base}/dist/js/jquery.min.js"></script>
    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>


    <script src="${base}/dist/js/adminlte.min.js"></script>


    <#--都用于设置网站图标-->
    <#--apple-touch-icon-precomposed,apple设备添加网站时,默认显示网站截图。设置该属性后,显示的是定义的图标-->
    <link rel="apple-touch-icon-precomposed" href="${options['site_favicon']}" />
    <link rel="shortcut icon" href="${options['site_favicon']}" />
</head>

<body class="hold-transition skin-blue sidebar-mini">


    <header class="main-header">

        <a href="${base}/index" class="logo" target="_blank">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>${options['site_name']}</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>${options['site_name']}</b></span>
        </a>

        <nav class="navbar navbar-static-top">

            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>


            <div class="navbar-custom-menu">

                <ul class="nav navbar-nav">

                    <#--user account-->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${base}/dist/images/default_head.png" class="user-image" alt="User Image">
                            <span class="hidden-xs">Admin</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="${base}/dist/images/default_head.png" class="img-circle" alt="User Image">
                                <p>
                                    Admin
                                </p>
                            </li>

                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">个人资料</a>
                                </div>
                                <div class="pull-right">
                                    <a href="#" class="btn btn-default btn-flat">退出</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>

            </div>
        </nav>
    </header>


    <#--left siderbar-->
    <aside class="main-sidebar">

        <section class="sidebar">
            <!-- Sidebar user panel -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="${base}/dist/images/default_head.png" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>Admin</p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>


            <script type="text/javascript">

                $(function () {
//                    $('.sidebar-menu li>a').on('click',function () {
//                        console.log("click--->"+this.href);
//                        var $parent=$(this).parent().addClass("active");
//
//                        //find方法找寻的是该集合的子节点。而不是其本身
//
//                        $parent.siblings().find("li").removeClass("active");
//                    });

                    $('.sidebar-menu a').each(function () {
//                        console.log("href--->"+this.href);
//                        console.log("window--->"+window.location.href);
                        if(this.href===window.location.href){

                            $(this).parent().addClass("active");

                        }else{
                            $(this).parent().removeClass("active");
                        }

                    })


                })


            </script>


            <#--sidebar menu-->
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">MENUS</li>

                <li class="active">
                    <a href="${base}/admin" >
                        <i class="fa fa-dashboard"></i> <span>仪表盘</span>
                    </a>
                </li>

                <@menus>
                    <#list menus as menu>
                        <li><a href="${base}/${menu.url}"><i class="${menu.icon}"></i><span>${menu.name}</span></a></li>
                    </#list>
                </@menus>
            </ul>
        </section>
    </aside>

    <div class="content-wrapper">
        <#nested>
    </div>


</body>
</html>
</#macro>



