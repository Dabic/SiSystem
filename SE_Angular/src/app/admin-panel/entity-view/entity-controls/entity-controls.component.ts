import {Component, Input, OnInit} from '@angular/core';
import {EntityTabsService} from '../entity-tabs/entity-tabs.service';
import {EntityTableService} from '../entity-table/entity-table.service';

@Component({
    selector: 'app-entity-controls',
    templateUrl: './entity-controls.component.html',
    styleUrls: ['./entity-controls.component.css']
})
export class EntityControlsComponent implements OnInit {

    @Input() controlType: string;

    constructor(private entityTabsService: EntityTabsService, private entityTableService: EntityTableService) {
    }

    ngOnInit() {
    }

    controlFunction(action: string) {
        let actionLabel: string;
        const currentEntity = this.getCurrentEntity(action);
        const formTemplate = [];
        switch (action) {
            case 'create':
                actionLabel = 'Create';
                currentEntity.attributes.forEach(attr => {
                    formTemplate.push({type: 'text', label: attr.name, name: attr.name, value: ''});
                });
                this.entityTableService.onControlClicked(action, formTemplate, currentEntity, actionLabel);
                break;
            case 'read':
                break;
            case 'update':
                if (currentEntity === undefined) {
                    return;
                }
                actionLabel = 'Update';
                currentEntity.attributes.forEach(attr => {
                    formTemplate.push({type: 'text', label: attr.name, name: attr.name, value: attr.value});
                });
                this.entityTableService.onControlClicked(action, formTemplate, currentEntity, actionLabel);
                break;
            case 'delete':
                if (currentEntity === undefined) {
                    return;
                }
                this.entityTabsService.deleteEntity(this.getCurrentEntity(action));
                break;
        }
    }

    getCurrentEntity(action: string) {
        if (this.controlType === 'main') {
            if (action === 'create' || action === 'read') {
                return this.entityTabsService.selectedMainEntity;
            } else {
                return this.entityTableService.selectedMainRow;
            }
        } else {
            if (action === 'create' || action === 'read') {
                return this.entityTabsService.selectedRelationEntity;
            } else {
                return this.entityTableService.selectedRelationRow;
            }
        }
    }

}
