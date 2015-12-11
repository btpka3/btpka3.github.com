// http://sassmeister.com/

if (!$.cssHooks) {
    throw( new Error("jQuery 1.4.3 or above is required for this plugin to work") );
}


// -------------------------------------------- css3-flexbox-20090723
$.cssHooks["display"] = {
    set: function (elem, value) {
        var NAME = "display";
        if ("flex" !== value && "inline-flex" !== value) {
            elem.style["display"] = value;
            return;
        }
        var values;
        if ("box" === value) {
            values = ["-moz-box", "-webkit-box", "box"];
        }
        if ("flex" === value) {
            $(elem).css(NAME, "box");
            values = ["-webkit-flex", "flex"];
        }
        if ("inline-flex" === value) {
            $(elem).css(NAME, "box");
            values = ["-webkit-inline-flex", "inline-flex"];
        }

        $.each(values, function (i, value) {
            elem.style["display"] = value;
        });
    }
};

// ------------------ box

$.cssHooks["box-orient"] = {
    // 排列方向： 水平、垂直
    // box-orient: horizontal | vertical | inline-axis (default) | block-axis | inherit

    set: function (elem, value) {
        var alias = [
            "box-orient"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};

$.cssHooks["box-direction"] = {
    // 排列顺序： 顺序、逆序
    // box-direction: normal (default) | reverse | inherit

    set: function (elem, value) {
        var alias = [
            "box-direction"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};

$.cssHooks["box-align"] = {
    // 子元素垂直方向的对其方式： 顶端对齐、底部对其、垂直居中对齐、文字的baseline对其、拉伸（两端对齐）
    // box-align: start | end | center | baseline | stretch (default)

    set: function (elem, value) {
        var alias = [
            "box-align"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};


$.cssHooks["box-pack"] = {
    // 子元素对齐与间隔：起始位置（无间隔）、结束位置（无间隔），中间（无间隔），平均分布（两端无空白）
    // box-pack: start (default) | end | center | justify

    set: function (elem, value) {
        var alias = [
            "box-pack"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};


$.cssHooks["box-lines"] = {
    // 子元素是否允许多行
    // box-lines: single (default)| multiple

    set: function (elem, value) {
        var alias = [
            "box-pack"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};



// ------------------ children of box

$.cssHooks["box-ordinal-group"] = {
    // 自己的排列顺序：数值
    // box-ordinal-group: 1  (default)

    set: function (elem, value) {
        var alias = [
            "box-orient"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};

$.cssHooks["box-flex"] = {
    // 自己的伸缩性： 数值越大，就伸缩的越占空间。0.0代表不伸缩。
    // box-flex: 0.0 (default)

    set: function (elem, value) {
        var alias = [
            "box-align"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};

$.cssHooks["box-flex-group"] = {
    // 自己的伸缩性： 数值越大，就伸缩的越占空间。0.0代表不伸缩。
    // box-flex-group: 1 (default)

    set: function (elem, value) {
        var alias = [
            "box-align"
        ];

        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};


$.cssHooks["box-pack"] = {
    // box-pack : start|end|center|justify
    set: function (elem, value) {
        var alias = [
            "-moz-box-pack", "-webkit-box-pack", "-ms-flex-pack", "box-pack"
        ];
        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};

//////////////////////////////
$.cssHooks["justify-content"] = {
    // justify-content: flex-start | flex-end | center | space-between | space-around

    set: function (elem, value) {
        var NAME = "justify-content";
        var OLD_NAME = "box-pack";
        var OLD_VALUE_MAPPINGS = {
            "flex-start": "start",
            "flex-end": "end",
            "space-between": "justify"
        };
        $(elem).css(OLD_NAME, OLD_VALUE_MAPPINGS[value] ? OLD_VALUE_MAPPINGS[value] : value);

        var alias = [
            "-webkit-justify-content", "justify-content"
        ];

        if ("flex-start" === value) {
            elem.style["moz-box-pack"] = value;
            return;
        }
        $.each(alias, function (i, value) {
            elem.style[alias] = value;
        });
    }
};



