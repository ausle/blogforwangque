/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/

define(function(require, exports, module) {
    require("plugins");

    var Main={
        init:function () {
            console.log("main init again......");
        }
    };

    console.log("main init......");

    exports.init=function () {
        Main.init();
    }
});