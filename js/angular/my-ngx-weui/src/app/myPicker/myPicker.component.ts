import {Component, OnDestroy, OnInit, ViewEncapsulation} from "@angular/core";
import { PickerData, PickerOptions, PickerService } from 'ngx-weui/picker';
import {DATA} from "./data";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myPicker.component.html',
  styleUrls: ['./myPicker.component.scss'],
  //host: {class: 'myClass'}
  encapsulation: ViewEncapsulation.None
})
export class MyPickerComponent implements OnInit, OnDestroy {
  DT: any = {
    min: new Date(2015, 1, 5),
    max: new Date()
  };
  res: any = {
    city: '310105',
    ym : new Date(),
    date: new Date()
  };
  cityData: any = DATA;

  onCityChange(data: any) {
    console.log('onCityChange', data);
  }

  constructor() {
  }

  ngOnInit(): void {

  }

  ngOnDestroy(): void {

  }

}
