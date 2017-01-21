function appConfigFactory() {

    let domain = "//kingsilk.net";
    let rootUrl = "/qh/admin/local/16600";   // 如果后台开发人员使用本地启动的API，仅仅修改此行的端口即可，不要提交修改。
    let rootPath = domain + rootUrl;
    let apiPath = domain + "/qh/admin/local/16600" + "/api";

    let env = __ENV__;
    let appConfig = {
        rootPath: rootPath,
        rootUrl: rootUrl,
        apiPath: apiPath,
        maxSize: 8,  // 页数多少多少翻页数
        pageSize: 15, // 每页多少条数据
        imgUrl: "//o96iiewkd.qnssl.com/",   // 图片地址
        cdnUrl: "//o96iczjtp.qnssl.com/qh-admin-front/prod/",// cdn地址访问本地图片
        api: {
            tokenImg: apiPath + "/common/generatorToken"
        },
        env: env
    };

    return appConfig;
}

export default appConfigFactory;
