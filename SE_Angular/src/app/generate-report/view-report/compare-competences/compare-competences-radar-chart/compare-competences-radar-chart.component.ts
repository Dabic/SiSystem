import {Component, Input, OnInit} from '@angular/core';
import {ChartDataSets} from 'chart.js';
import {GenerateReportService} from '../../../generate-report.service';
import {CompareCompetencesModel} from '../../../models/compare-competences.model';

@Component({
    selector: 'app-compare-competences-radar-chart',
    templateUrl: './compare-competences-radar-chart.component.html',
    styleUrls: ['./compare-competences-radar-chart.component.css']
})
export class CompareCompetencesRadarChartComponent implements OnInit {

    options = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    labels = [];
    type = 'radar';
    legend = true;
    data: ChartDataSets[] = [];
    @Input() selected: string;

    constructor(private generateReportService: GenerateReportService) {
    }

    ngOnInit() {
        this.generateReportService.compareDataEmitter.subscribe(compareData => {
            if (this.selected === 'comp') {
                this.labels = [];
                this.data = [];
                compareData.forEach(test => {
                    const model = test as CompareCompetencesModel;
                    model.myCompetences.forEach(myComp => {
                        myComp.competences.forEach(comp => {
                            if (this.labels.filter(lbl => lbl === comp.competence).length === 0) {
                                this.labels.push(comp.competence);
                            }
                        });
                    });
                    const myData = [];
                    this.labels.forEach(lbl => {
                        myData.push(Math.floor(Math.random() * 15));
                    });
                    this.data.push({data: myData, label: model.course});
                });
            } else {
                this.labels = [];
                this.data = [];
                compareData.forEach(test => {
                    const model = test as CompareCompetencesModel;
                    model.myCompetences.forEach(myComp => {
                        if (this.labels.filter(lbl => lbl === myComp.general.name).length === 0) {
                            this.labels.push(myComp.general.name);
                        }
                    });
                    const myData = [];
                    this.labels.forEach(lbl => {
                        myData.push(Math.floor(Math.random() * 15) + 3);
                    });
                    this.data.push({data: myData, label: model.course});
                });
            }
        });
    }

}
