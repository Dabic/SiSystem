import {EventEmitter, Injectable} from '@angular/core';
import {EntityModel} from '../../models/entity/entity.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class EntityTabsService {

    mainEntities: EntityModel[] = [];
    selectedMainEntity: EntityModel;
    mainEntityData: EntityModel[] = [];

    relationEntities: EntityModel[] = [];
    selectedRelationEntity: EntityModel;
    relationEntityData: EntityModel[] = [];

    constructor(private httpClient: HttpClient) {
    }

    openMainEntity(entity: EntityModel) {
        if (this.mainEntities.filter(ent => ent.name === entity.name).length === 0) {
            this.mainEntities.push(entity);
        }
        this.selectedMainEntity = entity;
        if (entity.attributes[0].value === undefined) {
            this.getDataForEntity(entity, 'main');
        }

        this.relationEntities = entity.relations;
        if (this.relationEntities.length > 0) {
            this.selectedRelationEntity = this.relationEntities[0];
            if (this.selectedRelationEntity.attributes[0].value === undefined) {
                this.getDataForEntity(this.selectedRelationEntity, 'relations');
            }
        }
    }

    openRelationEntity(entity: EntityModel) {
        if (entity !== undefined) {
            this.selectedRelationEntity = entity;
            if (entity.attributes[0].value === undefined) {
                this.getDataForEntity(entity, 'relations');
            }
        }
    }

    getDataForEntity(entity: EntityModel, type: string) {
        const headers = new HttpHeaders({
            Authorization: localStorage.getItem('token')
        });
        this.httpClient.post('http://localhost:8080' + '/api/broker/heavy-client/read', {name: entity.name}, {headers})
            .subscribe((responseEntityData: { name: string, rows: EntityModel[] }) => {
                if (type === 'main') {
                    this.mainEntityData = responseEntityData.rows;
                } else {
                    this.relationEntityData = responseEntityData.rows;
                }
                console.log(responseEntityData.rows);
            }, error => {

            });
    }

    createEntity(entity: EntityModel, action: string) {
        const headers = new HttpHeaders({
            Authorization: localStorage.getItem('token'),
            'Content-Type': 'application/json'
        });
        const path = action === 'Create' ? '/api/broker/heavy-client/heavy-client-composite?TYPE=CREATE' : '/api/broker/heavy-client/heavy-client-composite?TYPE=UPDATE';
        this.httpClient.post('http://localhost:8080' + path, entity, {headers, responseType: 'text'})
            .subscribe((response: string) => {
                this.getDataForEntity(entity, 'main');
                this.getDataForEntity(entity, 'relations');
            });

    }

    deleteEntity(entity: EntityModel) {
        const headers = new HttpHeaders({
            Authorization: localStorage.getItem('token')
        });
        console.log('delete', entity);
        this.httpClient.post('http://localhost:8080' + '/api/broker/heavy-client/heavy-client-composite?TYPE=DELETE', entity, {
            headers,
            responseType: 'text'
        })
            .subscribe((response: string) => {
                this.getDataForEntity(entity, 'main');
                this.getDataForEntity(entity, 'relations');
            });
    }


}
