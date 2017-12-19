import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myExpansion.component.html',
  styleUrls: ['./myExpansion.component.scss']
})
export class MyExpansionComponent implements OnInit {

  panelOpenState: boolean = false;

  constructor() {
  }

  ngOnInit(): void {

  }


}
