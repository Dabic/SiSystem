import { Component, OnInit } from '@angular/core';
import {GenerateReportService} from './generate-report.service';

@Component({
  selector: 'app-generate-report',
  templateUrl: './generate-report.component.html',
  styleUrls: ['./generate-report.component.css']
})
export class GenerateReportComponent implements OnInit {

  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
