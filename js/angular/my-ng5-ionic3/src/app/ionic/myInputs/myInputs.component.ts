import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myInputs.component.html',
  styleUrls: ['./myInputs.component.scss'],
  host: {class: 'zmain'}
})
export class MyInputsComponent implements OnInit {
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
