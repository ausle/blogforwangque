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
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>


    <meta name="keywords" content="望雀, ${keywords?default(options['site_keywords'])}">
    <meta name="description" content="${description?default(options['site_description'])}">
    <title>${title?default(options['site_name'])}</title>


    <link href="${base}/dist/vendors/pace/themes/pace-theme-minimal.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${base}/dist/css/style.css" rel="stylesheet"/>
    <link href="${base}/dist/css/editor.css" rel="stylesheet"/>
    <link href="${base}/dist/css/plugins.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/simple-line-icons/css/simple-line-icons.css" rel="stylesheet"/>
    <link href="${base}/dist/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>


    <#--pace 进度插件-->
    <script src="${base}/dist/vendors/pace/pace.min.js"></script>
    <script src="${base}/dist/js/jquery.min.js"></script>
    <#--layer web弹层组件-->
    <script src="${base}/dist/vendors/layer/layer.js"></script>
    <script src="${base}/dist/vendors/bootstrap/js/bootstrap.min.js"></script>


    <script type="text/javascript">
        var _MTONS = _MTONS || {};
        _MTONS.BASE_PATH = '${base}';
        _MTONS.LOGIN_TOKEN = '${profile.id}';
    </script>


    <#--sea.js-->
    <script src="${base}/dist/js/sea.js"></script>
    <script src="${base}/dist/js/sea.config.js"></script>

<#--
    <script src="${base}/dist/js/modules/autohidingnavbar.js"></script>
-->



    <#--都用于设置网站图标-->
    <#--apple-touch-icon-precomposed,apple设备添加网站时,默认显示网站截图。设置该属性后,显示的是定义的图标-->
    <#--<link rel="apple-touch-icon-precomposed" href="<@resource src=options['site_favicon']/>"/>-->
    <#--<link rel="shortcut icon" href="<@resource src=options['site_favicon']/>"/>-->
</head>

<body>


    <#include "/default/inc/header.ftl"/>

    <div class="wrap">
        <div class="container">
            <#nested>
        </div>
    </div>

    <#--<#include "/default/inc/footer.ftl"/>-->



</body>
</html>
</#macro>



