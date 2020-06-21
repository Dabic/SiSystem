import {Component, OnInit} from '@angular/core';
import {EntityTableService} from '../../entity-table/entity-table.service';
import {FormArray, FormControl, FormGroup, Validators} from '@angular/forms';
import {EntityModel} from '../../../models/entity/entity.model';
import {EntityTabsService} from '../../entity-tabs/entity-tabs.service';

@Component({
    selector: 'app-create-read-modal',
    templateUrl: './create-read-modal.component.html',
    styleUrls: ['./create-read-modal.component.css']
})
export class CreateReadModalComponent implements OnInit {

    formGroup: FormGroup;
    action = '';
    entity: EntityModel = null;
    template: { type: string, label: string, name: string, value?: string }[];

    constructor(private entityTableService: EntityTableService, private entityTabsService: EntityTabsService) {
    }

    ngOnInit() {
        this.entityTableService.formEmitter.subscribe(data => {
            const group = [];
            data.formTemplate.forEach(template => {
                group.push({name: template.name, control: new FormControl(template.value, Validators.required)});
            });
            this.formGroup = new FormGroup({});
            group.forEach(control => this.formGroup.addControl(control.name, control.control));
            this.template = data.formTemplate;
            this.entity = data.entity;
            this.action = data.action;
        });
    }

    closeModal() {
        this.entityTableService.formOpened = false;
    }

    onSubmit() {
        Object.keys(this.formGroup.value).map(attr => {
            this.entity.attributes.forEach(attribute => {
                if (attribute.name === attr) {
                    attribute.value = this.formGroup.value[attr];
                }
            });
        });
        const entityToCreate = {
            name: this.entity.name,
            attributes: []
        };

        this.entity.attributes.forEach(attribute => {
            entityToCreate.attributes.push({name: attribute.name, value: attribute.value});
        });

        this.entityTabsService.createEntity(entityToCreate, this.action);


    }

}
