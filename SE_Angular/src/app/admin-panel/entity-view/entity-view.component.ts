import { Component, OnInit } from '@angular/core';
import {EntityTableService} from './entity-table/entity-table.service';

@Component({
  selector: 'app-entity-view',
  templateUrl: './entity-view.component.html',
  styleUrls: ['./entity-view.component.css']
})
export class EntityViewComponent implements OnInit {

  constructor(private entityTableService: EntityTableService) { }

  ngOnInit() {
  }

}
