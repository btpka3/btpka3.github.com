import {NgModule} from "@angular/core";
import {PreloadAllModules, RouterModule, Routes} from "@angular/router";

import {MyDropdownComponent} from "./input/myDropdown/myDropdown.component";
import {MySliderComponent} from "./input/mySlider/mySlider.component";

import {MyButtonComponent} from "./button/myButton/myButton.component";
import {MySplitButtonComponent} from "./button/mySplitButton/mySplitButton.component";

import {MyHomeComponent} from "./myHome/myHome.component";

import {MyPanelComponent} from "./panel/myPanel/myPanel.component";

import {MyMenuComponent} from "./menu/myMenu/myMenu.component";


const routes: Routes = [
  {path: '', component: MyHomeComponent},

  {path: 'input/myDropdown', component: MyDropdownComponent},
  {path: 'input/mySlider', component: MySliderComponent},

  {path: 'button/myButton', component: MyButtonComponent},
  {path: 'button/mySplitButton', component: MySplitButtonComponent},


  {path: 'panel/myPanelButton', component: MyPanelComponent},


  {path: 'menu/myMenu', component: MyMenuComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})
  ],

  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}
