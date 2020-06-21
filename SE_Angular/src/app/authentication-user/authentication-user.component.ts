import {Component, OnInit} from '@angular/core';
import {LoggingService} from '../logging.service';



declare let particlesJS: any;
import {ParticlesConfig} from './particles-config';
import {HttpClient} from '@angular/common/http';
@Component({
  selector: 'app-authentication',
  templateUrl: './authentication-user.component.html',
  styleUrls: ['./authentication-user.component.css'],
  providers: [LoggingService]
})
export class AuthenticationUserComponent implements OnInit {

  constructor(private httpClient: HttpClient) {
  }

  ngOnInit() {
    particlesJS('particles-js', ParticlesConfig, null);
  }

}
