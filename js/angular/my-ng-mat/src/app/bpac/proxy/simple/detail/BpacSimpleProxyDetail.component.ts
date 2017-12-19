import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacSimpleProxyDetail.component.html',
  styleUrls: ['./BpacSimpleProxyDetail.component.scss']
})
export class BpacSimpleProxyDetailComponent implements OnInit {

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
