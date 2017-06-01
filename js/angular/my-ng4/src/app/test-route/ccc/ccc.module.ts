import {NgModule} from "@angular/core";
import {CccComponent} from "./ccc.component";
import {RouterModule, Routes} from "@angular/router";

const routes: Routes = [
  {path: '', component: CccComponent}
];


@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  declarations: [CccComponent]
})
export class CccModule {
}
