import {Subject} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccountTipService {
  statusEmitter = new Subject<string>();

  tips = [
    'Please select your role',
    'Please fill out the form bellow'
  ];

  currentTip = this.tips[0];

  setStatus() {
    this.currentTip = this.tips[1];
    this.statusEmitter.next(this.currentTip);
    this.statusEmitter.complete();
  }
}
