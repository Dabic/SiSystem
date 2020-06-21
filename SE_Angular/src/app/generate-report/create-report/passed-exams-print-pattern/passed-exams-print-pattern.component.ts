import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
  selector: 'app-passed-exams-print-pattern',
  templateUrl: './passed-exams-print-pattern.component.html',
  styleUrls: ['./passed-exams-print-pattern.component.css']
})
export class PassedExamsPrintPatternComponent implements OnInit {

  @Input() state: number;
  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
