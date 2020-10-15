import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CurrentTabComponent } from './tabs/current-tab/current-tab.component';
import { HourlyTabComponent } from './tabs/hourly-tab/hourly-tab.component';
import { WeeklyTabComponent } from './tabs/weekly-tab/weekly-tab.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  {
    path: 'currenttab', component: CurrentTabComponent,
  },
  {
    path: 'hourlytab', component: HourlyTabComponent
  },
  {
    path: 'weeklytab', component: WeeklyTabComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // Tell the router to use the HashLocationStrategy.
    useHash: true
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
