import {Component, Input, OnInit} from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
  selector: 'app-competences-print-pattern',
  templateUrl: './competences-print-pattern.component.html',
  styleUrls: ['./competences-print-pattern.component.css']
})
export class CompetencesPrintPatternComponent implements OnInit {

  @Input() state: number;
  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
