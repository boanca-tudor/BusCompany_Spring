export class City {
  id: number = 0
  name: string = ""
  population: number = 0

  constructor(id: number, name: string, population: number) {
    this.id = id
    this.name = name
    this.population = population
  }
}

export class CityDTO {
  cities: Array<City> = []
}
