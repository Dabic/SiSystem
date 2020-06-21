import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  loadingEmitter = new EventEmitter();

  constructor() {
  }

  endLoading() {
    this.loadingEmitter.emit(false);
  }
}
