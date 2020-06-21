import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent implements OnInit {

  @Input() private buttonName: string;
  @Input() private buttonText: string;
  @Input() private disabled: boolean;
  @Input() private danger = false;
  @Input() private buttonType: string;

  constructor() {
  }

  ngOnInit() {
  }

}
