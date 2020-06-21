import {Injectable} from '@angular/core';
import {FormGroup, NgForm} from '@angular/forms';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Constants} from '../../utils/Constants';
import {User} from './user.model';
import {DatabaseModel} from '../admin-panel/models/database/database.model';
import {LoginFormService} from './login-form/login-form.service';
import {AccountTypeService} from './accounts/account-type/account-type.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationUserService {

  constructor(private router: Router, private http: HttpClient, private accountTypeService: AccountTypeService, private loginFormService: LoginFormService) {
  }

  login(loginForm: FormGroup) {
    const username = loginForm.get('email').value;
    const password = loginForm.get('password').value;
    const role = this.accountTypeService.markedRole;
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    this.http
      .post('http://localhost:8080' + '/login', {username, password}, {headers, observe: 'response'})
      .subscribe(
        (response: HttpResponse<{ username: string, grantedAuthorities: [string], authenticated: boolean, email: string }>) => {
          let responseBody: { username: string, grantedAuthorities: [string], authenticated: boolean, email: string };
          responseBody = response.body;
          if (!this.checkRole(role, responseBody.grantedAuthorities)) {
            this.loginFormService.roleError();
          } else {
            this.loginFormService.noError();
            const token = response.headers.get('Authorization');
            const user = new User(role, username, token, responseBody.email);
            user.saveUser();
            if (user.role === 'Administrator') {
              this.router.navigate(['/admin-panel']);
            } else {
              this.router.navigate(['/generate-report']);
            }
          }
        },
        error => {
          this.loginFormService.credentialsError();
        }
      );

  }

  checkRole(roleName: string, gaRoles: [string]) {
    let result = false;
    gaRoles.forEach((role => {
      if (roleName.toLowerCase() === role.split('_')[1].toLowerCase()) {
        result = true;
      }
    }));
    return result;
  }

}
