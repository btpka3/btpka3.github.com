import {Component, OnInit} from "@angular/core";
import {ENTER, COMMA} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material';

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myChips.component.html',
  styleUrls: ['./myChips.component.scss']
})
export class MyChipsComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {

  }
  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = true;

  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];

  fruits = [
    { name: 'Lemon' },
    { name: 'Lime' },
    { name: 'Apple' },
  ];


  add(event: MatChipInputEvent): void {
    let input = event.input;
    let value = event.value;

    // Add our fruit
    if ((value || '').trim()) {
      this.fruits.push({ name: value.trim() });
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(fruit: any): void {
    let index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
}
