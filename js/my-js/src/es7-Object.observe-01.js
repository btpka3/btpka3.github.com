/**
 * 注意：该方法已经废弃
 *
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object/observe
 */

'use strict';

var person = {};
Object.observe(person, (changes)=> {
    // [ { type: 'add', object: { name: 'zhang3' }, name: 'name' } ]
    console.log(changes);
});

person.name = "zhang3";

