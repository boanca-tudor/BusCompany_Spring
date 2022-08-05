import {City} from "./city";

export class Station {
  id: number = 0
  name: string = ""
  city: City

  constructor(id: number, name: string, city: City) {
    this.id = id
    this.name = name
    this.city = city
  }
}

export class FilteredStation {
  id: number = 0
  name: string = ""

  constructor(id: number, name: string) {
    this.id = id
    this.name = name
  }
}

export class StationDTO {
  stations: Array<Station> = []
}

export class FilteredStationDTO {
  stations: Array<FilteredStation> = []
}
