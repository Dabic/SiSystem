import {Component, OnInit} from '@angular/core';
import {GenerateReportService} from '../../generate-report.service';

@Component({
    selector: 'app-view-table',
    templateUrl: './view-table.component.html',
    styleUrls: ['./view-table.component.css']
})
export class ViewTableComponent implements OnInit {

    data: any;
    heading: string[] = [];
    result: any;

    constructor(private generateReportService: GenerateReportService) {
    }

    ngOnInit() {
        this.generateReportService.reportDataEmitter.subscribe(reportData => {
            console.log('cao');
            this.data = [];
            this.heading = [];
            reportData.forEach(test => {
                this.result = Object.keys(test).map((key) => {
                    return test[key];
                });

                if (this.heading.length === 0) {
                  this.heading = Object.keys(test).map((key) => {
                    let newKey = '';
                    for (let i = 0; i < key.length; i++) {
                      newKey = newKey.concat(key[i].toUpperCase());
                      if (i + 1 < key.length) {
                        if (key[i + 1].toUpperCase() === key[i + 1]) {
                          newKey = newKey.concat('_');
                        }
                      }
                    }
                    return newKey;
                  });
                }
                this.data.push(this.result);
            });
        });
    }
}
