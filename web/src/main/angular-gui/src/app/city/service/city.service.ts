import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Bus, BusDTO} from "../../model/bus";
import {City, CityDTO} from "../../model/city";

@Injectable({
  providedIn: 'root'
})
export class CityService {
  private url = "http://localhost:8085/cities";

  constructor(private client: HttpClient) { }

  getAll(page: number): Observable<CityDTO> {
    return this.client.get<CityDTO>(this.url + `/getAll?page=${page}`);
  }

  addCity(name: string, population: number): Observable<any> {
    return this.client.post(
      this.url + "/save", {
        name: name,
        population: population
      }
    )
  }

  deleteCity(id: number): Observable<any> {
    return this.client.delete(this.url + `/delete/${id}`)
  }

  updateCity(city: City): Observable<any> {
    return this.client.put(this.url + `/update/${city.id}`, {
      name: city.name,
      population: city.population
    })
  }

  findAll() : Observable<CityDTO> {
    return this.client.get<CityDTO>(this.url + '/findAll');
  }
}
