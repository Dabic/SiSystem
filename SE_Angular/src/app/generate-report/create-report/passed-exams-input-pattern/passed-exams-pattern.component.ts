import { Component, OnInit } from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
  selector: 'app-passed-exams-pattern',
  templateUrl: './passed-exams-pattern.component.html',
  styleUrls: ['./passed-exams-pattern.component.css']
})
export class PassedExamsPatternComponent implements OnInit {

  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
