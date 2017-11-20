var Socks = require('socks');

var options = {
    proxy: {
        ipaddress: "192.168.0.41",
        port: 1080,
        type: 5,
        command: "bind" // Since we are using bind, we must specify it here.
    },
    target: {
        host: "192.168.0.41", // When using bind, it's best to give an estimation of the ip that will be connecting to the newly opened tcp port on the proxy server.
        port: 1080
    }
};

Socks.createConnection(options, function(err, socket, info) {
    if (err)
        console.log(err);
    else {
        // BIND request has completed.
        // info object contains the remote ip and newly opened tcp port to connect to.
        console.log(info);

        // { port: 1494, host: '202.101.228.108' }

        socket.on('data', function(data) {
            console.log(data.length);
            console.log(data);
        });

        // Remember to resume the socket stream.
        socket.resume();
    }
});