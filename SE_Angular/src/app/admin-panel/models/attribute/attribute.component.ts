import {Component, Input, OnInit} from '@angular/core';
import {FontAwesomeService} from '../../../font-awesome.service';

@Component({
  selector: 'app-attribute',
  templateUrl: './attribute.component.html',
  styleUrls: ['./attribute.component.css']
})
export class AttributeComponent implements OnInit {

  @Input() private attributeName: string;
  private caretDown;
  private caretRight;

  constructor(private fontAwesomeService: FontAwesomeService) {
  }

  ngOnInit() {
    this.caretDown = this.fontAwesomeService.getIcon('caret-down');
    this.caretRight = this.fontAwesomeService.getIcon('caret-right');
  }

}
