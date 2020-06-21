import {Role} from '../role.model';
import {EventEmitter, Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccountTypeService {
  roleEmitter = new EventEmitter();
  markedRole: string;
  roles = [
    new Role('../../../../../assets/administrator.png', false, 'Administrator'),
    new Role('../../../../../assets/student.png', false, 'Student'),
    new Role('../../../../../assets/professor.png', false, 'Employee')
  ];

  markRole(roleName): void {
    for (const role of this.roles) {
      if (role.roleName === roleName) {
        role.marked = true;
        this.markedRole = roleName;
        this.roleEmitter.emit(true);
      } else {
        role.marked = false;
      }
    }
  }
}
