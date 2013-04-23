(function ($) {
    $(function () {
        App.registerImage("routerImage", "/assets/images/topology/routerImage.png");
        $(".topology .content").topology();
    });

    $.fn.topology = function () {
        var box = new twaver.ElementBox();
        var autoLayouter = new twaver.layout.AutoLayouter(box);
        var network = new twaver.network.Network(box);

        function render(el) {
            var view = network.getView();
            $(view).addClass("fill");
            $(view).appendTo(el);
            autoLayouter.doLayout("hierarchic");
        }

        function load() {
            for (var k = 0; k < 2; k++) {
                var ip = "192.168." + k + ".";
                var count = 0;
                var root = new twaver.Node();
                root.setName(ip + count++);
                root.setImage("routerImage");
                box.add(root);

                for (var i = 0; i < 3; i++) {
                    var iNode = new twaver.Node();
                    iNode.setName(ip + count++);
                    iNode.setImage("routerImage");
                    box.add(iNode);
                    var link = new twaver.Link(root, iNode);
                    link.setStyle('link.width', 2);
                    link.setStyle('link.color', '#00FF00');
                    box.add(link);
                    for (var j = 0; j < 9; j++) {
                        var jNode = new twaver.Node();
                        jNode.setName(ip + count++);
//                        if (j % 3 == 0) {
//                            jNode.getAlarmState().increaseNewAlarm(demo.Util.randomNonClearedSeverity());
//                        }
                        box.add(jNode);
                        link = new twaver.Link(iNode, jNode);
                        link.setStyle('link.width', 2);
                        link.setStyle('link.color', '#00FF00');
                        box.add(link);
                    }
                }
            }
        }

        function bindAction() {
            $(".topology").delegate(".action", "click", function (ev) {
                ev.preventDefault();
                var ac = $(this).attr("action");
                if (ac) {
                    actions["handle_" + ac]();
                }
            });
            //noinspection JSUnusedGlobalSymbols
            var actions = {
                handle_pan: function () {
                    if (network.isPan)
                        network.setDefaultInteractions();
                    else {
                        network.setPanInteractions();
                    }
                    network.isPan = !network.isPan;
                },
                handle_zoom_in: function () {
                    network.zoomIn();
                },
                handle_zoom_out: function () {
                    network.zoomOut();
                },
                handle_zoom_reset: function () {
                    network.zoomReset();
                },
                handle_full_screen: function () {
                    App.fullScreen($(".topology").get(0));
                },
                handle_layout_round: function () {
                    autoLayouter.doLayout("round");
                },
                handle_layout_symmetry: function () {
                    autoLayouter.doLayout("symmetry");
                },
                handle_layout_hierarchic: function () {
                    autoLayouter.doLayout("hierarchic");
                }
            }
        }

        function setupContext() {
            var contextMenu = new twaver.controls.PopupMenu(network);
            contextMenu.onMenuShowing = function (t) {
                var target = network.getElementAt(t);
                if (target instanceof twaver.Node)
                    contextMenu.setMenuItems([
                        {label: "设备属性"},
                        {separator: true},
                        {label: "当前告警"},
                        {label: "运行统计"},
                        {label: "数据记录"},
                        {separator: true},
                        {label: "删除设备"}
                    ]);
                else if (target instanceof twaver.Link)
                    contextMenu.setMenuItems([
                        {label: "线路属性"},
                        {separator: true},
                        {label: "当前告警"},
                        {label: "运行统计"},
                        {label: "数据记录"},
                        {separator: true},
                        {label: "删除线路"}
                    ]);
                else
                    contextMenu.setMenuItems([
                        {label: "查找设备", action: "find_device"},
                        {label: "显示过滤", action: "display_filter"}
                    ]);
                return true;
            };
            contextMenu.onAction = function (t) {
                alert(t.label);
            }

        }

        return this.each(function () {
            load();
            render(this);
            bindAction();
            setupContext();
        });
    }

})(jQuery);