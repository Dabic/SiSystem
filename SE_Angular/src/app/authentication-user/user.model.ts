import construct = Reflect.construct;
import {JwtHelperService} from '@auth0/angular-jwt';

export class User {

  constructor(public role: string, public username: string, public token: string, public email: string) {

  }

  saveUser() {
    const helper = new JwtHelperService();
    const expiresIn: Date = helper.getTokenExpirationDate(this.token);
    localStorage.setItem('expiresIn', expiresIn.toJSON());
    localStorage.setItem('token', this.token);
    localStorage.setItem('username', this.username);
    localStorage.setItem('role', this.role);
    localStorage.setItem('email', this.email);
  }

  clearUser() {
    localStorage.removeItem('expiresIn');
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
  }

  public getCurrentUser(): User {
    return new User(
      localStorage.getItem('role'),
      localStorage.getItem('username'),
      localStorage.getItem('token'),
      localStorage.getItem('email')
    );
  }
}
