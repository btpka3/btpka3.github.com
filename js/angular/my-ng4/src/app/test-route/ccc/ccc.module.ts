import {NgModule} from "@angular/core";
import {CccComponent} from "./ccc.component";
import {RouteReuseStrategy, RouterModule, Routes} from "@angular/router";
import {CustomReuseStrategy} from "../../CustomReuseStrategy";

const routes: Routes = [
  {path: '', component: CccComponent}
];


@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  declarations: [CccComponent],
  providers: [
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy},
  ],
})
export class CccModule {
}
