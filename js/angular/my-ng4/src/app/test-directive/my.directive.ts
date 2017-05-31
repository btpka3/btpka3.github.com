import {Directive, ElementRef, HostListener, Input} from "@angular/core";


@Directive({selector: '[my-directive]'})
export class MyDirective {

  @Input() highlightColor: string;


  constructor(private el: ElementRef) {

    //el.nativeElement.style.backgroundColor = 'gold';
    console.log(
      `* AppRoot highlight called for ${el.nativeElement.tagName}`);

  }

  @HostListener('mouseenter')
  onMouseEnter() {
    this.highlight(this.highlightColor);
  }

  @HostListener('mouseleave')
  onMouseLeave() {
    this.highlight(null);
  }

  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}
