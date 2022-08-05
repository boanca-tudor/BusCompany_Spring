import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BusComponent } from './bus/bus.component';
import { BusListComponent } from './bus/bus-list/bus-list.component';
import { HttpClientModule } from "@angular/common/http";
import { HomepageComponent } from './homepage/homepage.component';
import { CityComponent } from './city/city.component';
import { CityListComponent } from './city/city-list/city-list.component';
import { StationComponent } from './station/station.component';
import { StationListComponent } from './station/station-list/station-list.component';
import { DriverComponent } from './driver/driver.component';
import { DriverListComponent } from './driver/driver-list/driver-list.component';

@NgModule({
  declarations: [
    AppComponent,
    BusComponent,
    BusListComponent,
    HomepageComponent,
    CityComponent,
    CityListComponent,
    StationComponent,
    StationListComponent,
    DriverComponent,
    DriverListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
