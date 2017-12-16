import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myDatetime.component.html',
  styleUrls: ['./myDatetime.component.scss'],
  host: {class: 'zmain'}
})
export class MyDatetimeComponent implements OnInit {
  myDate: Date = new Date();
  checked: boolean;

  constructor() {
  }

  ngOnInit(): void {

  }

  c(): void {
    this.checked = !this.checked;
  }

}
