
/*实现对navbar下滑隐藏，上滑显示的效果*/


(function($,window,document) {

    var pluginName = 'autoHidingNavbar',
        $window = $(window),
        $document = $(document),
        _scrollThrottleTimer = null,
        _resizeThrottleTimer = null,
        _throttleDelay = 70,                //滑动的间隙
        _lastScrollHandlerRun = 0,          //
        _previousScrollTop = null,          //上一次滑动的ScrollTop
        _windowHeight = $window.height(),
        _visible = true,                    //navbar是否显示
        _hideOffset,
        defaults = {
            disableAutohide: false,         //是否禁止自动滑动
            showOnUpscroll: true,           //
            showOnBottom: true,             //
            hideOffset: 'auto',         // "auto" means the navbar height
            animationDuration: 500,
            navbarOffset: 0
        };


    function AutohidingNavbar(element,options) {
        this.element=$(element);
        //若extend只指定了一个参数，那么就合并到jquery中，作为jquery的成员
        //若有多个参数，则合并到一个目标上。下面的例子，defaults和options合并到定义的空对象上
        this.settings=$.extend({},defaults,options);

        this._defaults = defaults;
        this._name = pluginName;
        this.init();
    }


    function bindEvents(autohidingNavbar) {
        $document.on('scroll.'+pluginName,function () {
            //两次滑动的间隙大于_throttleDelay
            if(new Date().getTime()-_lastScrollHandlerRun>_throttleDelay){
                scrollHandler(autohidingNavbar);
            }else{

                clearTimeout(_scrollThrottleTimer);
                _scrollThrottleTimer = setTimeout(function() {
                    scrollHandler(autohidingNavbar);
                }, _throttleDelay);

            }
        });
    }


    function scrollHandler(autohidingNavbar) {

        if(autohidingNavbar.settings.disableAutohide){
            return;
        }

        _lastScrollHandlerRun=new Date().getTime();

        detectStatus(autohidingNavbar);
    }


    function detectStatus(autohidingNavbar) {
        var scrollTop=$window.scrollTop();
        var scrollDelta=scrollTop-_previousScrollTop;
        _previousScrollTop = scrollTop;

        //向下滑，隐藏navbar
        if(scrollDelta>0){
            //向下滑
            if(!_visible){
                //滑到最底部也显示
                if (autohidingNavbar.settings.showOnBottom && scrollTop + _windowHeight === $document.height()) {
                    // console.log("滑动了最底部了");
                    show(autohidingNavbar);
                }
                return;
            }

            // console.log("滑动的距离为："+scrollTop);
            // console.log('正在下滑'+"scrollDelta-->"+scrollDelta);

            if(scrollTop>=_hideOffset){
                hide(autohidingNavbar);
            }
        }else{
            //若已经显示则return
            if(_visible){
                return;
            }
            // console.log('正在上滑'+"scrollDelta-->"+scrollDelta);
            if(autohidingNavbar.settings.showOnUpscroll||scrollTop<=_hideOffset){
                show(autohidingNavbar);
            }
        }
    }


    function show(autohidingNavbar) {
        if(_visible){
           return;
        }

        /*
            $(selector).animate(styles,options)
            options中，queue指示是否在效果队列中放置动画。为false，动画立即开始
        */
        autohidingNavbar.element.removeClass('navbar-hidden').animate({
            top:0
        },{
            queue:false,
            duration:autohidingNavbar.settings.animationDuration
        });

        _visible=true;
        // console.log("显示动画已经执行....");
        autohidingNavbar.element.trigger('show.autohidingNavbar');
    }

    function hide(autohidingNavbar) {
        if(!_visible){
            return;
        }

        autohidingNavbar.element.addClass("navbar-hidden").animate({
            top:-1*parseInt(autohidingNavbar.element.css('height'),10)
        },{
            queue: false,
            duration: autohidingNavbar.settings.animationDuration
        });
        _visible=false;
        // console.log("隐藏动画已经执行....");
        autohidingNavbar.element.trigger('hide.autohidingNavbar');
    }


    AutohidingNavbar.prototype={

        init:function () {
            this.elements={
                navbar: this.element
            },

            //重新设置navbar滑动相关的属性
            this.setDisableAutohide(this.settings.disableAutohide);
            this.setShowOnUpscroll(this.settings.showOnUpscroll);
            this.setShowOnBottom(this.settings.showOnBottom);
            this.setHideOffset(this.settings.hideOffset);
            this.setAnimationDuration(this.settings.animationDuration);

            //_hideOffset为navbar的高度
            _hideOffset=this.settings.hideOffset==='auto'?parseInt(this.element.css('height'),10):
                this.settings.hideOffset;

            bindEvents(this);
            return this.element;
        },


        setDisableAutohide: function(value) {
            this.settings.disableAutohide = value;
            return this.element;
        },
        setShowOnUpscroll: function(value) {
            this.settings.showOnUpscroll = value;
            return this.element;
        },
        setShowOnBottom: function(value) {
            this.settings.showOnBottom = value;
            return this.element;
        },
        setHideOffset: function(value) {
            this.settings.hideOffset = value;
            return this.element;
        },
        setAnimationDuration: function(value) {
            this.settings.animationDuration = value;
            return this.element;
        },
    };



    /*为jquery原型对象添加方法*/
    $.fn.extend({
            autoHidingNavbar:function(options) {
                console.log("autoHidingNavbar invoke.....");
                if(options===undefined||typeof options ==='object'){
                    console.log(this==$);
                    this.each(function () {
                        if(!$.data(this,'plugin_'+pluginName)){
                            $.data(this,'plugin_'+pluginName,new AutohidingNavbar(this,options));
                        }
                    });
                }
            }
        }
    )











})(jQuery, window, document)

