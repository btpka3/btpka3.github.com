import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './mySidenav.component.html',
  styleUrls: ['./mySidenav.component.scss']
})
export class MySidenavComponent implements OnInit {

  // mode = side : 不会自动关闭
  // mode = over : 会自动关闭
  // mode = push : 会自动关闭

  constructor() {
  }

  ngOnInit(): void {

  }



}
