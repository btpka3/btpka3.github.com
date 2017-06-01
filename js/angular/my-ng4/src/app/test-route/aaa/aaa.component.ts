import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './aaa.component.html',
  styleUrls: ['./aaa.component.css']
})
export class AaaComponent implements OnInit {
  v = 'a1-a2';

  constructor() {
  }

  ngOnInit(): void {

  }
}
