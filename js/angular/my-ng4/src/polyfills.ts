/**
 * This file includes polyfills needed by Angular and is loaded before the app.
 * You can add your own extra polyfills to this file.
 *
 * This file is divided into 2 sections:
 *   1. Browser polyfills. These are applied before loading ZoneJS and are sorted by browsers.
 *   2. Application imports. Files imported after ZoneJS that should be loaded before your main
 *      file.
 *
 * The current setup is for so-called "evergreen" browsers; the last versions of browsers that
 * automatically update themselves. This includes Safari >= 10, Chrome >= 55 (including Opera),
 * Edge >= 13 on the desktop, and iOS 10 and Chrome on mobile.
 *
 * Learn more in https://angular.io/docs/ts/latest/guide/browser-support.html
 */

/***************************************************************************************************
 * BROWSER POLYFILLS
 */

/** IE9, IE10 and IE11 requires all of the following polyfills. **/
// import 'core-js/es6/symbol';
// import 'core-js/es6/object';
// import 'core-js/es6/function';
// import 'core-js/es6/parse-int';
// import 'core-js/es6/parse-float';
// import 'core-js/es6/number';
// import 'core-js/es6/math';
// import 'core-js/es6/string';
// import 'core-js/es6/date';
// import 'core-js/es6/array';
// import 'core-js/es6/regexp';
// import 'core-js/es6/map';
// import 'core-js/es6/set';

/** IE10 and IE11 requires the following for NgClass support on SVG elements */
// import 'classlist.js';  // Run `npm install --save classlist.js`.

/** IE10 and IE11 requires the following to support `@angular/animation`. */
// import 'web-animations-js';  // Run `npm install --save web-animations-js`.


/** Evergreen browsers require these. **/
import 'core-js/es6/reflect';
import 'core-js/es7/reflect';


/** ALL Firefox browsers require the following to support `@angular/animation`. **/
import 'web-animations-js';  // Run `npm install --save web-animations-js`.



/***************************************************************************************************
 * Zone JS is required by Angular itself.
 */
import 'zone.js/dist/zone';  // Included with Angular CLI.


// //import '@webcomponents/webcomponentsjs/webcomponents-hi-sd-ce.js';
// //import '@webcomponents/webcomponentsjs/entrypoints/webcomponents-hi-sd-ce-pf-index.js';
// import '@webcomponents/webcomponents-platform/webcomponents-platform.js';
// import '@webcomponents/template/template.js';
// //import '@webcomponents/webcomponentsjs/src/promise.js';
// import ES6Promise from 'es6-promise/lib/es6-promise/promise.js';
//
// /*
// Assign the ES6 promise polyfill to window ourselves instead of using the "auto" polyfill
// to work around https://github.com/webcomponents/webcomponentsjs/issues/837
// */
// if (!window["Promise"]) {
//   window["Promise"] = ES6Promise;
//   // save catch function with a string name to prevent renaming and dead code eliminiation with closure
//   ES6Promise.prototype['catch'] = ES6Promise.prototype.catch;
// }
//
// import '@webcomponents/html-imports/src/html-imports.js';
// import '@webcomponents/webcomponentsjs/src/pre-polyfill.js';
// import '@webcomponents/shadydom/src/shadydom.js';
// import '@webcomponents/custom-elements/src/custom-elements.js';
// import '@webcomponents/shadycss/entrypoints/scoping-shim.js';
// import '@webcomponents/webcomponentsjs/src/post-polyfill.js';
// import '@webcomponents/webcomponentsjs/src/unresolved.js';

//import 'onsenui/core-src/polyfills';



/***************************************************************************************************
 * APPLICATION IMPORTS
 */

/**
 * Date, currency, decimal and percent pipes.
 * Needed for: All but Chrome, Firefox, Edge, IE11 and Safari 10
 */
// import 'intl';  // Run `npm install --save intl`.
/**
 * Need to import at least one locale-data with intl.
 */
// import 'intl/locale-data/jsonp/en';
