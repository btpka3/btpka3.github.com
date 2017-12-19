import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacStatus.component.html',
  styleUrls: ['./BpacStatus.component.scss']
})
export class BpacStatusComponent implements OnInit {

  foods = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];
  constructor() {
  }

  ngOnInit(): void {

  }


}
