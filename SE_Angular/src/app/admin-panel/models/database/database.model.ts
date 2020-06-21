import {EntityModel} from '../entity/entity.model';

export class DatabaseModel {
  constructor(public name, public entities: [EntityModel]) {
  }
}
