seajs.config({
    base: _MTONS.BASE_PATH,
    alias: {


        'main':'dist/js/main',


        'post': 'dist/js/modules/post',
        'tagsinput': 'vendors/bootstrap-tagsinput/bootstrap-tagsinput',



        'markdown': 'dist/js/modules/markdown',
        'editingmarkdown': 'dist/js/editingmarkdown',

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