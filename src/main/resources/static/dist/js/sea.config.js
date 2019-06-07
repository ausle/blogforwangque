seajs.config({
    base: _MTONS.BASE_PATH,
    alias: {

        /*js/modules为我自己写的js*/


        /**/
        'main':'dist/js/modules/main',
        'authc': 'dist/js/modules/authc',
        'comment': 'dist/js/modules/comment',
        'plugins': 'dist/js/modules/plugins',
        'view':'dist/js/modules/view',
        'post': 'dist/js/modules/post',/*发布文章*/


        'validate':'dist/js/modules/validate',


        /*写markdown*/
        'markdown': 'dist/js/modules/markdown',
        'editingmarkdown': 'dist/js/modules/editingmarkdown',



        'tagsinput': 'vendors/bootstrap-tagsinput/bootstrap-tagsinput',


        /*一键分享工具*/
        'share': 'vendors/share.js/js/social-share.min.js',
        'share-css': 'vendors/share.js/css/share.min.css',

        /*highlight.pack.js可以实现代码高亮，github.css是具体的样式*/
        'highlight':'vendors/highlight/highlight.pack.js',
        'highlight-css': 'vendors/highlight/styles/github.css',


        /*jquery-validation*/
        'validation': 'vendors/jquery-validation/jquery.validate.min.js',
        'validation-additional': 'vendors/jquery-validation/additional-methods.js',
        'validation-localization': 'vendors/jquery-validation/localization/messages_zh.min.js',/*提示文字本地化*/


        /*表情符号插件*/
        'owo': 'vendors/owo/OwO.min.js',
        'owo-css': 'vendors/owo/OwO.min.css',

        //codecodemirror
        'codemirror': 'vendors/codemirror/lib/codemirror.js',
        'codemirror-css': 'vendors/codemirror/lib/codemirror.css',
        'codemirror-markdown': 'vendors/codemirror/mode/markdown/markdown',
        'codemirror-theme': 'vendors/codemirror/theme/idea.css',
        'codemirror-keymap': 'vendors/codemirror/keymap/sublime',

        //把markdown编译为HTML
        'marked': 'vendors/marked/marked.min',
    },

    // 路径配置  '/'
    paths: {
        'vendors': _MTONS.BASE_PATH + '/dist/vendors',
        'dist': _MTONS.BASE_PATH + '/dist'
    },

    // 变量配置
    vars: {
        'locale': 'zh-cn'
    },

    charset: 'utf-8',

    debug: false
});


console.log("seaconfig init");

// var __SEAJS_FILE_VERSION = '?v=1.3';
//
// seajs.on('fetch', function(data) {
// 	if (!data.uri) {
// 		return ;
// 	}
//
// 	if (data.uri.indexOf(app.mainScript) > 0) {
// 		return ;
// 	}
//
//    if (/\:\/\/.*?\/assets\/libs\/[^(common)]/.test(data.uri)) {
//        return ;
//    }
//
//    data.requestUri = data.uri + __SEAJS_FILE_VERSION;
//
// });
//
// seajs.on('define', function(data) {
// 	if (data.uri.lastIndexOf(__SEAJS_FILE_VERSION) > 0) {
// 	    data.uri = data.uri.replace(__SEAJS_FILE_VERSION, '');
// 	}
// });