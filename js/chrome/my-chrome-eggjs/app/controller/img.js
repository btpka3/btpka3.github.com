'use strict';

const CDP = require('chrome-remote-interface');
const assert = require('assert');

module.exports = app => {

    function timeout(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    const defaultOptions = {
        //url: "https://btpka3.github.io/js/chrome/my-chrome-eggjs/app/public/test.html",
        url: "https://btpka3.github.io/js/chrome/my-chrome-eggjs/app/public/test.html",
        w: 320,
        h: 568,
        fullScreen: true,

        x: 0,    // viewPort : x
        y: 0,    // viewPort : x
        // scale: 2.0,

        timeout: 0,
        format: "png",
        referrer: "",
        // 微信
        userAgent: "Mozilla/5.0 (Linux; Android 6.0; Le X620 Build/HEXCNFN5902303291S; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043220 Safari/537.36 MicroMessenger/6.5.7.1041 NetType/4G Language/zh_CN"
    };


    function parseOptions(query) {
        const options = {};

        if (query.url) {
            options.url = query.url;
        }

        if (query.w) {
            let w = parseInt(query.w);
            assert.ok(!isNaN(w) && w >= 0 && w <= 10000000,
                `参数 w 必须是 [0, 10000000] 之间的整数。当前值是 : '${query.w}'`);
            options.w = w;
        }

        if (query.h) {
            let h = parseInt(query.h);
            assert.ok(!isNaN(h) && h >= 0 && h <= 10000000,
                `参数 h 必须是 [0, 10000000] 之间的整数。当前值是 : '${query.h}'`);
            options.h = h;
        }

        if (Object.getOwnPropertyNames(query).indexOf("fullScreen") >= 0) {
            let fullScreenStr = query.fullScreen;

            assert.ok(fullScreenStr === "true" || fullScreenStr === "false",
                `参数 fullScreen 必须是 'true' 或者 'false'。当前值是 : '${fullScreenStr}'`);

            options.fullScreen = "true" === fullScreenStr;
        }

        if (query.x) {
            let x = parseInt(query.x);
            assert.ok(!isNaN(x) && x >= 0,
                `参数 x 必须是大于等于0的整数。当前值是 : '${query.x}'`);
            options.x = x;
        }
        if (query.y) {
            let y = parseInt(query.y);
            assert.ok(!isNaN(y) && y >= 0,
                `参数 y 必须是大于等于0的整数。当前值是 : '${query.y}'`);
            options.y = y;
        }

        // if (query.scale) {
        //     let scale = parseFloat(query.scale);
        //     assert.ok(!isNaN(scale) && y > 0,
        //         `参数 scale 必须是大于0的浮点数。当前值是 : '${query.scale}'`);
        //     options.scale = scale;
        // }

        if (query.timeout) {
            let timeout = parseInt(query.timeout);
            assert.ok(!isNaN(timeout) && timeout >= 0,
                `参数 timeout 必须是大于等于0的整数。当前值是 : '${query.timeout}'`);
            options.timeout = timeout;
        }


        if (query.format) {
            let format = query.format;
            assert.ok(format === "png" || format === "jpeg",
                `参数 format 必须是 'png', 'jpeg'。当前值是 '${format}'`);
            options.format = format;
        }

        if (query.referrer) {
            options.referrer = query.referrer;
        }

        if (query.userAgent) {
            options.userAgent = query.userAgent;
        }

        return options;
    }

    function getMergedOptions(options) {
        return Object.assign({}, defaultOptions, options);
    }

    async function screenshot(client, options) {


        const opts = getMergedOptions(options);

        console.log("====opts:", opts);

        // Extract used DevTools domains.
        const {DOM, Emulation, Network, Page, Runtime} = client;

        // Enable events on domains we are interested in.
        await Page.enable();
        await DOM.enable();
        await Network.enable();
        await Network.setUserAgentOverride({userAgent: opts.userAgent});

        // Set up viewport resolution, etc.
        const deviceMetrics = {
            // required params
            width: opts.w,
            height: opts.h,
            deviceScaleFactor: 0,
            mobile: true,
            fitWindow: false,

            // optional params
            //scale: 1,
            screenWidth: opts.w,
            screenHeight: opts.h
        };
        console.log('======deviceMetrics : ', deviceMetrics);
        await Emulation.setDeviceMetricsOverride(deviceMetrics);
        console.log('======deviceMetrics end ');
        await Emulation.setVisibleSize({width: opts.w, height: opts.h});

        // Navigate to target page
        await Page.navigate({
            url: opts.url,
            referrer: opts.referrer
        });

        // Wait for page load event to take screenshot
        await Page.loadEventFired();

        // ------------- full screen
        if (opts.fullScreen) {
            const {root: {nodeId: documentNodeId}} = await DOM.getDocument();
            const {nodeId: bodyNodeId} = await DOM.querySelector({
                selector: 'html',
                nodeId: documentNodeId,
            });

            const {model: {height}} = await DOM.getBoxModel({nodeId: bodyNodeId});

            console.log("--------- height :" + height);
            // await Emulation.setVisibleSize({width: opts.w * opts.scale, height: height * opts.scale});
            await Emulation.setVisibleSize({width: opts.w, height: height});
            // This forceViewport call ensures that content outside the viewport is
            // rendered, otherwise it shows up as grey. Possibly a bug?


        }
        // await Emulation.forceViewport({
        //     x: opts.x,
        //     y: opts.y,
        //     scale: 1
        // });
        console.log("--------- 77777 :");

        await timeout(0);
        const screenshot = await Page.captureScreenshot({
            fromSurface: true,
            format: opts.format
        });
        const buffer = new Buffer(screenshot.data, 'base64');
        buffer.imgFormat = opts.format;
        return buffer;
    }


    class ImgController extends app.Controller {
        async index() {

            const options = parseOptions(this.ctx.query);

            const target = await CDP.New({
                host: app.config.chrome.host,
                port: app.config.chrome.port
            });
            console.log("target : ", target);

            const client = await CDP({target});
            const id = client.target.id;
            try {
                let imgBuf = await screenshot(client, options);

                this.ctx.response.type = imgBuf.imgFormat;
                this.ctx.body = imgBuf;
            } finally {
                CDP.Close({id});
                client.close();
            }
        }
    }
    return ImgController;
};


