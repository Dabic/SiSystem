import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-attribute-fields',
  templateUrl: './attribute-fields.component.html',
  styleUrls: ['./attribute-fields.component.css']
})
export class AttributeFieldsComponent implements OnInit {

  @Input() private fieldName: string;
  @Input() private filedValue: string;

  constructor() {
  }

  ngOnInit() {
  }

}
