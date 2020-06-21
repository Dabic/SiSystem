import {EventEmitter, Injectable} from '@angular/core';
import {EntityModel} from '../../models/entity/entity.model';

@Injectable({
  providedIn: 'root'
})
export class EntityTableService {

  selectedMainRow: EntityModel;
  selectedRelationRow: EntityModel;
  formEmitter: EventEmitter<any> = new EventEmitter();
  formOpened = false;
  constructor() { }

  selectMainRow(entity: EntityModel) {
    this.selectedMainRow = entity;
  }
  selectRelationRow(entity: EntityModel) {
    this.selectedRelationRow = entity;
  }

  onControlClicked(type: string, formTemplate: string[], entity, action) {
    this.formOpened = true;
    const data = {
      formTemplate,
      entity,
      action
    };
    this.formEmitter.emit(data);
  }
}
