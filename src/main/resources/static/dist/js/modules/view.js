


define(function (require,exports,module) {

    require('share-css');
    require('share');

    require.async(['highlight','highlight-css'],function () {
        //引入样式后进行初始化
        hljs.initHighlightingOnLoad();

        $('pre').each(function(i, block) {
            hljs.highlightBlock(block);
        });
    });

});