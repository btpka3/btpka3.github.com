import {NgModule} from "@angular/core";
import {CommonModule} from '@angular/common';

import {OnsNavigator} from 'ngx-onsenui/directives/ons-navigator';
import {OnsTab} from 'ngx-onsenui/directives/ons-tabbar';
import {OnsSwitch} from 'ngx-onsenui/directives/ons-switch';
import {OnsRange} from 'ngx-onsenui/directives/ons-range';
import {OnsSelect} from 'ngx-onsenui/directives/ons-select';
import {OnsInput} from 'ngx-onsenui/directives/ons-input';
import {OnsPullHook} from 'ngx-onsenui/directives/ons-pull-hook';
import {OnsLazyRepeat} from 'ngx-onsenui/directives/ons-lazy-repeat';
import {OnsSplitterContent, OnsSplitterSide} from 'ngx-onsenui/directives/ons-splitter';

import {AlertDialogFactory} from 'ngx-onsenui/ons/alert-dialog-factory';
import {PopoverFactory} from 'ngx-onsenui/ons/popover-factory';
import {DialogFactory} from 'ngx-onsenui/ons/dialog-factory';
import {ModalFactory} from 'ngx-onsenui/ons/modal-factory';
import {ComponentLoader} from 'ngx-onsenui/ons/component-loader';

export * from 'ngx-onsenui/directives/ons-navigator';
export * from 'ngx-onsenui/directives/ons-tabbar';
export * from 'ngx-onsenui/directives/ons-switch';
export * from 'ngx-onsenui/directives/ons-range';
export * from 'ngx-onsenui/directives/ons-select';
export * from 'ngx-onsenui/directives/ons-input';
export * from 'ngx-onsenui/directives/ons-pull-hook';
export * from 'ngx-onsenui/directives/ons-lazy-repeat';
export * from 'ngx-onsenui/directives/ons-splitter';

export * from 'ngx-onsenui/ons/notification';
export * from 'ngx-onsenui/ons/platform';
export * from 'ngx-onsenui/ons/alert-dialog-factory';
export * from 'ngx-onsenui/ons/popover-factory';
export * from 'ngx-onsenui/ons/dialog-factory';
export * from 'ngx-onsenui/ons/modal-factory';
export * from 'ngx-onsenui/ons/params';

const directives = [
  OnsNavigator,
  OnsTab,
  OnsSwitch,
  OnsRange,
  OnsSelect,
  OnsInput,
  OnsPullHook,
  OnsLazyRepeat,
  OnsSplitterSide,
  OnsSplitterContent
];

@NgModule({
  imports: [CommonModule],
  declarations: [directives],
  exports: [
    directives
  ],
  providers: [
    AlertDialogFactory,
    PopoverFactory,
    DialogFactory,
    ModalFactory,
    ComponentLoader
  ]
})
export class OnsExModule {
}


// 参考 ngx-onsenui.ts

/*
原因：
* Onsen UI 使用之后， clarity 出错 "Error: The custom element being constructed was not registered with `customElements`."
* OnsenModule 因为re-export 了BrowserModule，故只能在顶层route中使用，子route中数据无法双向绑定
*/

