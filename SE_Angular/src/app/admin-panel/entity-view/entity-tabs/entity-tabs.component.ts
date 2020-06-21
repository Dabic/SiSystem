import {Component, Input, OnInit} from '@angular/core';
import {EntityTabsService} from './entity-tabs.service';

@Component({
    selector: 'app-entity-tabs',
    templateUrl: './entity-tabs.component.html',
    styleUrls: ['./entity-tabs.component.css']
})
export class EntityTabsComponent implements OnInit {

    @Input() tabsType: string;
    selectionFunction;

    constructor(private entityTabsService: EntityTabsService) {
    }

    ngOnInit() {
        if (this.tabsType === 'main') {
            this.selectionFunction = () => {
              return this.entityTabsService.mainEntities.indexOf(this.entityTabsService.selectedMainEntity);
            };
        } else if (this.tabsType === 'relations') {
          this.selectionFunction = () => {
            return this.entityTabsService.relationEntities.indexOf(this.entityTabsService.selectedRelationEntity);
          };
        }
    }

    tabSelected(index: number) {
      if (this.tabsType === 'main') {
        this.entityTabsService.openMainEntity(this.entityTabsService.mainEntities[index]);
      } else if (this.tabsType === 'relations') {
        this.entityTabsService.openRelationEntity(this.entityTabsService.relationEntities[index]);
      }
    }

}
