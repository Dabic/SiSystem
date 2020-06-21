import {Component, OnInit} from '@angular/core';
import {faCheck} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-account-type-item-mark',
  templateUrl: './account-type-item-mark.component.html',
  styleUrls: ['./account-type-item-mark.component.css']
})
export class AccountTypeItemMarkComponent implements OnInit {
  private faCheck = faCheck;

  constructor() {
  }

  ngOnInit() {
  }

}
