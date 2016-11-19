(function () {

    window.addEventListener('load', function () {
        //regSw();


    });

    function log(msg) {
        var target = window.log.target;
        if (!target) {
            target = window.log.target = document.getElementById("msg");
        }
        target.innerText += msg + "\n";
    }


    function regSw() {
        if (!('serviceWorker' in navigator)) {
            console.log('Service workers aren\'t supported in this browser.');
            return;
        }

        navigator.serviceWorker.register('sw.js').then(function (reg) {
            if (reg.installing) {
                log('Service worker installing');
            } else if (reg.waiting) {
                log('Service worker installed');
            } else if (reg.active) {
                log('Service worker active');
            }

            //initialiseState(reg);
        }).catch(function (error) {
            log('Registration failed with ' + error);
        });
    }


    function handleInstalled(ev) {
        const date = new Date(ev.timeStamp / 1000);
        document.getElementById("msg").innerText = "installed.";

        log(`Yay! Our app got installed at ${date.toTimeString()}`);
    }

    window.onappinstalled = handleInstalled;

// Using .addEventListener()
    window.addEventListener("appinstalled", handleInstalled);

})();