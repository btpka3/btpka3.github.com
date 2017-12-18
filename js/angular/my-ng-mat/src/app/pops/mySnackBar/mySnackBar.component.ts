import {Component, OnInit} from "@angular/core";
import {MatSnackBar} from '@angular/material';
@Component({
  ///selector: '[aaa-component]',
  templateUrl: './mySnackBar.component.html',
  styleUrls: ['./mySnackBar.component.scss']
})
export class MySnackBarComponent implements OnInit {


  constructor(public snackBar: MatSnackBar) {
  }

  ngOnInit(): void {

  }

  openSnackBar() {
    this.snackBar.open("Hello", "World",{
      duration: 500,
    });
  }
}
