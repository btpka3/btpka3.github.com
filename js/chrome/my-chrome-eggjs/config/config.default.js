'use strict';

module.exports = appInfo => {
    const config = {};

    // should change to your own
    config.keys = appInfo.name + '_1497429418797_2462';

    // add your config here
    config.appInfo = appInfo;
    config.chrome = {
        host: "localhost",
        port: 9222,
    };

    return config;
};
