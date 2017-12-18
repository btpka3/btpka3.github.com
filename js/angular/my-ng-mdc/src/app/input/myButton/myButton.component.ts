import {Component, OnInit} from "@angular/core";
import {autoInit, MDCFoundation} from "material-components-web";

import {MDCRipple} from '@material/ripple';


declare global {
  interface Window {
    mdc: any;
  }
}

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myButton.component.html',
  styleUrls: ['./myButton.component.scss']
})
export class MyButtonComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
    MDCRipple.attachTo(document.querySelector('#rippleBtn'));
    //autoInit()
    console.info("MyButtonComponent#ngOnInit");
  }

}
