const fs = require('fs');
const path = require('path');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const ProgressPlugin = require('webpack/lib/ProgressPlugin');
const CircularDependencyPlugin = require('circular-dependency-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const autoprefixer = require('autoprefixer');
const postcssUrl = require('postcss-url');
const cssnano = require('cssnano');

const { NoEmitOnErrorsPlugin, SourceMapDevToolPlugin, NamedModulesPlugin } = require('webpack');
const { NamedLazyChunksWebpackPlugin, BaseHrefWebpackPlugin } = require('@angular/cli/plugins/webpack');
const { CommonsChunkPlugin } = require('webpack').optimize;
const { AotPlugin } = require('@ngtools/webpack');

const nodeModules = path.join(process.cwd(), 'node_modules');
const realNodeModules = fs.realpathSync(nodeModules);
const genDirNodeModules = path.join(process.cwd(), 'src', '$$_gendir', 'node_modules');
const entryPoints = ["inline","polyfills","sw-register","styles","vendor","main"];
const minimizeCss = false;
const baseHref = "";
const deployUrl = "";
const postcssPlugins = function () {
        // safe settings based on: https://github.com/ben-eb/cssnano/issues/358#issuecomment-283696193
        const importantCommentRe = /@preserve|@license|[@#]\s*source(?:Mapping)?URL|^!/i;
        const minimizeOptions = {
            autoprefixer: false,
            safe: true,
            mergeLonghand: false,
            discardComments: { remove: (comment) => !importantCommentRe.test(comment) }
        };
        return [
            postcssUrl({
                url: (URL) => {
                    // Only convert root relative URLs, which CSS-Loader won't process into require().
                    if (!URL.startsWith('/') || URL.startsWith('//')) {
                        return URL;
                    }
                    if (deployUrl.match(/:\/\//)) {
                        // If deployUrl contains a scheme, ignore baseHref use deployUrl as is.
                        return `${deployUrl.replace(/\/$/, '')}${URL}`;
                    }
                    else if (baseHref.match(/:\/\//)) {
                        // If baseHref contains a scheme, include it as is.
                        return baseHref.replace(/\/$/, '') +
                            `/${deployUrl}/${URL}`.replace(/\/\/+/g, '/');
                    }
                    else {
                        // Join together base-href, deploy-url and the original URL.
                        // Also dedupe multiple slashes into single ones.
                        return `/${baseHref}/${deployUrl}/${URL}`.replace(/\/\/+/g, '/');
                    }
                }
            }),
            autoprefixer(),
        ].concat(minimizeCss ? [cssnano(minimizeOptions)] : []);
    };




module.exports = {
  "resolve": {
    "extensions": [
      ".ts",
      ".js"
    ],
    "modules": [
      "./node_modules",
      "./node_modules"
    ],
    "symlinks": true,
    "alias": {
      "rxjs/AsyncSubject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/AsyncSubject.js",
      "rxjs/BehaviorSubject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/BehaviorSubject.js",
      "rxjs/InnerSubscriber": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/InnerSubscriber.js",
      "rxjs/Notification": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Notification.js",
      "rxjs/Observable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Observable.js",
      "rxjs/Observer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Observer.js",
      "rxjs/Operator": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Operator.js",
      "rxjs/OuterSubscriber": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/OuterSubscriber.js",
      "rxjs/ReplaySubject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/ReplaySubject.js",
      "rxjs/Rx": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Rx.js",
      "rxjs/Scheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Scheduler.js",
      "rxjs/Subject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Subject.js",
      "rxjs/SubjectSubscription": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/SubjectSubscription.js",
      "rxjs/Subscriber": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Subscriber.js",
      "rxjs/Subscription": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/Subscription.js",
      "rxjs/add/observable/bindCallback": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/bindCallback.js",
      "rxjs/add/observable/bindNodeCallback": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/bindNodeCallback.js",
      "rxjs/add/observable/combineLatest": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/combineLatest.js",
      "rxjs/add/observable/concat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/concat.js",
      "rxjs/add/observable/defer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/defer.js",
      "rxjs/add/observable/dom/ajax": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/dom/ajax.js",
      "rxjs/add/observable/dom/webSocket": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/dom/webSocket.js",
      "rxjs/add/observable/empty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/empty.js",
      "rxjs/add/observable/forkJoin": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/forkJoin.js",
      "rxjs/add/observable/from": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/from.js",
      "rxjs/add/observable/fromEvent": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/fromEvent.js",
      "rxjs/add/observable/fromEventPattern": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/fromEventPattern.js",
      "rxjs/add/observable/fromPromise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/fromPromise.js",
      "rxjs/add/observable/generate": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/generate.js",
      "rxjs/add/observable/if": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/if.js",
      "rxjs/add/observable/interval": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/interval.js",
      "rxjs/add/observable/merge": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/merge.js",
      "rxjs/add/observable/never": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/never.js",
      "rxjs/add/observable/of": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/of.js",
      "rxjs/add/observable/onErrorResumeNext": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/onErrorResumeNext.js",
      "rxjs/add/observable/pairs": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/pairs.js",
      "rxjs/add/observable/race": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/race.js",
      "rxjs/add/observable/range": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/range.js",
      "rxjs/add/observable/throw": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/throw.js",
      "rxjs/add/observable/timer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/timer.js",
      "rxjs/add/observable/using": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/using.js",
      "rxjs/add/observable/zip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/observable/zip.js",
      "rxjs/add/operator/audit": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/audit.js",
      "rxjs/add/operator/auditTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/auditTime.js",
      "rxjs/add/operator/buffer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/buffer.js",
      "rxjs/add/operator/bufferCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/bufferCount.js",
      "rxjs/add/operator/bufferTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/bufferTime.js",
      "rxjs/add/operator/bufferToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/bufferToggle.js",
      "rxjs/add/operator/bufferWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/bufferWhen.js",
      "rxjs/add/operator/catch": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/catch.js",
      "rxjs/add/operator/combineAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/combineAll.js",
      "rxjs/add/operator/combineLatest": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/combineLatest.js",
      "rxjs/add/operator/concat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/concat.js",
      "rxjs/add/operator/concatAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/concatAll.js",
      "rxjs/add/operator/concatMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/concatMap.js",
      "rxjs/add/operator/concatMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/concatMapTo.js",
      "rxjs/add/operator/count": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/count.js",
      "rxjs/add/operator/debounce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/debounce.js",
      "rxjs/add/operator/debounceTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/debounceTime.js",
      "rxjs/add/operator/defaultIfEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/defaultIfEmpty.js",
      "rxjs/add/operator/delay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/delay.js",
      "rxjs/add/operator/delayWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/delayWhen.js",
      "rxjs/add/operator/dematerialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/dematerialize.js",
      "rxjs/add/operator/distinct": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/distinct.js",
      "rxjs/add/operator/distinctUntilChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/distinctUntilChanged.js",
      "rxjs/add/operator/distinctUntilKeyChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/distinctUntilKeyChanged.js",
      "rxjs/add/operator/do": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/do.js",
      "rxjs/add/operator/elementAt": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/elementAt.js",
      "rxjs/add/operator/every": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/every.js",
      "rxjs/add/operator/exhaust": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/exhaust.js",
      "rxjs/add/operator/exhaustMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/exhaustMap.js",
      "rxjs/add/operator/expand": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/expand.js",
      "rxjs/add/operator/filter": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/filter.js",
      "rxjs/add/operator/finally": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/finally.js",
      "rxjs/add/operator/find": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/find.js",
      "rxjs/add/operator/findIndex": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/findIndex.js",
      "rxjs/add/operator/first": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/first.js",
      "rxjs/add/operator/groupBy": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/groupBy.js",
      "rxjs/add/operator/ignoreElements": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/ignoreElements.js",
      "rxjs/add/operator/isEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/isEmpty.js",
      "rxjs/add/operator/last": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/last.js",
      "rxjs/add/operator/let": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/let.js",
      "rxjs/add/operator/map": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/map.js",
      "rxjs/add/operator/mapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/mapTo.js",
      "rxjs/add/operator/materialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/materialize.js",
      "rxjs/add/operator/max": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/max.js",
      "rxjs/add/operator/merge": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/merge.js",
      "rxjs/add/operator/mergeAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/mergeAll.js",
      "rxjs/add/operator/mergeMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/mergeMap.js",
      "rxjs/add/operator/mergeMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/mergeMapTo.js",
      "rxjs/add/operator/mergeScan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/mergeScan.js",
      "rxjs/add/operator/min": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/min.js",
      "rxjs/add/operator/multicast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/multicast.js",
      "rxjs/add/operator/observeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/observeOn.js",
      "rxjs/add/operator/onErrorResumeNext": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/onErrorResumeNext.js",
      "rxjs/add/operator/pairwise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/pairwise.js",
      "rxjs/add/operator/partition": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/partition.js",
      "rxjs/add/operator/pluck": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/pluck.js",
      "rxjs/add/operator/publish": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/publish.js",
      "rxjs/add/operator/publishBehavior": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/publishBehavior.js",
      "rxjs/add/operator/publishLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/publishLast.js",
      "rxjs/add/operator/publishReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/publishReplay.js",
      "rxjs/add/operator/race": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/race.js",
      "rxjs/add/operator/reduce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/reduce.js",
      "rxjs/add/operator/repeat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/repeat.js",
      "rxjs/add/operator/repeatWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/repeatWhen.js",
      "rxjs/add/operator/retry": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/retry.js",
      "rxjs/add/operator/retryWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/retryWhen.js",
      "rxjs/add/operator/sample": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/sample.js",
      "rxjs/add/operator/sampleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/sampleTime.js",
      "rxjs/add/operator/scan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/scan.js",
      "rxjs/add/operator/sequenceEqual": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/sequenceEqual.js",
      "rxjs/add/operator/share": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/share.js",
      "rxjs/add/operator/shareReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/shareReplay.js",
      "rxjs/add/operator/single": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/single.js",
      "rxjs/add/operator/skip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/skip.js",
      "rxjs/add/operator/skipLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/skipLast.js",
      "rxjs/add/operator/skipUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/skipUntil.js",
      "rxjs/add/operator/skipWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/skipWhile.js",
      "rxjs/add/operator/startWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/startWith.js",
      "rxjs/add/operator/subscribeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/subscribeOn.js",
      "rxjs/add/operator/switch": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/switch.js",
      "rxjs/add/operator/switchMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/switchMap.js",
      "rxjs/add/operator/switchMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/switchMapTo.js",
      "rxjs/add/operator/take": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/take.js",
      "rxjs/add/operator/takeLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/takeLast.js",
      "rxjs/add/operator/takeUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/takeUntil.js",
      "rxjs/add/operator/takeWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/takeWhile.js",
      "rxjs/add/operator/throttle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/throttle.js",
      "rxjs/add/operator/throttleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/throttleTime.js",
      "rxjs/add/operator/timeInterval": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/timeInterval.js",
      "rxjs/add/operator/timeout": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/timeout.js",
      "rxjs/add/operator/timeoutWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/timeoutWith.js",
      "rxjs/add/operator/timestamp": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/timestamp.js",
      "rxjs/add/operator/toArray": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/toArray.js",
      "rxjs/add/operator/toPromise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/toPromise.js",
      "rxjs/add/operator/window": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/window.js",
      "rxjs/add/operator/windowCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/windowCount.js",
      "rxjs/add/operator/windowTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/windowTime.js",
      "rxjs/add/operator/windowToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/windowToggle.js",
      "rxjs/add/operator/windowWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/windowWhen.js",
      "rxjs/add/operator/withLatestFrom": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/withLatestFrom.js",
      "rxjs/add/operator/zip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/zip.js",
      "rxjs/add/operator/zipAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/add/operator/zipAll.js",
      "rxjs/interfaces": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/interfaces.js",
      "rxjs/observable/ArrayLikeObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ArrayLikeObservable.js",
      "rxjs/observable/ArrayObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ArrayObservable.js",
      "rxjs/observable/BoundCallbackObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/BoundCallbackObservable.js",
      "rxjs/observable/BoundNodeCallbackObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/BoundNodeCallbackObservable.js",
      "rxjs/observable/ConnectableObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ConnectableObservable.js",
      "rxjs/observable/DeferObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/DeferObservable.js",
      "rxjs/observable/EmptyObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/EmptyObservable.js",
      "rxjs/observable/ErrorObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ErrorObservable.js",
      "rxjs/observable/ForkJoinObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ForkJoinObservable.js",
      "rxjs/observable/FromEventObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/FromEventObservable.js",
      "rxjs/observable/FromEventPatternObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/FromEventPatternObservable.js",
      "rxjs/observable/FromObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/FromObservable.js",
      "rxjs/observable/GenerateObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/GenerateObservable.js",
      "rxjs/observable/IfObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/IfObservable.js",
      "rxjs/observable/IntervalObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/IntervalObservable.js",
      "rxjs/observable/IteratorObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/IteratorObservable.js",
      "rxjs/observable/NeverObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/NeverObservable.js",
      "rxjs/observable/PairsObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/PairsObservable.js",
      "rxjs/observable/PromiseObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/PromiseObservable.js",
      "rxjs/observable/RangeObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/RangeObservable.js",
      "rxjs/observable/ScalarObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/ScalarObservable.js",
      "rxjs/observable/SubscribeOnObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/SubscribeOnObservable.js",
      "rxjs/observable/TimerObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/TimerObservable.js",
      "rxjs/observable/UsingObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/UsingObservable.js",
      "rxjs/observable/bindCallback": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/bindCallback.js",
      "rxjs/observable/bindNodeCallback": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/bindNodeCallback.js",
      "rxjs/observable/combineLatest": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/combineLatest.js",
      "rxjs/observable/concat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/concat.js",
      "rxjs/observable/defer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/defer.js",
      "rxjs/observable/dom/AjaxObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/dom/AjaxObservable.js",
      "rxjs/observable/dom/WebSocketSubject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/dom/WebSocketSubject.js",
      "rxjs/observable/dom/ajax": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/dom/ajax.js",
      "rxjs/observable/dom/webSocket": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/dom/webSocket.js",
      "rxjs/observable/empty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/empty.js",
      "rxjs/observable/forkJoin": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/forkJoin.js",
      "rxjs/observable/from": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/from.js",
      "rxjs/observable/fromEvent": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/fromEvent.js",
      "rxjs/observable/fromEventPattern": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/fromEventPattern.js",
      "rxjs/observable/fromPromise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/fromPromise.js",
      "rxjs/observable/generate": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/generate.js",
      "rxjs/observable/if": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/if.js",
      "rxjs/observable/interval": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/interval.js",
      "rxjs/observable/merge": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/merge.js",
      "rxjs/observable/never": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/never.js",
      "rxjs/observable/of": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/of.js",
      "rxjs/observable/onErrorResumeNext": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/onErrorResumeNext.js",
      "rxjs/observable/pairs": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/pairs.js",
      "rxjs/observable/race": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/race.js",
      "rxjs/observable/range": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/range.js",
      "rxjs/observable/throw": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/throw.js",
      "rxjs/observable/timer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/timer.js",
      "rxjs/observable/using": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/using.js",
      "rxjs/observable/zip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/observable/zip.js",
      "rxjs/operator/audit": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/audit.js",
      "rxjs/operator/auditTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/auditTime.js",
      "rxjs/operator/buffer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/buffer.js",
      "rxjs/operator/bufferCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/bufferCount.js",
      "rxjs/operator/bufferTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/bufferTime.js",
      "rxjs/operator/bufferToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/bufferToggle.js",
      "rxjs/operator/bufferWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/bufferWhen.js",
      "rxjs/operator/catch": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/catch.js",
      "rxjs/operator/combineAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/combineAll.js",
      "rxjs/operator/combineLatest": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/combineLatest.js",
      "rxjs/operator/concat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/concat.js",
      "rxjs/operator/concatAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/concatAll.js",
      "rxjs/operator/concatMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/concatMap.js",
      "rxjs/operator/concatMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/concatMapTo.js",
      "rxjs/operator/count": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/count.js",
      "rxjs/operator/debounce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/debounce.js",
      "rxjs/operator/debounceTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/debounceTime.js",
      "rxjs/operator/defaultIfEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/defaultIfEmpty.js",
      "rxjs/operator/delay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/delay.js",
      "rxjs/operator/delayWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/delayWhen.js",
      "rxjs/operator/dematerialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/dematerialize.js",
      "rxjs/operator/distinct": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/distinct.js",
      "rxjs/operator/distinctUntilChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/distinctUntilChanged.js",
      "rxjs/operator/distinctUntilKeyChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/distinctUntilKeyChanged.js",
      "rxjs/operator/do": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/do.js",
      "rxjs/operator/elementAt": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/elementAt.js",
      "rxjs/operator/every": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/every.js",
      "rxjs/operator/exhaust": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/exhaust.js",
      "rxjs/operator/exhaustMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/exhaustMap.js",
      "rxjs/operator/expand": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/expand.js",
      "rxjs/operator/filter": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/filter.js",
      "rxjs/operator/finally": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/finally.js",
      "rxjs/operator/find": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/find.js",
      "rxjs/operator/findIndex": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/findIndex.js",
      "rxjs/operator/first": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/first.js",
      "rxjs/operator/groupBy": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/groupBy.js",
      "rxjs/operator/ignoreElements": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/ignoreElements.js",
      "rxjs/operator/isEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/isEmpty.js",
      "rxjs/operator/last": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/last.js",
      "rxjs/operator/let": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/let.js",
      "rxjs/operator/map": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/map.js",
      "rxjs/operator/mapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/mapTo.js",
      "rxjs/operator/materialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/materialize.js",
      "rxjs/operator/max": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/max.js",
      "rxjs/operator/merge": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/merge.js",
      "rxjs/operator/mergeAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/mergeAll.js",
      "rxjs/operator/mergeMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/mergeMap.js",
      "rxjs/operator/mergeMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/mergeMapTo.js",
      "rxjs/operator/mergeScan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/mergeScan.js",
      "rxjs/operator/min": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/min.js",
      "rxjs/operator/multicast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/multicast.js",
      "rxjs/operator/observeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/observeOn.js",
      "rxjs/operator/onErrorResumeNext": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/onErrorResumeNext.js",
      "rxjs/operator/pairwise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/pairwise.js",
      "rxjs/operator/partition": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/partition.js",
      "rxjs/operator/pluck": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/pluck.js",
      "rxjs/operator/publish": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/publish.js",
      "rxjs/operator/publishBehavior": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/publishBehavior.js",
      "rxjs/operator/publishLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/publishLast.js",
      "rxjs/operator/publishReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/publishReplay.js",
      "rxjs/operator/race": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/race.js",
      "rxjs/operator/reduce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/reduce.js",
      "rxjs/operator/repeat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/repeat.js",
      "rxjs/operator/repeatWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/repeatWhen.js",
      "rxjs/operator/retry": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/retry.js",
      "rxjs/operator/retryWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/retryWhen.js",
      "rxjs/operator/sample": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/sample.js",
      "rxjs/operator/sampleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/sampleTime.js",
      "rxjs/operator/scan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/scan.js",
      "rxjs/operator/sequenceEqual": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/sequenceEqual.js",
      "rxjs/operator/share": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/share.js",
      "rxjs/operator/shareReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/shareReplay.js",
      "rxjs/operator/single": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/single.js",
      "rxjs/operator/skip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/skip.js",
      "rxjs/operator/skipLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/skipLast.js",
      "rxjs/operator/skipUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/skipUntil.js",
      "rxjs/operator/skipWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/skipWhile.js",
      "rxjs/operator/startWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/startWith.js",
      "rxjs/operator/subscribeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/subscribeOn.js",
      "rxjs/operator/switch": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/switch.js",
      "rxjs/operator/switchMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/switchMap.js",
      "rxjs/operator/switchMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/switchMapTo.js",
      "rxjs/operator/take": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/take.js",
      "rxjs/operator/takeLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/takeLast.js",
      "rxjs/operator/takeUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/takeUntil.js",
      "rxjs/operator/takeWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/takeWhile.js",
      "rxjs/operator/throttle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/throttle.js",
      "rxjs/operator/throttleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/throttleTime.js",
      "rxjs/operator/timeInterval": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/timeInterval.js",
      "rxjs/operator/timeout": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/timeout.js",
      "rxjs/operator/timeoutWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/timeoutWith.js",
      "rxjs/operator/timestamp": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/timestamp.js",
      "rxjs/operator/toArray": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/toArray.js",
      "rxjs/operator/toPromise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/toPromise.js",
      "rxjs/operator/window": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/window.js",
      "rxjs/operator/windowCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/windowCount.js",
      "rxjs/operator/windowTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/windowTime.js",
      "rxjs/operator/windowToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/windowToggle.js",
      "rxjs/operator/windowWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/windowWhen.js",
      "rxjs/operator/withLatestFrom": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/withLatestFrom.js",
      "rxjs/operator/zip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/zip.js",
      "rxjs/operator/zipAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operator/zipAll.js",
      "rxjs/operators/audit": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/audit.js",
      "rxjs/operators/auditTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/auditTime.js",
      "rxjs/operators/buffer": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/buffer.js",
      "rxjs/operators/bufferCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/bufferCount.js",
      "rxjs/operators/bufferTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/bufferTime.js",
      "rxjs/operators/bufferToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/bufferToggle.js",
      "rxjs/operators/bufferWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/bufferWhen.js",
      "rxjs/operators/catchError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/catchError.js",
      "rxjs/operators/combineAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/combineAll.js",
      "rxjs/operators/combineLatest": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/combineLatest.js",
      "rxjs/operators/concat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/concat.js",
      "rxjs/operators/concatAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/concatAll.js",
      "rxjs/operators/concatMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/concatMap.js",
      "rxjs/operators/concatMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/concatMapTo.js",
      "rxjs/operators/count": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/count.js",
      "rxjs/operators/debounce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/debounce.js",
      "rxjs/operators/debounceTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/debounceTime.js",
      "rxjs/operators/defaultIfEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/defaultIfEmpty.js",
      "rxjs/operators/delay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/delay.js",
      "rxjs/operators/delayWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/delayWhen.js",
      "rxjs/operators/dematerialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/dematerialize.js",
      "rxjs/operators/distinct": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/distinct.js",
      "rxjs/operators/distinctUntilChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/distinctUntilChanged.js",
      "rxjs/operators/distinctUntilKeyChanged": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/distinctUntilKeyChanged.js",
      "rxjs/operators/elementAt": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/elementAt.js",
      "rxjs/operators/every": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/every.js",
      "rxjs/operators/exhaust": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/exhaust.js",
      "rxjs/operators/exhaustMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/exhaustMap.js",
      "rxjs/operators/expand": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/expand.js",
      "rxjs/operators/filter": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/filter.js",
      "rxjs/operators/finalize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/finalize.js",
      "rxjs/operators/find": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/find.js",
      "rxjs/operators/findIndex": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/findIndex.js",
      "rxjs/operators/first": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/first.js",
      "rxjs/operators/groupBy": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/groupBy.js",
      "rxjs/operators/ignoreElements": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/ignoreElements.js",
      "rxjs/operators/index": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/index.js",
      "rxjs/operators/isEmpty": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/isEmpty.js",
      "rxjs/operators/last": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/last.js",
      "rxjs/operators/map": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/map.js",
      "rxjs/operators/mapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/mapTo.js",
      "rxjs/operators/materialize": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/materialize.js",
      "rxjs/operators/max": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/max.js",
      "rxjs/operators/merge": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/merge.js",
      "rxjs/operators/mergeAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/mergeAll.js",
      "rxjs/operators/mergeMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/mergeMap.js",
      "rxjs/operators/mergeMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/mergeMapTo.js",
      "rxjs/operators/mergeScan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/mergeScan.js",
      "rxjs/operators/min": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/min.js",
      "rxjs/operators/multicast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/multicast.js",
      "rxjs/operators/observeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/observeOn.js",
      "rxjs/operators/onErrorResumeNext": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/onErrorResumeNext.js",
      "rxjs/operators/pairwise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/pairwise.js",
      "rxjs/operators/partition": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/partition.js",
      "rxjs/operators/pluck": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/pluck.js",
      "rxjs/operators/publish": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/publish.js",
      "rxjs/operators/publishBehavior": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/publishBehavior.js",
      "rxjs/operators/publishLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/publishLast.js",
      "rxjs/operators/publishReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/publishReplay.js",
      "rxjs/operators/race": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/race.js",
      "rxjs/operators/reduce": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/reduce.js",
      "rxjs/operators/refCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/refCount.js",
      "rxjs/operators/repeat": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/repeat.js",
      "rxjs/operators/repeatWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/repeatWhen.js",
      "rxjs/operators/retry": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/retry.js",
      "rxjs/operators/retryWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/retryWhen.js",
      "rxjs/operators/sample": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/sample.js",
      "rxjs/operators/sampleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/sampleTime.js",
      "rxjs/operators/scan": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/scan.js",
      "rxjs/operators/sequenceEqual": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/sequenceEqual.js",
      "rxjs/operators/share": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/share.js",
      "rxjs/operators/shareReplay": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/shareReplay.js",
      "rxjs/operators/single": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/single.js",
      "rxjs/operators/skip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/skip.js",
      "rxjs/operators/skipLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/skipLast.js",
      "rxjs/operators/skipUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/skipUntil.js",
      "rxjs/operators/skipWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/skipWhile.js",
      "rxjs/operators/startWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/startWith.js",
      "rxjs/operators/subscribeOn": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/subscribeOn.js",
      "rxjs/operators/switchAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/switchAll.js",
      "rxjs/operators/switchMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/switchMap.js",
      "rxjs/operators/switchMapTo": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/switchMapTo.js",
      "rxjs/operators/take": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/take.js",
      "rxjs/operators/takeLast": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/takeLast.js",
      "rxjs/operators/takeUntil": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/takeUntil.js",
      "rxjs/operators/takeWhile": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/takeWhile.js",
      "rxjs/operators/tap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/tap.js",
      "rxjs/operators/throttle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/throttle.js",
      "rxjs/operators/throttleTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/throttleTime.js",
      "rxjs/operators/timeInterval": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/timeInterval.js",
      "rxjs/operators/timeout": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/timeout.js",
      "rxjs/operators/timeoutWith": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/timeoutWith.js",
      "rxjs/operators/timestamp": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/timestamp.js",
      "rxjs/operators/toArray": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/toArray.js",
      "rxjs/operators/window": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/window.js",
      "rxjs/operators/windowCount": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/windowCount.js",
      "rxjs/operators/windowTime": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/windowTime.js",
      "rxjs/operators/windowToggle": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/windowToggle.js",
      "rxjs/operators/windowWhen": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/windowWhen.js",
      "rxjs/operators/withLatestFrom": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/withLatestFrom.js",
      "rxjs/operators/zip": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/zip.js",
      "rxjs/operators/zipAll": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/zipAll.js",
      "rxjs/scheduler/Action": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/Action.js",
      "rxjs/scheduler/AnimationFrameAction": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AnimationFrameAction.js",
      "rxjs/scheduler/AnimationFrameScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AnimationFrameScheduler.js",
      "rxjs/scheduler/AsapAction": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AsapAction.js",
      "rxjs/scheduler/AsapScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AsapScheduler.js",
      "rxjs/scheduler/AsyncAction": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AsyncAction.js",
      "rxjs/scheduler/AsyncScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/AsyncScheduler.js",
      "rxjs/scheduler/QueueAction": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/QueueAction.js",
      "rxjs/scheduler/QueueScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/QueueScheduler.js",
      "rxjs/scheduler/VirtualTimeScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/VirtualTimeScheduler.js",
      "rxjs/scheduler/animationFrame": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/animationFrame.js",
      "rxjs/scheduler/asap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/asap.js",
      "rxjs/scheduler/async": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/async.js",
      "rxjs/scheduler/queue": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/scheduler/queue.js",
      "rxjs/symbol/iterator": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/symbol/iterator.js",
      "rxjs/symbol/observable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/symbol/observable.js",
      "rxjs/symbol/rxSubscriber": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/symbol/rxSubscriber.js",
      "rxjs/testing/ColdObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/ColdObservable.js",
      "rxjs/testing/HotObservable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/HotObservable.js",
      "rxjs/testing/SubscriptionLog": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/SubscriptionLog.js",
      "rxjs/testing/SubscriptionLoggable": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/SubscriptionLoggable.js",
      "rxjs/testing/TestMessage": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/TestMessage.js",
      "rxjs/testing/TestScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/testing/TestScheduler.js",
      "rxjs/util/AnimationFrame": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/AnimationFrame.js",
      "rxjs/util/ArgumentOutOfRangeError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/ArgumentOutOfRangeError.js",
      "rxjs/util/EmptyError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/EmptyError.js",
      "rxjs/util/FastMap": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/FastMap.js",
      "rxjs/util/Immediate": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/Immediate.js",
      "rxjs/util/Map": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/Map.js",
      "rxjs/util/MapPolyfill": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/MapPolyfill.js",
      "rxjs/util/ObjectUnsubscribedError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/ObjectUnsubscribedError.js",
      "rxjs/util/Set": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/Set.js",
      "rxjs/util/TimeoutError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/TimeoutError.js",
      "rxjs/util/UnsubscriptionError": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/UnsubscriptionError.js",
      "rxjs/util/applyMixins": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/applyMixins.js",
      "rxjs/util/assign": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/assign.js",
      "rxjs/util/errorObject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/errorObject.js",
      "rxjs/util/identity": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/identity.js",
      "rxjs/util/isArray": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isArray.js",
      "rxjs/util/isArrayLike": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isArrayLike.js",
      "rxjs/util/isDate": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isDate.js",
      "rxjs/util/isFunction": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isFunction.js",
      "rxjs/util/isNumeric": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isNumeric.js",
      "rxjs/util/isObject": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isObject.js",
      "rxjs/util/isPromise": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isPromise.js",
      "rxjs/util/isScheduler": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/isScheduler.js",
      "rxjs/util/noop": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/noop.js",
      "rxjs/util/not": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/not.js",
      "rxjs/util/pipe": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/pipe.js",
      "rxjs/util/root": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/root.js",
      "rxjs/util/subscribeToResult": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/subscribeToResult.js",
      "rxjs/util/toSubscriber": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/toSubscriber.js",
      "rxjs/util/tryCatch": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/util/tryCatch.js",
      "rxjs/operators": "/data0/work/git-repo/github/btpka3/btpka3.github.com/js/angular/my-ng4/node_modules/rxjs/_esm5/operators/index.js"
    }
  },
  "resolveLoader": {
    "modules": [
      "./node_modules",
      "./node_modules"
    ]
  },
  "entry": {
    "main": [
      "./src/main.ts"
    ],
    "polyfills": [
      "./src/polyfills.ts"
    ],
    "styles": [
      "./src/styles.css",
      "./node_modules/font-awesome/css/font-awesome.min.css",
      "./node_modules/primeng/resources/themes/omega/theme.css",
      "./node_modules/primeng/resources/primeng.min.css"
    ]
  },
  "output": {
    "path": path.join(process.cwd(), "dist"),
    "filename": "[name].bundle.js",
    "chunkFilename": "[id].chunk.js"
  },
  "module": {
    "rules": [
      {
        "enforce": "pre",
        "test": /\.js$/,
        "loader": "source-map-loader",
        "exclude": [
          /(\\|\/)node_modules(\\|\/)/
        ]
      },
      {
        "test": /\.html$/,
        "loader": "raw-loader"
      },
      {
        "test": /\.(eot|svg|cur)$/,
        "loader": "file-loader",
        "options": {
          "name": "[name].[hash:20].[ext]",
          "limit": 10000
        }
      },
      {
        "test": /\.(jpg|png|webp|gif|otf|ttf|woff|woff2|ani)$/,
        "loader": "url-loader",
        "options": {
          "name": "[name].[hash:20].[ext]",
          "limit": 10000
        }
      },
      {
        "exclude": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.css$/,
        "use": [
          "exports-loader?module.exports.toString()",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          }
        ]
      },
      {
        "exclude": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.scss$|\.sass$/,
        "use": [
          "exports-loader?module.exports.toString()",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "sass-loader",
            "options": {
              "sourceMap": false,
              "precision": 8,
              "includePaths": []
            }
          }
        ]
      },
      {
        "exclude": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.less$/,
        "use": [
          "exports-loader?module.exports.toString()",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "less-loader",
            "options": {
              "sourceMap": false
            }
          }
        ]
      },
      {
        "exclude": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.styl$/,
        "use": [
          "exports-loader?module.exports.toString()",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "stylus-loader",
            "options": {
              "sourceMap": false,
              "paths": []
            }
          }
        ]
      },
      {
        "include": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.css$/,
        "use": [
          "style-loader",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          }
        ]
      },
      {
        "include": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.scss$|\.sass$/,
        "use": [
          "style-loader",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "sass-loader",
            "options": {
              "sourceMap": false,
              "precision": 8,
              "includePaths": []
            }
          }
        ]
      },
      {
        "include": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.less$/,
        "use": [
          "style-loader",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "less-loader",
            "options": {
              "sourceMap": false
            }
          }
        ]
      },
      {
        "include": [
          path.join(process.cwd(), "src/styles.css"),
          path.join(process.cwd(), "node_modules/font-awesome/css/font-awesome.min.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/themes/omega/theme.css"),
          path.join(process.cwd(), "node_modules/primeng/resources/primeng.min.css")
        ],
        "test": /\.styl$/,
        "use": [
          "style-loader",
          {
            "loader": "css-loader",
            "options": {
              "sourceMap": false,
              "importLoaders": 1
            }
          },
          {
            "loader": "postcss-loader",
            "options": {
              "ident": "postcss",
              "plugins": postcssPlugins
            }
          },
          {
            "loader": "stylus-loader",
            "options": {
              "sourceMap": false,
              "paths": []
            }
          }
        ]
      },
      {
        "test": /\.ts$/,
        "loader": "@ngtools/webpack"
      }
    ]
  },
  "plugins": [
    new NoEmitOnErrorsPlugin(),
    new CopyWebpackPlugin([
      {
        "context": "src",
        "to": "",
        "from": {
          "glob": "assets/**/*",
          "dot": true
        }
      },
      {
        "context": "src",
        "to": "./assets",
        "from": {
          "glob": "favicon.ico",
          "dot": true
        }
      }
    ], {
      "ignore": [
        ".gitkeep"
      ],
      "debug": "warning"
    }),
    new ProgressPlugin(),
    new CircularDependencyPlugin({
      "exclude": /(\\|\/)node_modules(\\|\/)/,
      "failOnError": false
    }),
    new NamedLazyChunksWebpackPlugin(),
    new HtmlWebpackPlugin({
      "template": "./src/index.html",
      "filename": "./index.html",
      "hash": false,
      "inject": true,
      "compile": true,
      "favicon": false,
      "minify": false,
      "cache": true,
      "showErrors": true,
      "chunks": "all",
      "excludeChunks": [],
      "title": "Webpack App",
      "xhtml": true,
      "chunksSortMode": function sort(left, right) {
        let leftIndex = entryPoints.indexOf(left.names[0]);
        let rightindex = entryPoints.indexOf(right.names[0]);
        if (leftIndex > rightindex) {
            return 1;
        }
        else if (leftIndex < rightindex) {
            return -1;
        }
        else {
            return 0;
        }
    }
    }),
    new BaseHrefWebpackPlugin({}),
    new CommonsChunkPlugin({
      "name": [
        "inline"
      ],
      "minChunks": null
    }),
    new CommonsChunkPlugin({
      "name": [
        "vendor"
      ],
      "minChunks": (module) => {
                return module.resource
                    && (module.resource.startsWith(nodeModules)
                        || module.resource.startsWith(genDirNodeModules)
                        || module.resource.startsWith(realNodeModules));
            },
      "chunks": [
        "main"
      ]
    }),
    new SourceMapDevToolPlugin({
      "filename": "[file].map[query]",
      "moduleFilenameTemplate": "[resource-path]",
      "fallbackModuleFilenameTemplate": "[resource-path]?[hash]",
      "sourceRoot": "webpack:///"
    }),
    new CommonsChunkPlugin({
      "name": [
        "main"
      ],
      "minChunks": 2,
      "async": "common"
    }),
    new NamedModulesPlugin({}),
    new AotPlugin({
      "mainPath": "main.ts",
      "replaceExport": false,
      "hostReplacementPaths": {
        "environments/environment.ts": "environments/environment.ts"
      },
      "exclude": [],
      "tsConfigPath": "src/tsconfig.app.json",
      "skipCodeGeneration": true
    })
  ],
  "node": {
    "fs": "empty",
    "global": true,
    "crypto": "empty",
    "tls": "empty",
    "net": "empty",
    "process": true,
    "module": false,
    "clearImmediate": false,
    "setImmediate": false
  },
  "devServer": {
    "historyApiFallback": true
  }
};
