import {EventEmitter, Injectable} from '@angular/core';
import {ReportModel} from './report.model';
import {HttpClient} from '@angular/common/http';
import {PassedExamsModel} from './models/passed-exams.model';
import {CompareCompetencesModel} from './models/compare-competences.model';

@Injectable({
    providedIn: 'root'
})
export class GenerateReportService {
    currentPrintState = 0;
    patternName: string;
    equality: string;
    rangeField: string;
    rangeCriteria: string;
    rangeValue: string;
    includesFields: string[];
    report = null;
    currentReportData: any[] = [];
    currentGraphData: PassedExamsModel[] = [];
    graphDataEmitter = new EventEmitter();
    reportDataEmitter = new EventEmitter();

    selectedSubjects: string[] = ['', '', '', '', ''];

    compareData: CompareCompetencesModel[];
    compareDataEmitter = new EventEmitter();

    constructor(private httpClient: HttpClient) {

    }

    setCurrentPrintState(state: number) {
        this.patternName = '';
        this.rangeField = '';
        this.rangeCriteria = '';
        this.rangeValue = '';
        this.includesFields = [];
        this.currentPrintState = state;
        this.selectedSubjects = ['', '', '', '', ''];
    }

    changeEquality(event: any) {
        this.equality = event.target.value;
    }

    addRemoveField(field: string) {
        let index = -1;
        this.includesFields.forEach((f, i) => {
            if (f === field) {
                index = i;
            }
        });
        if (index === -1) {
            this.includesFields.push(field);
        } else {
            this.includesFields.splice(index, 1);
        }
    }

    changeRangeField(field: string) {
        this.rangeField = field;
    }

    changeRangeCriteria(range: string) {
        this.rangeCriteria = range;
    }

    changeRangeValue(event: any) {
        this.rangeValue = event.target.value;
    }

    selectSubject(name: string, index) {
        this.selectedSubjects[index] = name;
    }

    onCompare() {
        const courses = [];
        this.selectedSubjects.forEach(c => {
            if (c !== '') {
                courses.push({course: c});
            }
        });
        this.httpClient.post('http://localhost:3000/service-with-arango/compare/', courses)
            .subscribe((response: CompareCompetencesModel[]) => {
                this.compareData = response;
                this.compareDataEmitter.emit(response);
            });
    }

    onGenerate() {
        if (localStorage.getItem('role') === 'Employee') {
            let rangeString: string;
            switch (this.rangeCriteria) {
                case 'sw':
                    rangeString = `{${this.rangeField}: {$regex: '^${this.rangeValue}'}}`;
                    break;
                case 'ew':
                    rangeString = `{${this.rangeField}: {$regex: '${this.rangeValue}$'}}`;
                    break;
                case 'cs':
                    rangeString = `{${this.rangeField}: {$regex: '${this.rangeValue}'}}`;
                    break;
                case 'gt':
                    rangeString = `{${this.rangeField}: {$gt: '${this.rangeValue}'}}`;
                    break;
                case 'lt':
                    rangeString = `{${this.rangeField}: {$lt: '${this.rangeValue}'}}`;
                    break;
                case 'eq':
                    rangeString = `{${this.rangeField}: '${this.rangeValue}'}`;
                    break;
            }
            switch (this.currentPrintState) {
                case 1:
                    this.patternName = 'pattern1';
                    this.report = new ReportModel('pattern1', this.equality, rangeString, this.includesFields);
                    break;
                case 2:
                    this.patternName = 'pattern2';
                    this.report = new ReportModel('pattern2', this.equality, rangeString, this.includesFields);
                    break;
                case 3:
                    this.patternName = 'pattern3';
                    this.report = new ReportModel('pattern3', this.equality, rangeString, this.includesFields);
                    break;
            }
            this.httpClient.post('http://localhost:8085/service-with-mongo/generate/', this.report)
                .subscribe((response: any) => {
                    this.currentReportData = response;
                    this.reportDataEmitter.emit(response);
                });
            this.httpClient.post('http://localhost:8085/service-with-mongo/chart/', 'pattern1')
                .subscribe((response: PassedExamsModel[]) => {
                    this.currentGraphData = response;
                    this.graphDataEmitter.emit(response);
                });
        }

    }

}
