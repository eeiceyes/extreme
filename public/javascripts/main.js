(function ($) {
    $(function(){
        $(document).ajaxSend(function(){
            $(".overlay").css("display", "block");
        });

        $(document).ajaxComplete(function(){
            $(".overlay").css("display", "none");
        });

        $("a[action='full_screen']").click(function(){
            App.fullScreen($(".layout-inner").get(0));
        });

        $(".gui-list").grid();

        $(".chosen").chosen();

        window.setTimeout(function(){
            $("#flashContainer").slideUp({duration:1000});
        }, 3000)

        var custom = {
            focusCleanup: false,

            wrapper: 'div',
            errorElement: 'span',

            highlight: function(element) {
                $(element).parents ('.control-group').removeClass ('success').addClass('error');
            },
            success: function(element) {
                $(element).parents ('.control-group').removeClass ('error').addClass('success');
                $(element).parents ('.controls:not(:has(.clean))').find ('div:last').before ('<div class="clean"></div>');
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parents ('.controls'));
            }

        };
        $("form.validation-required").validate(custom);
    });
})(jQuery);

App = {
    registerImage: function (name, url) {
        var image = new Image();
        image.src = url;
        image.onload = function () {
            twaver.Util.registerImage(name, image, image.width, image.height);
            image.onload = null;
        };
    },
    fullScreen: function (docElm) {
        var fullscreen = document.fullscreen || document.mozFullScreen || document.webkitIsFullScreen;

        if (!fullscreen) {
            if (docElm.requestFullscreen) {
                docElm.requestFullscreen();
            } else if (docElm.webkitRequestFullScreen) {
                docElm.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
            } else if (docElm.mozRequestFullScreen) {
                docElm.mozRequestFullScreen();
            }
            return true;
        } else {
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            }
            return false;
        }
    }
};

//$(function () {
//
//    var devs = ["100.100.100.10", "20.0.8.134"]
//    var i = 0;
//
//    function showAlarm() {
//        $.msgGrowl({
//            type: "warning", title: '告警', text: '设备' + devs[i++] + '的CPU阈值超过60%'
//        });
//    }
//
//    window.setTimeout(showAlarm, 3000);
//    window.setTimeout(showAlarm, 5000);
//
//});

