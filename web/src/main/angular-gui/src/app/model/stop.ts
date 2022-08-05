import {Station} from "./station";
import {Bus} from "./bus";

export class Stop {
  bus: Bus
  busStation: Station
  stopTime: string = ""

  constructor(bus: Bus, station: Station, time: string) {
    this.stopTime = time
    this.bus = bus
    this.busStation = station
  }
}

export class StopDTO {
  stops: Array<Stop> = []
}
