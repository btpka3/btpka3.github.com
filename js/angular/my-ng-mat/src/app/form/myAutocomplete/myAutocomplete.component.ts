import {Component, OnInit,ViewChild} from "@angular/core";
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import {ENTER,COMMA} from '@angular/cdk/keycodes';
import {MatChipInputEvent, MatAutocompleteSelectedEvent} from '@angular/material';

import 'rxjs/add/operator/startWith';
import 'rxjs/add/operator/map';

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myAutocomplete.component.html',
  styleUrls: ['./myAutocomplete.component.scss']
})
export class MyAutocompleteComponent implements OnInit {

  myControl: FormControl = new FormControl();

  options = [
    'One',
    'Two',
    'Three'
  ];

  filteredOptions: Observable<string[]>;


  // autocomplete + chips
  // https://github.com/angular/material2/pull/7830
  visible: boolean = true;
  selectable: boolean = true;
  removable: boolean = true;
  addOnBlur: boolean = false;

  // Enter, comma
  separatorKeysCodes = [ENTER, COMMA];

  fruitCtrl = new FormControl();

  filteredFruits: Observable<any[]>;

  fruits = [
    { name: 'Lemon' },
  ];
  allFruits = [
    'Orange',
    'Strawberry',
    'Lime',
    'Apple',
  ];
  @ViewChild('fruitInput') fruitInput;

  constructor() {

    this.filteredOptions = this.myControl.valueChanges
      .pipe(
        startWith(''),
        map(val => this.filter(val))
      );

    this.filteredFruits = this.fruitCtrl.valueChanges
      .startWith(null)
      .map(fruit => fruit ? this.fruitFilter(fruit) : this.allFruits.slice());
  }

  filter(val: string): string[] {
    return this.options.filter(option =>
      option.toLowerCase().indexOf(val.toLowerCase()) === 0);
  }

  ngOnInit(): void {

  }



  add(event: MatChipInputEvent): void {
    let input = event.input;
    let value = event.value;

    // Add our person
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

  fruitFilter(name: string) {
    return this.allFruits.filter(fruit =>
      fruit.toLowerCase().indexOf(name.toLowerCase()) === 0);
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    this.fruits.push({ name: event.option.viewValue });
    this.fruitInput.nativeElement.value = '';
  }


}
