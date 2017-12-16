import {Component, OnInit} from "@angular/core";


@Component({
  // selector: 'mat-test',
  templateUrl: './mat.component.html',
  styleUrls: ['./mat.component.css']
})
export class MatComponent implements OnInit {
  checked = false;
  indeterminate = false;

  messages = [
    {
      from: 'zhang3',
      subject: '111',
      content: 'a11'
    },
    {
      from: 'li4',
      subject: '222',
      content: 'a22'
    }
  ];

  typesOfShoes = ['Boots', 'Clogs', 'Loafers', 'Moccasins', 'Sneakers'];

  constructor() {

  }

  ngOnInit(): void {

  }

}
