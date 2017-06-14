'use strict';

const CDP = require('chrome-remote-interface');
const assert = require('assert');

module.exports = app => {

    function timeout(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    async function screenshot(client, options) {

        const defaultOptions = {
            // url: "http://news.163.com",
            url: "http://192.168.0.41:64444/docker/chrome-headless/test.html",
            w: 500, // 500
            h: 568, // 1200
            fullScreen: true,
            format: "png",
            referrer: "",
            // 微信
            userAgent: "Mozilla/5.0 (Linux; Android 6.0; Le X620 Build/HEXCNFN5902303291S; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043220 Safari/537.36 MicroMessenger/6.5.7.1041 NetType/4G Language/zh_CN"

        };

        const opts = Object.assign({}, defaultOptions, options);

        console.log(opts);


        const format = "png";
        // const url = "https://www.baidu.com";
        // const url = "http://192.168.0.41:64444/docker/chrome-headless/test.html";
        const url = "http://news.163.com";

        // Extract used DevTools domains.
        const {DOM, Emulation, Network, Page, Runtime} = client;

        // Enable events on domains we are interested in.
        await Page.enable();
        await DOM.enable();
        await Network.enable();
        await Network.setUserAgentOverride({userAgent: opts.userAgent});

        // Set up viewport resolution, etc.
        const deviceMetrics = {
            width: opts.w,
            height: opts.h,
            fullScreen: opts.fullScreen,
            deviceScaleFactor: 0,
            emulateViewport: true,
            scale: 1,
            screenWidth: opts.w,
            screenHeight: opts.h,
            mobile: true,
            fitWindow: false,
        };
        await Emulation.setDeviceMetricsOverride(deviceMetrics);
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

            const a = await DOM.getBoxModel({nodeId: bodyNodeId});
            console.log("=====:", a);

            const {model: {height}} = await DOM.getBoxModel({nodeId: bodyNodeId});

            console.log("--------- height :" + height);
            await Emulation.setVisibleSize({width: opts.w, height: height});
            // This forceViewport call ensures that content outside the viewport is
            // rendered, otherwise it shows up as grey. Possibly a bug?
            await Emulation.forceViewport({x: 0, y: 0, scale: 1});
        }

        await timeout(200);
        const screenshot = await Page.captureScreenshot({
            fromSurface: true,
            format: opts.format
        });
        const buffer = new Buffer(screenshot.data, 'base64');
        return buffer;
    }

    function parseOptions(query) {
        const options = {};

        if (query.url) {
            options.url = query.url;
        }

        if (query.w) {
            var w = parseInt(query.w);
            assert.ok(!isNaN(w) && w >= 0 && w <= 10000000,
                `参数 w 必须是 [0, 10000000] 之间的整数。当前值是 : ${query.w}`);
            options.w = w;
        }

        if (query.h) {
            var h = parseInt(query.h);
            assert.ok(!isNaN(h) && h >= 0 && h <= 10000000,
                `参数 h 必须是 [0, 10000000] 之间的整数。当前值是 : ${query.h}`);
            options.h = h;
        }

        if (Object.getOwnPropertyNames(query).indexOf("fullScreen") >= 0) {
            var fullScreenStr = query.fullScreen

            assert.ok(fullScreenStr === "true" || fullScreenStr === "false",
                `参数 fullScreen 必须是 "true" 或者 "false"。当前值是 : ${fullScreenStr}`);

            options.fullScreen = "true" === fullScreenStr;
        }

        if (query.format) {
            var format = query.format;
            assert.ok(format === "png" || format === "jpeg",
                `参数 format 必须是 "png", "jpeg"。当前值是 "${format}"`);
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
                var imgBuf = await screenshot(client, options);

                this.ctx.response.type = "image/png";
                this.ctx.body = imgBuf;
            } catch (err) {
                console.log("------------err:", err);
            } finally {
                CDP.Close({id});
                client.close();
            }
        }
    }
    return ImgController;
};


