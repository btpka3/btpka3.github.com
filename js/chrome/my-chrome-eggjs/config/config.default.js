'use strict';

module.exports = appInfo => {
    const config = {
        keys: appInfo.name + '_1497429418797_2462',

        appInfo: appInfo,

        chrome: {
            host: "localhost",
            port: 9222
        },

        middleware: ['errorHandler'],
        errorHandler: {
            // 非 `/api/` 路径不在这里做错误处理，留给默认的 onerror 插件统一处理
            match: '/img',
        }
    };

    return config;
};
