import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DriverDTO} from "../../model/driver";

@Injectable({
  providedIn: 'root'
})
export class DriverService {
  private url = "http://localhost:8085/drivers";

  constructor(private client: HttpClient) { }

  getAll(page: number): Observable<DriverDTO> {
    return this.client.get<DriverDTO>(this.url + `/getAll?page=${page}`);
  }

  addDriver(name: string, cnp: string): Observable<any> {
    return this.client.post(
      this.url + "/save", {
        name: name,
        cnp: cnp
      }
    )
  }

  deleteDriver(id: number): Observable<any> {
    return this.client.delete(this.url + `/delete/${id}`)
  }

  updateDriver(id: number, name: string, cnp: string): Observable<any> {
    return this.client.put(this.url + `/update/${id}`, {
      name: name,
      cnp: cnp
    })
  }
}
