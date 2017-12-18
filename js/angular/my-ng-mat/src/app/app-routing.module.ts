import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {MyHomeComponent} from "./myHome/myHome.component";


import {MyButtonComponent} from "./button/myButton/myButton.component";
import {MyChipsComponent} from "./button/myChips/myChips.component";

import {MyAutocompleteComponent} from "./form/myAutocomplete/myAutocomplete.component";
import {MyCheckboxComponent} from "./form/myCheckbox/myCheckbox.component";
import {MyInputComponent} from "./form/myInput/myInput.component";
import {MySelectComponent} from "./form/mySelect/mySelect.component";

import {MyListComponent} from "./layout/myList/myList.component";

import {MyToolbarComponent} from "./navigation/myToolbar/myToolbar.component";

import {MySnackBarComponent} from "./pops/mySnackBar/mySnackBar.component";

const routes: Routes = [
  {path: '', component: MyHomeComponent},

  {path: 'button/myButton', component: MyButtonComponent},
  {path: 'button/myChips', component: MyChipsComponent},

  {path: 'form/myAutocomplete', component: MyAutocompleteComponent},
  {path: 'form/myCheckbox', component: MyCheckboxComponent},
  {path: 'form/myInput', component: MyInputComponent},
  {path: 'form/mySelect', component: MySelectComponent},

  {path: 'layout/myList', component: MyListComponent},

  {path: 'navigation/myToolbar', component: MyToolbarComponent},

  {path: 'pops/mySnackBar', component: MySnackBarComponent},

  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
