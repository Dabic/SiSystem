import { Component, OnInit } from '@angular/core';
import {GenerateReportService} from '../generate-report.service';

@Component({
  selector: 'app-create-report',
  templateUrl: './create-report.component.html',
  styleUrls: ['./create-report.component.css']
})
export class CreateReportComponent implements OnInit {

  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
