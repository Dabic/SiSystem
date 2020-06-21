import {EventEmitter, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {DatabaseModel} from '../models/database/database.model';
import {EntityTabsService} from '../entity-view/entity-tabs/entity-tabs.service';
import {EntityModel} from '../models/entity/entity.model';

@Injectable({
    providedIn: 'root'
})
export class TreeService {
    eventEmitter = new EventEmitter();
    databaseStorage: DatabaseModel;

    constructor(private httpClient: HttpClient, private entityTabsService: EntityTabsService) {
    }

    getDatabaseModel() {
        let database: DatabaseModel;
        this.httpClient
            .get('http://localhost:8080' + '/api/broker/heavy-client/read-meta-data/',
                {headers: {Authorization: localStorage.getItem('token')}})
            .subscribe((response: DatabaseModel) => {
                console.log(response);
                database = response;
                this.databaseStorage = database;
                this.eventEmitter.emit(database);
            });
    }

    entityDoubleClicked(entityName: string) {
        this.entityTabsService.openMainEntity(this.getFromStorage(entityName));
        console.log('VALUE', this.getFromStorage(entityName).attributes[0].value);
    }

    getFromStorage(entityName: string) {
        return this.databaseStorage.entities.filter(ent => ent.name === entityName)[0];
    }
}
