//

var Rx = require('rxjs/Rx');
/*
 // import all
 import Rx from "rxjs/Rx";

 // import what you need
 import { Observable} from 'rxjs/Observable';
 import 'rxjs/add/observable/of';
 import 'rxjs/add/operator/map';
 */


function test01() {
    var a = Rx.Observable.of(1, 2, 3)
        .map(x => x * 2);

    console.log("test01 : a instanceof Rx.Observable == " + (a instanceof Rx.Observable));
    console.log(a);

}

test01();


function test02() {
    Rx.Observable.range(0, 5)
        //.average()
        .subscribe(
            function (x) {
                console.log('Next: ' + x);
            },
            function (err) {
                console.log('Error: ' + err);
            },
            function () {
                console.log('Completed');
            });
}

test02();