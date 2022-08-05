import {Bus} from "./bus";

export class Driver {
  id: number
  name: string
  cnp: string

  constructor(id: number, name: string, cnp: string) {
    this.id = id
    this.name = name
    this.cnp = cnp
  }
}

export class DriverDTO {
  drivers: Array<Driver> = []
}
