import {NgModule} from "@angular/core";

import {MyDirective} from "./my.directive";

@NgModule({
  declarations: [MyDirective],
  exports: [MyDirective]
})
export class TestDirectiveModule {
}
