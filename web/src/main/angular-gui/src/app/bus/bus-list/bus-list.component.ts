import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Bus} from "../../model/bus";
import {BusService} from "../service/bus.service";
import {Driver} from "../../model/driver";

@Component({
  selector: 'app-bus-list',
  templateUrl: './bus-list.component.html',
  styleUrls: ['./bus-list.component.css']
})
export class BusListComponent implements OnInit {
  errorMessage: string = ""
  buses: Array<Bus> = []
  availableDrivers: Array<Driver> = []
  selectedDriver: Driver | undefined
  show: boolean = true
  page: number = 0
  canAdd: boolean = false

  @ViewChild('modelInput') modelInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('idUpdateInput') idUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('modelUpdateInput') modelUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('filterModelName') filterModelName: ElementRef<HTMLInputElement> | undefined
  @ViewChild('driverSelect') driverSelect: ElementRef<HTMLSelectElement> | undefined

  constructor(private busService: BusService) { }

  ngOnInit(): void {
    this.getBuses(this.page)
    this.getDrivers()
    console.log(this.buses)
    console.log(this.availableDrivers)
  }

  getBuses(page: number) {
    this.busService.getAll(page)
      .subscribe(
        busesDto => {
          console.log(busesDto)
          this.buses = busesDto.buses
        },
        error => this.errorMessage = <any> error
      )
  }

  addBus(model: string) {
    // @ts-ignore
    this.busService.addBus(model, this.selectedDriver?.id).subscribe(
      result => {
        console.log(result)
        this.getBuses(this.page)
        this.getDrivers()
        this.canAdd = false
      }
    )
    // @ts-ignore
    this.modelInput?.nativeElement.value = ''
    // @ts-ignore
    this.driverSelect?.nativeElement.value = 'Choose Driver'
  }

  deleteBus(id: number) {
    this.busService.deleteBus(id)
      .subscribe(
        result => {
          console.log(result)
          this.getBuses(this.page)
          this.getDrivers()
        }
      )
  }

  loadBusData(bus: Bus) {
    this.show = !this.show;

    // @ts-ignore
    this.idUpdateInput?.nativeElement.value = bus.id.toString()
    // @ts-ignore
    this.modelUpdateInput?.nativeElement.value = bus.modelName
  }

  updateBus(idString: string, model: string) {
    let id = parseInt(idString)
    this.busService.updateBus(id, model)
      .subscribe(
        result => {
          console.log(result)
          this.getBuses(this.page)
          this.show = true
        }
      )
  }

  filterByModel(model: string) {
    this.busService.getBusesByModel(model)
      .subscribe(
        busesDto => {
          this.buses = busesDto.buses
          // @ts-ignore
          this.filterModelName?.nativeElement.value = ''
        }
      )
  }

  refresh() {
    this.getBuses(this.page)
  }

  increasePage() {
    this.page += 1
    this.getBuses(this.page)
  }

  decreasePage() {
    if (this.page > 0) {
      this.page -= 1
      this.getBuses(this.page)
    }
  }

  getDrivers() {
    this.busService.getDrivers()
      .subscribe(
        dto => {
          this.availableDrivers = dto
        }
      )
  }

  setDriver() {
    let driverName = this.driverSelect?.nativeElement.value

    if (driverName === "Choose Driver") return

    for (let driver of this.availableDrivers) {
      if (driverName === driver.name) {
        this.selectedDriver = driver
        this.canAdd = true
      }
    }
  }
}
