import {Component, OnInit} from "@angular/core";


@Component({
  // selector: 'mat-test',
  templateUrl: './mat.component.html',
  styleUrls: ['./mat.component.css']
})
export class MatComponent implements OnInit {
  checked = false;
  indeterminate = false;

  constructor() {

  }

  ngOnInit(): void {

  }

}
