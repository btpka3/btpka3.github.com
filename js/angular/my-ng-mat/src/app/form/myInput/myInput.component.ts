import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myInput.component.html',
  styleUrls: ['./myInput.component.scss']
})
export class MyInputComponent implements OnInit {


  value:string;

  constructor() {
  }

  ngOnInit(): void {

  }


}
