(function () {
    // for common use
    function antiClickjack() {
        console.log("~~~~~~~~~~~~~~~~~"+(self === top));
        if (self === top) {
            var antiClickjack = document.getElementById("antiClickjack");
            if(antiClickjack){
                antiClickjack.parentNode.removeChild(antiClickjack);
            }
        } else {
            top.location = self.location;
        }
    }

    // for dojo
    if (typeof define != 'undefined') {
        console.log("~~~~~~~~~~~~~~~~~ in dojo");
        define(function() {return antiClickjack;});
    } else {
        antiClickjack();
    }
})();