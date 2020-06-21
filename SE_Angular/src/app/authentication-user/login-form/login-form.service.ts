import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginFormService {

  errorEmitter = new EventEmitter();
  private errorMessages = [
    'Credentials are incorrect! Please try again.',
    'User with selected role was not found.'
  ];
  private currentMessage: string;
  private error: boolean;

  constructor() {
  }

  public credentialsError() {
    this.currentMessage = this.errorMessages[0];
    this.error = true;
    this.errorEmitter.emit({error: true, currentMessage: this.currentMessage});
  }

  public roleError() {
    this.currentMessage = this.errorMessages[1];
    this.error = true;
    this.errorEmitter.emit({error: true, currentMessage: this.currentMessage});
  }

  public noError() {
    this.error = false;
    this.errorEmitter.emit({error: false, currentMessage: this.currentMessage});
  }
}
