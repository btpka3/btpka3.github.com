import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './mySelect.component.html',
  styleUrls: ['./mySelect.component.scss']
})
export class MySelectComponent implements OnInit {

  gender:string;
  toppings:string;

  constructor() {
  }

  ngOnInit(): void {

  }

}
