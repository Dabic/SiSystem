import {AttributeModel} from '../attribute/attribute.model';

export class EntityModel {
  constructor(public name: string, public attributes: AttributeModel[], public relations?: EntityModel[]) {
  }
}
