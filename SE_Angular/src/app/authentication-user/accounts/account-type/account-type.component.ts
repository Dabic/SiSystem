import {Component, OnInit} from '@angular/core';
import {AccountTypeService} from './account-type.service';
import {Role} from '../role.model';

@Component({
  selector: 'app-account-type',
  templateUrl: './account-type.component.html',
  styleUrls: ['./account-type.component.css']
})
export class AccountTypeComponent implements OnInit {

  roles: Role[] = [];

  constructor(private accountTypeService: AccountTypeService) {
  }

  ngOnInit() {
    this.roles = this.accountTypeService.roles;
  }

}
