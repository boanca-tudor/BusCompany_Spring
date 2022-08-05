package common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bus_stop")
public class BusStop implements Serializable {

    @EmbeddedId
    private BusStopKey id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("busId")
    private Bus bus;

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId("busStationId")
    private BusStation busStation;

    @Column(name = "stop_time")
    private String stopTime;

    public BusStop() {
    }

    public BusStop(Bus bus, BusStation busStation, String stopTime) {
        this.bus = bus;
        this.busStation = busStation;
        this.stopTime = stopTime;
        this.id = new BusStopKey(bus.getId(), busStation.getId());
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public BusStation getBusStation() {
        return busStation;
    }

    public void setBusStation(BusStation busStation) {
        this.busStation = busStation;
    }
}

