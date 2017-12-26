import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacCombineProxyDetail.component.html',
  styleUrls: ['./BpacCombineProxyDetail.component.scss']
})
export class BpacCombineProxyDetailComponent implements OnInit {

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
