import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BusComponent } from './bus/bus.component';
import {HomepageComponent} from "./homepage/homepage.component";
import {CityComponent} from "./city/city.component";
import {StationComponent} from "./station/station.component";
import {DriverComponent} from "./driver/driver.component";

const routes: Routes = [
  {
    path: "bus-component", component: BusComponent,
  },
  {
    path: "", component: HomepageComponent
  },
  {
    path: "city-component", component: CityComponent
  },
  {
    path: "station-component", component: StationComponent
  },
  {
    path: "driver-component", component: DriverComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
