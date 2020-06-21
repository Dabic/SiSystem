import {Component, Input, OnInit} from '@angular/core';
import {AccountTypeService} from '../account-type.service';
import {Role} from '../../role.model';
import {AccountTipService} from '../../account-tip.service';


@Component({
  selector: 'app-account-type-item',
  templateUrl: './account-type-item.component.html',
  styleUrls: ['./account-type-item.component.css']
})
export class AccountTypeItemComponent implements OnInit {

  @Input() private element: Role;

  constructor(private accountTypeService: AccountTypeService, private accountTipService: AccountTipService) {
  }

  ngOnInit() {
  }

  onItemClicked(): void {
    this.accountTypeService.markRole(this.element.roleName);
    this.accountTipService.setStatus();
  }

}
