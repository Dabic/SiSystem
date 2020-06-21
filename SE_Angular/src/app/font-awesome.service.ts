import {Injectable} from '@angular/core';

import {faUser} from '@fortawesome/free-solid-svg-icons';
import {faEnvelope} from '@fortawesome/free-solid-svg-icons';
import {faUnlockAlt} from '@fortawesome/free-solid-svg-icons';
import {faCaretDown} from '@fortawesome/free-solid-svg-icons';
import {faCaretRight} from '@fortawesome/free-solid-svg-icons';
import {faTimes} from '@fortawesome/free-solid-svg-icons';
import {faPlus} from '@fortawesome/free-solid-svg-icons';

@Injectable({
  providedIn: 'root'
})
export class FontAwesomeService {

  constructor() {
  }

  getIcon(name: string) {
    switch (name) {
      case 'user':
        return faUser;
      case 'mail':
        return faEnvelope;
      case 'password':
        return faUnlockAlt;
      case 'caret-down':
        return faCaretDown;
      case 'caret-right':
        return faCaretRight;
      case 'exit':
        return faTimes;
      case 'plus':
        return faPlus;
    }
  }
}
