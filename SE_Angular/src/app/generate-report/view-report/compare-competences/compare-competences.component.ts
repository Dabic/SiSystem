import { Component, OnInit } from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
  selector: 'app-compare-competences',
  templateUrl: './compare-competences.component.html',
  styleUrls: ['./compare-competences.component.css']
})
export class CompareCompetencesComponent implements OnInit {

  selected = 'comp';
  constructor(private generateReportService: GenerateReportService) { }

  ngOnInit() {
  }

}
