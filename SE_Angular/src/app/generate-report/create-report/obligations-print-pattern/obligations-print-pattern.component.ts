import {Component, Input, OnInit} from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
  selector: 'app-obligations-print-pattern',
  templateUrl: './obligations-print-pattern.component.html',
  styleUrls: ['./obligations-print-pattern.component.css']
})
export class ObligationsPrintPatternComponent implements OnInit {

  @Input() state: number;
  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
