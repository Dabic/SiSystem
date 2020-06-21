import {Component, OnInit} from '@angular/core';
import {GenerateReportService} from '../../../generate-report.service';
import {ChartDataSets} from 'chart.js';
import {PassedExamsModel} from '../../../models/passed-exams.model';

@Component({
    selector: 'app-passed-exams-bar-chart',
    templateUrl: './passed-exams-bar-chart.component.html',
    styleUrls: ['./passed-exams-bar-chart.component.css']
})
export class PassedExamsBarChartComponent implements OnInit {

    options = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    labels = [];
    names = [];
    type = 'bar';
    legend = true;
    data: ChartDataSets[] = [{
        data: [],
        label: 'Passed exams'
    }];

    constructor(private generateReportService: GenerateReportService) {

    }

    ngOnInit() {
        this.generateReportService.graphDataEmitter.subscribe(graphData => {
            this.data.splice(0, this.data.length);
            this.data.push({data: [], label: 'Passed exams', backgroundColor: 'rgb(255,210,101, 0.7)'});
            this.labels = [];
            this.names = [];
            graphData.forEach(test => {
                const exam = test as PassedExamsModel;
                if (this.labels.filter(e => e === exam.courseName).length === 0) {
                    this.labels.push(exam.courseName);
                }
                this.names.push(exam.courseName);
            });
            this.labels.forEach(lbl => {
                this.data[0].data.push(this.names.filter(name => name === lbl).length);
            });
            this.data[0].data.push(0);
        });
    }

}
