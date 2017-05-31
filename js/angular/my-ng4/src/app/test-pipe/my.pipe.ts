import {Pipe, PipeTransform} from "@angular/core";


@Pipe({name: 'myPipe'})
export class MyPipe implements PipeTransform {

  constructor() {
  }

  transform(value: string, add1: string, add2: String): string {
    return value + add1 + (add2 ? add2 : "")
  }

}
