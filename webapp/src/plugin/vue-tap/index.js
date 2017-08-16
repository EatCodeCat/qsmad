export default (function(){


    var vueTap = {};
    vueTap.install = function(Vue){

        var isMove = false;
        var startTime = 0;
        var timeStamp = 0;
        var tapInfo = {};
        var funcMap = new Map ();


        function isPc (){
            var uaInfo = navigator.userAgent;
            var agents = [ "Android", "iPhone", "Windows Phone", "iPad", "iPod" ];
            var flag = true;
            for ( var i = 0; i < agents.length; i++ ) {
                if ( uaInfo.indexOf (agents[ i ]) > 0 ) {
                    flag = false;
                    break;
                }
            }
            return flag;
        }

        function doModifiers (e, modifiers){
            if ( !modifiers ) return

            if ( modifiers.prevent ) {
                e.preventDefault ();
            }
            if ( modifiers.stop ) {
                e.stopPropagation ();
            }

        }

        function clickHandle (e){
            doModifiers (e, this.modifiers)
            this.callback && this.callback.apply (this, this.params.concat ([ e ]));
        }

        function touchstart (e){
            doModifiers (e, this.modifiers)
            timeStamp = e.timeStamp;
            startTime = Date.now ();
            var touch = e.touches[ 0 ];
            tapInfo.x1 = touch.pageX;
            tapInfo.y1 = touch.pageY;
        }

        function touchmove (e){
            doModifiers (e, this.modifiers)

            if ( !e.touches ) return;

            var touch = e.touches[ 0 ];
            tapInfo.x2 = touch.pageX;
            tapInfo.y2 = touch.pageY;
            if ( (tapInfo.x2 && Math.abs (tapInfo.x1 - tapInfo.x2) > 8) ||
                (tapInfo.y2 && Math.abs (tapInfo.y1 - tapInfo.y2) > 8) ) {
                isMove = true;
            } else {
                isMove = false;
            }
        }

        function touchend (e){
            doModifiers (e, this.modifiers)
            if ( !e.changedTouches ) return;
            if ( !isMove && (Date.now () - startTime) < 300 ) {
                /*调用 callback*/
                setTimeout (() =>{
                    this.callback && this.callback.apply (this, this.params.concat ([ e ]));
                }, 0)

            }
            /*重置 参数*/
            isMove = false;
            startTime = 0;
        }


        function bindEvent (dom, binding){

            var callback = function(){};
            var value = binding.value;

            if ( typeof value == 'function' ) {
                callback = value;
            } else if ( typeof value.handler == 'function' ) {
                callback = value.handler;
            }
            var modifiers = binding.modifiers;
            var params = binding.value.params || [];


            if ( isPc () ) {
                var clickFunc = clickHandle.bind ({
                    value: value,
                    callback: callback,
                    modifiers: modifiers,
                    params: params
                });
                funcMap.set (dom, {click: clickFunc});
                dom.addEventListener ('click', clickFunc, false);
            } else {
                var start = touchstart.bind ({
                    value: value,
                    callback: callback,
                    modifiers: modifiers,
                    params: params
                })
                var move = touchmove.bind ({
                    value: value,
                    callback: callback,
                    modifiers: modifiers,
                    params: params
                })
                var end = touchend.bind ({
                    value: value,
                    callback: callback,
                    modifiers: modifiers,
                    params: params
                })

                funcMap.set (dom, {touchstart: start, touchmove: move, touchend: end});

                dom.addEventListener ('touchstart', start, false);
                dom.addEventListener ('touchmove', move, false);
                dom.addEventListener ('touchend', end, false);
            }
        }

        function unbindEvent (el){
            var func =  funcMap.get(el)
            el.removeEventListener ('touchstart', func.touchstart, false);
            el.removeEventListener ('touchmove', func.touchmove, false);
            el.removeEventListener ('touchend', func.touchend, false);
            el.removeEventListener ('click', func.click, false);
        }

        Vue.directive ('tap', {
            bind: function(dom, binding){


                bindEvent (dom, binding);

            },
            inserted: function(dom, binding){
            },
            update: function(dom, binding){
                unbindEvent (dom);

                bindEvent (dom, binding);


            },
            unbind: function(el){

                unbindEvent (el);
            }
        });

    }
    return vueTap;
}) ();
