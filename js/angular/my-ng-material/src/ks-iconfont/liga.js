/* A polyfill for browsers that don't support ligatures. */
/* The script tag referring to this file must be placed before the ending body tag. */

/* To provide support for elements dynamically added, this script adds
   method 'icomoonLiga' to the window object. You can pass element references to this method.
*/
(function () {
    'use strict';
    function supportsProperty(p) {
        var prefixes = ['Webkit', 'Moz', 'O', 'ms'],
            i,
            div = document.createElement('div'),
            ret = p in div.style;
        if (!ret) {
            p = p.charAt(0).toUpperCase() + p.substr(1);
            for (i = 0; i < prefixes.length; i += 1) {
                ret = prefixes[i] + p in div.style;
                if (ret) {
                    break;
                }
            }
        }
        return ret;
    }
    var icons;
    if (!supportsProperty('fontFeatureSettings')) {
        icons = {
            'arrow_left': '&#xe920;',
            'arrow_right': '&#xe91b;',
            'arrow_down': '&#xe90f;',
            'r-selected': '&#xe91a;',
            'r-unselected': '&#xe919;',
            'c-selected': '&#xe921;',
            'c-unselected': '&#xe922;',
            'selected': '&#xe91e;',
            'remind-success': '&#xe916;',
            'delete': '&#xe906;',
            'cancle': '&#xe915;',
            'close': '&#xe91f;',
            'trash-can-o': '&#xe90c;',
            'question': '&#xe92d;',
            'timing': '&#xe907;',
            'tag': '&#xe902;',
            'bell': '&#xe928;',
            'msg': '&#xe929;',
            'chat': '&#xe917;',
            'location': '&#xe90e;',
            'location-o': '&#xe92b;',
            'sort': '&#xe936;',
            'sort-asc': '&#xe91c;',
            'sort-desc': '&#xe91d;',
            'card': '&#xe911;',
            'card-r': '&#xe92e;',
            'kingsilk-word': '&#xe92f;',
            'kingsilk-logo': '&#xe925;',
            'gift': '&#xe93b;',
            'gift-o': '&#xe93e;',
            'order': '&#xe913;',
            'order-o': '&#xe93a;',
            'shopping-cart': '&#xe926;',
            'shopping-cart-o': '&#xe927;',
            'user': '&#xe914;',
            'user-o': '&#xe923;',
            'edit': '&#xe905;',
            'edit-o': '&#xe90d;',
            'coupon': '&#xe90b;',
            'coupon-o': '&#xe935;',
            'alipay': '&#xe908;',
            'weixin': '&#xe909;',
            'quilt': '&#xe930;',
            'more': '&#xe931;',
            'link': '&#xe933;',
            'star-o': '&#xe934;',
            'service': '&#xe937;',
            'rent-order': '&#xe938;',
            'done': '&#xe939;',
            'wallet': '&#xe93c;',
            'rent-word': '&#xe901;',
            'maintain': '&#xe93d;',
            'recovery': '&#xe903;',
            'delivery': '&#xe904;',
            'drawer': '&#xe932;',
            'invoice': '&#xe90a;',
            'phone': '&#xe910;',
            'handbag': '&#xe912;',
            'email': '&#xe918;',
            'search': '&#xe924;',
            'qrcode': '&#xe92a;',
            'bulb': '&#xe92c;',
          '0': 0
        };
        delete icons['0'];
        window.icomoonLiga = function (els) {
            var classes,
                el,
                i,
                innerHTML,
                key;
            els = els || document.getElementsByTagName('*');
            if (!els.length) {
                els = [els];
            }
            for (i = 0; ; i += 1) {
                el = els[i];
                if (!el) {
                    break;
                }
                classes = el.className;
                if (/ks-icon/.test(classes)) {
                    innerHTML = el.innerHTML;
                    if (innerHTML && innerHTML.length > 1) {
                        for (key in icons) {
                            if (icons.hasOwnProperty(key)) {
                                innerHTML = innerHTML.replace(new RegExp(key, 'g'), icons[key]);
                            }
                        }
                        el.innerHTML = innerHTML;
                    }
                }
            }
        };
        window.icomoonLiga();
    }
}());
