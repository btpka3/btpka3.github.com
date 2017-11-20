// https://developer.mozilla.org/en-US/docs/Web/HTTP/Proxy_servers_and_tunneling/Proxy_Auto-Configuration_(PAC)_file

function FindProxyForURL(url, host) {
    //console.log("=================FindProxyForURL : ", url, host);


    if(host.indexOf("google")){
        //return "SOCKS5 zhang3:zhang3@localhost:1080";
        return "SOCKS localhost:9999";
    }
    // //return "DIRECT";
    // return "DIRECT";
    return "SOCKS 127.0.0.1:1080";
}