import {Component, Input, OnInit} from '@angular/core';
import {FontAwesomeService} from '../../../font-awesome.service';

@Component({
  selector: 'app-entity',
  templateUrl: './entity.component.html',
  styleUrls: ['./entity.component.css']
})
export class EntityComponent implements OnInit {

  @Input() private entityName: string;
  @Input() private attributeList: { attributeName: string }[];
  private caretDown;
  private caretRight;

  constructor(private fontAwesomeService: FontAwesomeService) {
  }

  ngOnInit() {
    this.caretDown = this.fontAwesomeService.getIcon('caret-down');
    this.caretRight = this.fontAwesomeService.getIcon('caret-right');
  }

}
