import {Component, Input, OnInit} from '@angular/core';
import {EntityModel} from '../../models/entity/entity.model';
import {EntityTableService} from './entity-table.service';

@Component({
  selector: 'app-entity-table',
  templateUrl: './entity-table.component.html',
  styleUrls: ['./entity-table.component.css']
})
export class EntityTableComponent implements OnInit {

  @Input() entityDesc: EntityModel;
  @Input() entityData: EntityModel[];
  @Input() tableType: string;

  activeIndex = -1;
  constructor(private entityTableService: EntityTableService) { }

  ngOnInit() {
  }

  setActive(index: number, entity: EntityModel) {
    if (this.activeIndex === index) {
      this.activeIndex = -1;
    } else {
      this.activeIndex = index;
    }
    if (this.tableType === 'main') {
      this.entityTableService.selectMainRow(entity);
    } else {
      this.entityTableService.selectRelationRow(entity);
    }
  }

}
