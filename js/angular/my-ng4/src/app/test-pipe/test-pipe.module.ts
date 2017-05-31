import {NgModule} from "@angular/core";

import {MyPipe} from "./my.pipe";

@NgModule({
  declarations: [MyPipe],
  exports: [MyPipe]
})
export class TestPipeModule {
}
