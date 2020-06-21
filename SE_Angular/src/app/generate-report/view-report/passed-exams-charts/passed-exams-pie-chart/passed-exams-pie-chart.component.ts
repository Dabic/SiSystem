import {Component, OnInit} from '@angular/core';
import {ChartDataSets} from 'chart.js';
import {GenerateReportService} from '../../../generate-report.service';
import {PassedExamsModel} from '../../../models/passed-exams.model';

@Component({
    selector: 'app-passed-exams-pie-chart',
    templateUrl: './passed-exams-pie-chart.component.html',
    styleUrls: ['./passed-exams-pie-chart.component.css']
})
export class PassedExamsPieChartComponent implements OnInit {

    options = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    labels = [];
    names = [];
    type = 'pie';
    legend = true;
    data: ChartDataSets[] = [{
        data: []
    }];

    constructor(private generateReportService: GenerateReportService) {
    }

    ngOnInit() {
        this.generateReportService.graphDataEmitter.subscribe(graphData => {
            this.data.splice(0, this.data.length);
            this.data.push({data: []});
            this.labels = [];
            this.names = [];
            this.generateReportService.currentGraphData.forEach(test => {
                const exam = test as PassedExamsModel;
                if (this.labels.filter(name => name === exam.studentName + ' ' + exam.studentLastName).length === 0) {
                    this.labels.push(exam.studentName + ' ' + exam.studentLastName);
                }
                this.names.push(exam.studentName + ' ' + exam.studentLastName);
            });
            const colors = [];
            this.labels.forEach(lbl => {
                this.data[0].data.push(this.names.filter(name => name === lbl).length);
                colors.push(this.makeColor());
            });
            this.data[0].data.push(0);
            this.data[0].backgroundColor = colors;
        });
    }

    makeColor() {
        const r = Math.floor(Math.random() * 255);
        const g = Math.floor(Math.random() * 255);
        const b = Math.floor(Math.random() * 255);
        return 'rgb(' + r + ',' + g + ',' + b + ',' + 0.7 + ')';
    }


}
