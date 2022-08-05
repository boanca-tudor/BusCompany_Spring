import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FilteredStationDTO, Station, StationDTO} from "../../model/station";
import {StopDTO} from "../../model/stop";

@Injectable({
  providedIn: 'root'
})
export class StationService {
  private url = "http://localhost:8085/stations";

  constructor(private client: HttpClient) { }

  getAll(page: number): Observable<StationDTO> {
    return this.client.get<StationDTO>(this.url + `/getAll?page=${page}`);
  }

  addStation(name: string, cityId: number): Observable<any> {
    return this.client.post(
      this.url + "/save", {
        name: name,
        cityId: cityId
      }
    )
  }

  deleteStation(id: number): Observable<any> {
    return this.client.delete(this.url + `/delete/${id}`)
  }

  updateStation(id: number, name: string): Observable<any> {
    return this.client.put(this.url + `/update/${id}`, {
      name: name
    })
  }

  filterByCityName(name: string) : Observable<FilteredStationDTO> {
    return this.client.get<FilteredStationDTO>(
      this.url + `/filterByCity/${name}`
    )
  }

  addStop(busId: number, stationId: number, stopTime: string) {
    return this.client.post(
      this.url + "/addStop", {
        busId: busId,
        stationId: stationId,
        stopTime: stopTime
      }
    )
  }

  getStopsForStation(stationId: number): Observable<StopDTO> {
    return this.client.get<StopDTO>(
      this.url + `/getStops?stationId=${stationId}`
    )
  }

  deleteStop(busId: number, stationId: number) {
    return this.client.delete(
      this.url + `/deleteStop?busId=${busId}&stationId=${stationId}`
    )
  }
}
