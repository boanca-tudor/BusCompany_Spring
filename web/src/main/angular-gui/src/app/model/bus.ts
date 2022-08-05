import {Driver} from "./driver";

export class Bus {
  id: number = 0
  modelName: string = ""
  driver: Driver

  constructor(busId: number, model: string, driver: Driver) {
    this.id = busId
    this.modelName = model
    this.driver = driver
  }
}

export class BusDTO {
  buses: Array<Bus> = []
}
