import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Bus, BusDTO} from "../../model/bus";

@Injectable({
  providedIn: 'root'
})
export class BusService {
  private url = "http://localhost:8085/buses";

  constructor(private client: HttpClient) { }

  getAll(page: number): Observable<BusDTO> {
    return this.client.get<BusDTO>(this.url + `/getAll?page=${page}`);
  }

  findAll(): Observable<BusDTO> {
    return this.client.get<BusDTO>(this.url + '/findAll');
  }

  getBusesByModel(model: string): Observable<BusDTO> {
    return this.client.get<BusDTO>(this.url + `/getByModel/${model}`)
  }

  addBus(model: string, driverId: number): Observable<any> {
    return this.client.post(
      this.url + "/save", {
        modelName: model,
        driverId: driverId
      }
    )
  }

  deleteBus(id: number): Observable<any> {
    return this.client.delete(this.url + `/delete/${id}`)
  }

  updateBus(id: number, model: string): Observable<any> {
    return this.client.put(this.url + `/update/${id}`, {
      modelName: model
    })
  }

  getDrivers() : Observable<any> {
    return this.client.get(this.url + "/getDrivers")
  }
}
