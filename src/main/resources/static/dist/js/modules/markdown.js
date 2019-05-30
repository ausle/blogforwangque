
define('markdown', ['codemirror-css','codemirror-theme', 'codemirror', 'marked'], function (require, exports, module) {
    require.async(['codemirror-markdown', 'codemirror-keymap'],function () {
        require.async('editingmarkdown',function (MdEditor) {
            MdEditor.initEditor();
        })
    });

});