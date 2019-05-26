


define('markdown', ['codemirror-css','codemirror-theme', 'codemirror', 'marked'], function (require, exports, module) {

    require.async(['codemirror-markdown', 'codemirror-keymap'],function () {
        console.log("codemirror-markdown,加载完毕....");

        require.async('editingmarkdown',function (MdEditor) {
            MdEditor.initEditor();
        })
    });

});