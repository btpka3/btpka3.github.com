import {RouterModule, Routes} from '@angular/router';
import {NgModule} from "@angular/core";


import {HomeComponent} from './home/home.component';
import {MyNavbarComponent} from './myNavbar/myNavbar.component';
import {MyPickerComponent} from './myPicker/myPicker.component';



const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'myNavbar', component: MyNavbarComponent},
  {path: 'myPicker', component: MyPickerComponent},
  //{path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'},,




  {path: '**', redirectTo: ''}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
