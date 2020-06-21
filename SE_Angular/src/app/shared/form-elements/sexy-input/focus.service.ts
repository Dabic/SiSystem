import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

export class FocusService {
  focusEmitter = new Subject<boolean>();
  focused: boolean;

  constructor() {
  }

  setFocus(isFocused: boolean) {
    this.focused = isFocused;
    this.focusEmitter.next(this.focused);
  }
}
