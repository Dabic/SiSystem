import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import {AccountTypeService} from '../accounts/account-type/account-type.service';
import {of} from 'rxjs';
import {LoginFormService} from './login-form.service';
import {AuthenticationUserService} from '../authentication-user.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './logn-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  private loginForm: FormGroup;
  private buttonEnabled: boolean;
  private currentMessage: string;
  private showError: string;

  constructor(private loginFormService: LoginFormService, private accountTypeService: AccountTypeService, private authenticationService: AuthenticationUserService) {
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
    });
    this.accountTypeService.roleEmitter.subscribe(value => {
      this.buttonEnabled = true;
    });
    this.loginFormService.errorEmitter.subscribe((value: { error: string, currentMessage: string }) => {
      this.showError = value.error;
      this.currentMessage = value.currentMessage;
    });
  }

  onSubmit() {
    this.authenticationService.login(this.loginForm);
  }

}
