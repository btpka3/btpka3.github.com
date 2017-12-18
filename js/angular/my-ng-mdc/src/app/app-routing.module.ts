import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MyHomeComponent } from './myHome/myHome.component';

import { MyIconToggleComponent } from './button/myIconToggle/myIconToggle.component';
import { MyFabComponent } from './button/myFab/myFab.component';


import { MyDialogComponent } from './dialog/myDialog/myDialog.component';


import { MyButtonComponent } from './input/myButton/myButton.component';

const routes: Routes = [
  {path: '', component: MyHomeComponent},

  {path: 'button/myFab', component: MyFabComponent},
  {path: 'button/myIconToggle', component: MyIconToggleComponent},

  {path: 'dialog/myDialog', component: MyDialogComponent},

  {path: 'input/myButton', component: MyButtonComponent},

  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
