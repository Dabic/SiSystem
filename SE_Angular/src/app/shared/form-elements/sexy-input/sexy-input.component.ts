import {Component, ElementRef, Input, OnInit, Renderer2, ViewChild} from '@angular/core';
import {FontAwesomeService} from '../../../font-awesome.service';
import {FocusService} from './focus.service';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-sexy-input',
  templateUrl: './sexy-input.component.html',
  styleUrls: ['./sexy-input.component.css'],
  providers: [FocusService]
})
export class SexyInputComponent implements OnInit {

  @Input() private type: string;
  @Input() private inputName: string;
  @Input() private placeholder: string;
  @Input() private imageClassName: string;
  @Input() private parentForm: FormGroup;
  @Input() private errorMsg: string;
  @Input() private showLabel: boolean;
  @ViewChild('container', {static: true}) container: ElementRef;
  imageClass;

  constructor(private fontAwesomeService: FontAwesomeService, private focusService: FocusService, private renderer: Renderer2) {
    this.focusService.focusEmitter.subscribe(obs => {
      if (obs === true) {
        this.renderer.setStyle(this.container.nativeElement, 'border-color', '#2895c7');
      } else {
        this.renderer.setStyle(this.container.nativeElement, 'border-color', '#d6d6d6');
      }
    });
  }

  ngOnInit() {
    if (this.imageClassName != null) {
      this.imageClass = this.fontAwesomeService.getIcon(this.imageClassName);
    }
  }

}
