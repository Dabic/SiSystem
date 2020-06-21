import {Directive, ElementRef, Host, HostListener, OnInit} from '@angular/core';
import {FocusService} from './focus.service';

@Directive({
  selector: '[appSexyInput]',
})
export class SexyInputDirective implements OnInit {
  constructor(private elementRef: ElementRef, private focusService: FocusService) {
  }

  ngOnInit(): void {
  }

  @HostListener('focusin')
  focusIn() {
    this.focusService.setFocus(true);
  }

  @HostListener('focusout')
  focusOut() {
    this.focusService.setFocus(false);
  }


}
