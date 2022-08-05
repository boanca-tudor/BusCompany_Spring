import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {City} from "../../model/city";
import {CityService} from "../../city/service/city.service";
import {FilteredStation, Station} from "../../model/station";
import {StationService} from "../service/station.service";
import {Stop} from "../../model/stop";
import {BusService} from "../../bus/service/bus.service";
import {Bus} from "../../model/bus";

@Component({
  selector: 'app-station-list',
  templateUrl: './station-list.component.html',
  styleUrls: ['./station-list.component.css']
})
export class StationListComponent implements OnInit {
  errorMessage: string = ""
  stations: Array<Station> = []
  filteredStations: Array<FilteredStation> = []
  cities: Array<City> = []
  stops: Array<Stop> = []
  buses: Array<Bus> = []
  selectedCity: City | undefined
  selectedBus: Bus | undefined
  selectedStation: Station | undefined
  stopsStation: Station | undefined
  hide: boolean = true
  hideFiltered: boolean = true
  hideStopAdd: boolean = true
  hideStopView: boolean = true
  page: number = 0

  @ViewChild('nameInput') nameInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('cityIdInput') cityIdInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('idUpdateInput') idUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('nameUpdateInput') nameUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('cityIdUpdateInput') cityIdUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('cityNameInput') cityNameInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('citySelect') citySelect: ElementRef<HTMLSelectElement> | undefined
  @ViewChild('busSelect') busSelect: ElementRef<HTMLSelectElement> | undefined
  @ViewChild('stopTimeInput') stopTimeInput: ElementRef<HTMLInputElement> | undefined

  constructor(private stationService: StationService,
              private cityService: CityService,
              private busService: BusService) { }

  ngOnInit(): void {
    this.getStations(this.page)
    this.getCities()
    this.getBuses()
  }

  getStations(page: number) {
    this.stationService.getAll(page)
      .subscribe(
        stationsDto => {
          console.log(stationsDto)
          this.stations = stationsDto.stations
        },
        error => this.errorMessage = <any> error
      )
  }

  getCities() {
    this.cityService.findAll()
      .subscribe(
        dto => {
          console.log(dto)
          this.cities = dto.cities
        },
        error => this.errorMessage = <any> error
      )
  }

  getBuses() {
    this.busService.findAll()
      .subscribe(
        dto => {
          console.log(dto)
          this.buses = dto.buses
        },
        error => this.errorMessage = <any> error
      )
  }

  addStation(name: string) {
    // @ts-ignore
    this.stationService.addStation(name, this.selectedCity.id).
    subscribe(
      result => {
        console.log(result)
        this.getStations(this.page)
      }
    )

    // @ts-ignore
    this.nameInput?.nativeElement.value = ''
    // @ts-ignore
    this.cityIdInput?.nativeElement.value = "0"
  }

  deleteStation(id: number) {
    this.stationService.deleteStation(id)
      .subscribe(
        result => {
          console.log(result)
          this.getStations(this.page)
        }
      )
  }

  loadStationData(station: Station) {
    this.hide = !this.hide;

    // @ts-ignore
    this.idUpdateInput?.nativeElement.value = station.id.toString()
    // @ts-ignore
    this.nameUpdateInput?.nativeElement.value = station.name
    // @ts-ignore
    this.cityIdUpdateInput?.nativeElement.value = station.city.id.toString()

  }

  updateStation(idString: string, name: string) {
    let id = parseInt(idString)
    this.stationService.updateStation(id, name)
      .subscribe(
        result => {
          console.log(result)
          this.getStations(this.page)
          this.hide = true
        }
      )
  }

  filterByCityName(cityName: string) {
    this.stationService.filterByCityName(cityName)
      .subscribe(
        filteredStationDto => {
          this.filteredStations = filteredStationDto.stations
          this.hideFiltered = false
        }
      )
  }

  resetFiltered() {
    this.hideFiltered = true
    // @ts-ignore
    this.cityNameInput?.nativeElement.value = ""
  }

  increasePage() {
    this.page += 1
    this.getStations(this.page)
  }

  decreasePage() {
    if (this.page > 0) {
      this.page -= 1
      this.getStations(this.page)
    }
  }

  setCity() {
    let cityName = this.citySelect?.nativeElement.value

    if (cityName === "Choose City") return

    for (let city of this.cities) {
      if (cityName === city.name) {
        this.selectedCity = city
      }
    }
  }

  prepareAddStop(station: Station) {
    this.selectedStation = station
    this.hideStopAdd = !this.hideStopAdd
  }

  setBus() {
    let busName = this.busSelect?.nativeElement.value
    if (busName === "Choose Bus") return

    for (let bus of this.buses) {
      if (busName === bus.modelName) {
        this.selectedBus = bus
      }
    }
  }

  addStop(stopTime: string) {
    // @ts-ignore
    this.stationService.addStop(this.selectedBus.id, this.selectedStation.id, stopTime)
      .subscribe(
        r => {
          console.log(r)
          //@ts-ignore
          this.stopTimeInput?.nativeElement.value = ""
          //@ts-ignore
          this.busSelect?.nativeElement.value = "Choose Bus"
          this.hideStopAdd = true
        }
      )
  }

  getStopsForStation() {
      // @ts-ignore
    this.stationService.getStopsForStation(this.stopsStation.id)
        .subscribe(
          dto => {
            console.log(dto)
            this.stops = dto.stops
          }
        )
  }

  showStopsForStation(station: Station) {
    this.stopsStation = station
    if (this.hideStopView) {
      this.hideStopView = !this.hideStopView
      this.getStopsForStation()
    }
    else {
      this.hideStopView = !this.hideStopView
    }
  }

  deleteStop(stop: Stop) {
    this.stationService.deleteStop(stop.bus.id, stop.busStation.id)
      .subscribe(
        r => {
          console.log(r)
          // @ts-ignore
          this.getStopsForStation()
        }
      )
  }
}
