import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {City} from "../../model/city";
import {Driver} from "../../model/driver";
import {DriverService} from "../service/driver.service";

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.css']
})
export class DriverListComponent implements OnInit {
  errorMessage: string = ""
  drivers: Array<Driver> = []
  show: boolean = true
  page: number = 0

  @ViewChild('nameInput') nameInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('cnpInput') cnpInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('idUpdateInput') idUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('nameUpdateInput') nameUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('cnpUpdateInput') cnpUpdateInput: ElementRef<HTMLInputElement> | undefined

  constructor(private driverService: DriverService) { }

  ngOnInit(): void {
    this.getDrivers(this.page)
    console.log(this.drivers)
  }

  getDrivers(page: number) {
    this.driverService.getAll(page)
      .subscribe(
        dto => {
          console.log(dto)
          this.drivers = dto.drivers
        },
        error => this.errorMessage = <any> error
      )
  }

  addDriver(name: string, cnp: string) {
    this.driverService.addDriver(name, cnp).
    subscribe(
      result => {
        console.log(result)
        this.getDrivers(this.page)
      }
    )

    // @ts-ignore
    this.nameInput?.nativeElement.value = ''
    // @ts-ignore
    this.cnpInput?.nativeElement.value = "0"
  }

  deleteDriver(id: number) {
    this.driverService.deleteDriver(id)
      .subscribe(
        result => {
          console.log(result)
          this.getDrivers(this.page)
        }
      )
  }

  loadDriverData(driver: Driver) {
    this.show = !this.show;

    // @ts-ignore
    this.idUpdateInput?.nativeElement.value = driver.id.toString()
    // @ts-ignore
    this.nameUpdateInput?.nativeElement.value = driver.name
    // @ts-ignore
    this.cnpUpdateInput?.nativeElement.value = driver.cnp

  }

  updateDriver(idString: string, name: string, cnp: string) {
    let id = parseInt(idString)
    this.driverService.updateDriver(id, name, cnp)
      .subscribe(
        result => {
          console.log(result)
          this.getDrivers(this.page)
          this.show = true
        }
      )
  }

  increasePage() {
    this.page += 1
    this.getDrivers(this.page)
  }

  decreasePage() {
    if (this.page > 0) {
      this.page -= 1
      this.getDrivers(this.page)
    }
  }

}
