import {AfterViewInit, Component, OnInit, ViewChild} from "@angular/core";
import {autoInit, MDCFoundation} from "material-components-web";

import {MDCRipple} from '@material/ripple';
import {MDCDialog, MDCDialogFoundation, util} from '@material/dialog';


@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myDialog.component.html',
  styleUrls: ['./myDialog.component.scss']
})
export class MyDialogComponent implements OnInit ,AfterViewInit{


  dialog: MDCDialog;

  @ViewChild('myMdcDialog') myMdcDialog: any;

  constructor() {
    console.log("=== constructor :", this.myMdcDialog);


  }

  ngOnInit(): void {
    console.log("=== ngOnInit :", this.myMdcDialog);
  }

  ngAfterViewInit(): void {
    console.log("=== ngAfterViewInit :", this.myMdcDialog);
    this.dialog = new MDCDialog(this.myMdcDialog.nativeElement);
    this.dialog.listen('MDCDialog:accept', function () {
      console.log('accepted');
    });

    this.dialog.listen('MDCDialog:cancel', function () {
      console.log('canceled');
    });
  }

  show(event): void {
    console.log("=== show :", event);
    this.dialog.lastFocusedTarget = event.target;
    this.dialog.show();
  }

  close(event): void {
    console.log("=== close :", event);
    this.dialog.lastFocusedTarget = event.target;
    this.dialog.close();
  }


}
