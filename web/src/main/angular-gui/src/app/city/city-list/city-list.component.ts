import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Bus} from "../../model/bus";
import {BusService} from "../../bus/service/bus.service";
import {City, CityDTO} from "../../model/city";
import {CityService} from "../service/city.service";

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {
  errorMessage: string = ""
  cities: Array<City> = []
  show: boolean = true
  page: number = 0

  @ViewChild('nameInput') nameInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('populationInput') populationInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('idUpdateInput') idUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('nameUpdateInput') nameUpdateInput: ElementRef<HTMLInputElement> | undefined
  @ViewChild('populationUpdateInput') populationUpdateInput: ElementRef<HTMLInputElement> | undefined

  constructor(private cityService: CityService) { }

  ngOnInit(): void {
    this.getCities(this.page)
    console.log(this.cities)
  }

  getCities(page: number) {
    this.cityService.getAll(page)
      .subscribe(
        citiesDto => {
          console.log(citiesDto)
          this.cities = citiesDto.cities
        },
        error => this.errorMessage = <any> error
      )
  }

  addCity(name: string, populationString: string) {
    let population = parseInt(populationString)
    this.cityService.addCity(name, population).
    subscribe(
      result => {
        console.log(result)
        this.getCities(this.page)
      }
    )

    // @ts-ignore
    this.nameInput?.nativeElement.value = ''
    // @ts-ignore
    this.populationInput?.nativeElement.value = "0"
  }

  deleteCity(id: number) {
    this.cityService.deleteCity(id)
      .subscribe(
        result => {
          console.log(result)
          this.getCities(this.page)
        }
      )
  }

  loadCityData(city: City) {
    this.show = !this.show;

    // @ts-ignore
    this.idUpdateInput?.nativeElement.value = city.id.toString()
    // @ts-ignore
    this.nameUpdateInput?.nativeElement.value = city.name
    // @ts-ignore
    this.populationUpdateInput?.nativeElement.value = city.population

  }

  updateCity(idString: string, name: string, populationString: string) {
    let id = parseInt(idString)
    let population = parseInt(populationString)
    let city = new City(id, name, population)
    this.cityService.updateCity(city)
      .subscribe(
        result => {
          console.log(result)
          this.getCities(this.page)
          this.show = true
        }
      )
  }

  increasePage() {
    this.page += 1
    this.getCities(this.page)
  }

  decreasePage() {
    if (this.page > 0) {
      this.page -= 1
      this.getCities(this.page)
    }
  }
}
