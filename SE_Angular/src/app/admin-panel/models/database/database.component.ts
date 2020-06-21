import {Component, Input, OnInit, Renderer2} from '@angular/core';
import {FontAwesomeService} from '../../../font-awesome.service';

@Component({
  selector: 'app-database',
  templateUrl: './database.component.html',
  styleUrls: ['./database.component.css']
})
export class DatabaseComponent implements OnInit {

  @Input() private databaseName: string;
  private caretRight;
  private caretDown;

  constructor(private fontAwesomeService: FontAwesomeService) {
  }

  ngOnInit() {
    this.caretDown = this.fontAwesomeService.getIcon('caret-down');
    this.caretRight = this.fontAwesomeService.getIcon('caret-right');
  }

}
