import {Component, OnInit} from "@angular/core";
import {FormControl, Validators} from '@angular/forms';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacDynamicProxyTest.component.html',
  styleUrls: ['./BpacDynamicProxyTest.component.scss']
})
export class BpacDynamicProxyTestComponent implements OnInit {

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  constructor() {
  }

  ngOnInit(): void {

  }


}
