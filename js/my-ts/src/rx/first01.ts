
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/range';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/reduce';
import 'rxjs/add/operator/first';



var source = Observable.range(0, 10)
    .first();

var subscription = source.subscribe(
    function (x) {
        console.log('Next: %s', x);
    },
    function (err) {
        console.log('Error: %s', err);
    },
    function () {
        console.log('Completed');
    });
