// this ServiceWorkerGlobalScope
console.log("============================= running service-worker-1.js");
var cacheFiles = [
    'svg/drawing1.svg',
    'svg/LOGO.svg'
];
var CACHE_NAME = 'my-test-cahce-v1';
self.addEventListener('install', function (event) {
    console.log("==========installed", event);

    // 这里可以做一些事情
    // event.waitUntil(
    //     caches.open(CACHE_NAME)
    //         .then(function(cache) {
    //             console.log('Opened cache : '+CACHE_NAME);
    //             return cache.addAll(cacheFiles);
    //         })
    // );
});


self.addEventListener('activate', function (event) {
    console.log("==========actived", event);
});

self.addEventListener('message', function (event) {
    console.log("==========message : ", event);
    event.ports[0].postMessage({date: new Date()});
});


self.addEventListener('fetch', function (event) {
    var reqUrl = new URL(event.request.url);
    // if (event.request.url.endsWith(".css")) {
    //     console.log("==========fetch : ", event);
    // }
    event.respondWith(
        caches.match(event.request).then(function (response) {


            // 缓存中已经存在?则直接返回
            if (response) {
                if (event.request.url.endsWith(".css")) {
                    console.log('return from cache : ', reqUrl.pathname);
                }
                return response;
            }


            var request = event.request.clone();

            // FIXME: 不会递归?  // fetch(request, {credentials: 'include'})
            return fetch(request).then(function (response) {
                // 不符合要求的请求都跳过缓存,直接返回
                if (!reqUrl.pathname.endsWith(".svg") || (!response && response.status !== 200)) {
                    if (event.request.url.endsWith(".css")) {
                        console.log('skip cache for', reqUrl.pathname);
                    }

                    return response;
                }

                // 符合要求的请求则先放到缓存中,再返回。
                console.log('add to cache : ', reqUrl.pathname);
                var responseClone = response.clone();
                caches.open(CACHE_NAME).then(function (cache) {
                    cache.put(event.request, responseClone);
                });
                return response;
            });
        })
    );
});