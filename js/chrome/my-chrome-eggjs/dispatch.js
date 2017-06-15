const egg = require('egg');

// EGG_SERVER_ENV=prod nohup node dispatch.js > stdout.log 2> stderr.log &

egg.startCluster({
    baseDir: __dirname,
});