import {Component, OnInit} from '@angular/core';
import {DatabaseModel} from '../models/database/database.model';
import {TreeService} from './tree.service';

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit {

  isLoading = true;
  tree: DatabaseModel = null;

  constructor(private treeService: TreeService) {
  }

  ngOnInit() {
    this.isLoading = true;
    this.treeService.getDatabaseModel();
    this.treeService.eventEmitter.subscribe(value => {
      this.tree = value;
      this.isLoading = false;
    });
  }

}
