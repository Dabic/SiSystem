import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AuthenticationUserComponent} from './authentication-user/authentication-user.component';
import {AppRoutingModule} from './app.routing';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {InputComponent} from './shared/form-elements/input/input.component';
import {SexyInputComponent} from './shared/form-elements/sexy-input/sexy-input.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LoggingService} from './logging.service';
import {SexyInputDirective} from './shared/form-elements/sexy-input/sexy-input.directive';
import {ButtonComponent} from './shared/form-elements/button/button.component';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import {TreeComponent} from './admin-panel/tree/tree.component';
import {DatabaseComponent} from './admin-panel/models/database/database.component';
import {EntityComponent} from './admin-panel/models/entity/entity.component';
import {AttributeComponent} from './admin-panel/models/attribute/attribute.component';
import {AttributeFieldsComponent} from './admin-panel/models/attribute-fields/attribute-fields.component';
import {TreeDirective} from './admin-panel/tree/tree.directive';
import {HttpClientModule} from '@angular/common/http';
import {LoadingSpinnerComponent} from './shared/loading-spinner.component';
import {AccountTypeComponent} from './authentication-user/accounts/account-type/account-type.component';
import {AccountTypeItemComponent} from './authentication-user/accounts/account-type/account-type-item/account-type-item.component';
import {AccountsComponent} from './authentication-user/accounts/accounts.component';
import {LoginFormComponent} from './authentication-user/login-form/login-form.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
// tslint:disable-next-line:import-spacing
import {AccountTypeItemMarkComponent}
    from './authentication-user/accounts/account-type/account-type-item/account-type-item-mark/account-type-item-mark.component';
import {MatTabsModule} from '@angular/material/tabs';
import {ModalComponent} from './shared/modal/modal.component';
import {EntityViewComponent} from './admin-panel/entity-view/entity-view.component';
import { EntityTableComponent } from './admin-panel/entity-view/entity-table/entity-table.component';
import { EntityTabsComponent } from './admin-panel/entity-view/entity-tabs/entity-tabs.component';
import { EntityControlsComponent } from './admin-panel/entity-view/entity-controls/entity-controls.component';
import { CreateReadModalComponent } from './admin-panel/entity-view/entity-controls/create-read-modal/create-read-modal.component';
import { GenerateReportComponent } from './generate-report/generate-report.component';
import {MatTooltipModule} from '@angular/material/tooltip';
import { CreateReportComponent } from './generate-report/create-report/create-report.component';
import {MatCheckboxModule, MatFormFieldModule, MatInputModule, MatSelectModule} from '@angular/material';
import { PassedExamsPatternComponent } from './generate-report/create-report/passed-exams-input-pattern/passed-exams-pattern.component';
// tslint:disable-next-line:max-line-length
import { PassedExamsPrintPatternComponent } from './generate-report/create-report/passed-exams-print-pattern/passed-exams-print-pattern.component';
import {ChartsModule} from 'ng2-charts';
// tslint:disable-next-line:max-line-length
import { PassedExamsBarChartComponent } from './generate-report/view-report/passed-exams-charts/passed-exams-bar-chart/passed-exams-bar-chart.component';
import { ViewReportComponent } from './generate-report/view-report/view-report.component';
// tslint:disable-next-line:max-line-length
import { PassedExamsRadarChartComponent } from './generate-report/view-report/passed-exams-charts/passed-exams-radar-chart/passed-exams-radar-chart.component';
// tslint:disable-next-line:max-line-length
import { PassedExamsPieChartComponent } from './generate-report/view-report/passed-exams-charts/passed-exams-pie-chart/passed-exams-pie-chart.component';
import { CompetencesPrintPatternComponent } from './generate-report/create-report/competences-print-pattern/competences-print-pattern.component';
import { ObligationsPrintPatternComponent } from './generate-report/create-report/obligations-print-pattern/obligations-print-pattern.component';
import { FullReportTableComponent } from './generate-report/view-report/full-report-table/full-report-table.component';
import { PartReportTableComponent } from './generate-report/view-report/part-report-table/part-report-table.component';
import { ViewTableComponent } from './generate-report/view-report/view-table/view-table.component';
import { CompareCompetencesComponent } from './generate-report/view-report/compare-competences/compare-competences.component';
import { CompareCompetencesRadarChartComponent } from './generate-report/view-report/compare-competences/compare-competences-radar-chart/compare-competences-radar-chart.component';
import { CompareCompetencesRadarGeneralComponent } from './generate-report/view-report/compare-competences/compare-competences-radar-general/compare-competences-radar-general.component';

@NgModule({
    declarations: [
        AppComponent,
        AuthenticationUserComponent,
        AccountTypeComponent,
        AccountTypeItemComponent,
        AccountsComponent,
        LoginFormComponent,
        AccountTypeItemMarkComponent,
        InputComponent,
        SexyInputComponent,
        SexyInputDirective,
        ButtonComponent,
        AdminPanelComponent,
        TreeComponent,
        DatabaseComponent,
        EntityComponent,
        AttributeComponent,
        AttributeFieldsComponent,
        TreeDirective,
        LoadingSpinnerComponent,
        ModalComponent,
        EntityViewComponent,
        EntityTableComponent,
        EntityTabsComponent,
        EntityControlsComponent,
        CreateReadModalComponent,
        GenerateReportComponent,
        CreateReportComponent,
        PassedExamsPatternComponent,
        PassedExamsPrintPatternComponent,
        PassedExamsBarChartComponent,
        ViewReportComponent,
        PassedExamsRadarChartComponent,
        PassedExamsPieChartComponent,
        CompetencesPrintPatternComponent,
        ObligationsPrintPatternComponent,
        FullReportTableComponent,
        PartReportTableComponent,
        ViewTableComponent,
        CompareCompetencesComponent,
        CompareCompetencesRadarChartComponent,
        CompareCompetencesRadarGeneralComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FontAwesomeModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatTabsModule,
        MatTooltipModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule,
        MatCheckboxModule,
        ChartsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
