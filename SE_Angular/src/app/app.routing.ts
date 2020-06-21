import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthenticationUserComponent} from './authentication-user/authentication-user.component';
import {AdminPanelComponent} from './admin-panel/admin-panel.component';
import {GenerateReportComponent} from './generate-report/generate-report.component';
import {AuthGuardService} from './shared/auth-guard.service';

const appRoutes: Routes = [
    {path: '', redirectTo: '/authentication', pathMatch: 'full'},
    {path: 'authentication', component: AuthenticationUserComponent},
    {path: 'admin-panel', component: AdminPanelComponent, canActivate: [AuthGuardService]},
    {path: 'generate-report', component: GenerateReportComponent, canActivate: [AuthGuardService]},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
