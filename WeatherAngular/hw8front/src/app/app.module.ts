import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatAutocompleteModule, MatInputModule } from '@angular/material';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { ResTabsComponent } from './res-tabs/res-tabs.component';
import { CurrentTabComponent } from './tabs/current-tab/current-tab.component';
import { FormerrorComponent } from './formerror/formerror.component';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';
import { HourlyTabComponent } from './tabs/hourly-tab/hourly-tab.component';
import { WeeklyTabComponent, NgbdModalContent } from './tabs/weekly-tab/weekly-tab.component';
import { ResultContainerComponent } from './result-container/result-container.component';

import { SearchService } from './services/search.service';
import { FavoriteComponent } from './favorite/favorite.component';
import { ProgressBarComponent } from './progress-bar/progress-bar.component';
import { ChartsModule } from 'ng2-charts';


@NgModule({
  declarations: [
    AppComponent,
    SearchFormComponent,
    ResTabsComponent,
    CurrentTabComponent,
    FormerrorComponent,
    AutocompleteComponent,
    HourlyTabComponent,
    WeeklyTabComponent,
    FavoriteComponent,
    ProgressBarComponent,
    ResultContainerComponent,
    NgbdModalContent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatAutocompleteModule,
    MatInputModule,
    ChartsModule,
    NgbModule
  ],
  providers: [
    SearchService
  ],
  bootstrap: [AppComponent],
  entryComponents: [NgbdModalContent]
})
export class AppModule { }
