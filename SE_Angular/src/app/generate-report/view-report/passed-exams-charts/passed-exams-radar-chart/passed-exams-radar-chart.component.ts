import {Component, OnInit} from '@angular/core';
import {ChartDataSets} from 'chart.js';
import {GenerateReportService} from '../../../generate-report.service';
import {PassedExamsModel} from '../../../models/passed-exams.model';

@Component({
    selector: 'app-passed-exams-radar-chart',
    templateUrl: './passed-exams-radar-chart.component.html',
    styleUrls: ['./passed-exams-radar-chart.component.css']
})
export class PassedExamsRadarChartComponent implements OnInit {

    options = {
        scaleShowVerticalLines: false,
        responsive: true
    };

    labels = [];
    years = [];
    type = 'radar';
    legend = true;
    data: ChartDataSets[] = [];

    constructor(private generateReportService: GenerateReportService) {
    }

    ngOnInit() {
        this.generateReportService.graphDataEmitter.subscribe(graphData => {
            this.data.splice(0, this.data.length);
            this.labels = [];
            this.years = [];
            this.generateReportService.currentGraphData.forEach(test => {
                const exam = test as PassedExamsModel;
                if (this.labels.filter(name => name === exam.courseName).length === 0) {
                    this.labels.push(exam.courseName);
                }
                if (this.years.filter(year => year === exam.passDate.split('-')[0]).length === 0) {
                    this.years.push(exam.passDate.split('-')[0]);
                }
            });
            this.years.forEach(year => {
                const result = [];
                this.labels.forEach(lbl => {
                    const exams = this.generateReportService.currentGraphData.filter(test2 => test2.courseName === lbl);
                    const res = exams.filter(ex => ex.passDate.split('-')[0] === year).length;
                    result.push(res);
                });
                const len = this.data.length;
                this.data.push({data: result, label: year, backgroundColor: this.getColor(len),
                    borderColor: this.getBorder(len), pointBackgroundColor: this.getPoint(len)});
            });
        });
    }

    getColor(index) {
        switch (index) {
            case 0:
                return 'rgb(255,210,101, 0.5)';
            case 1:
                return 'rgb(255,99,132, 0.5)';
            case 2:
                return 'rgb(54,162,235, 0.5)';
        }
    }
    getBorder(index) {
        switch (index) {
            case 0:
                return 'rgb(255,210,101, 0.7)';
            case 1:
                return 'rgb(255,99,132, 0.7)';
            case 2:
                return 'rgb(54,162,235, 0.7)';
        }
    }

    getPoint(index) {
        switch (index) {
            case 0:
                return 'rgb(255,210,101, 0.9)';
            case 1:
                return 'rgb(255,99,132, 0.9)';
            case 2:
                return 'rgb(54,162,235, 0.9)';
        }
    }
}


