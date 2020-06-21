import {Component, OnInit} from '@angular/core';
import {AccountTipService} from './account-tip.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  constructor(private accountTipService: AccountTipService) {
  }

  private currentTip: string;

  ngOnInit() {
    this.currentTip = this.accountTipService.currentTip;
    this.accountTipService.statusEmitter.subscribe((tip: string) => {
      this.currentTip = tip;
    });
  }

}
