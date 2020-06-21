import {AttributeFieldsModel} from '../attribute-fields/attribute-fields.model';


export class AttributeModel {
  constructor(public name: string, public value: string, public attributeFields?: [AttributeFieldsModel]) {
  }
}
