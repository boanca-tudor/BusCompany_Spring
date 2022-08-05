package common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

@Entity
@Table(name = "bus_station")
@AttributeOverride(name = "id", column = @Column(name = "station_id"))
public class BusStation extends BaseEntity<Long> {
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "busStation",
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @Getter
    @JsonIgnore
    private Collection<BusStop> busStops;

    public BusStation(){

    }

    public BusStation(String name, City city){
        this.name = name;
        this.city = city;
    }

    public City getCity(){return this.city;}

    public void setCity(City city){this.city = city;}

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof BusStation
                && getName().equals(((BusStation) obj).getName())
                && getCity().getId().equals(((BusStation) obj).getCity().getId());
    }

    @Override
    public String toString(){
        return "BusStation{" +
                "cityId=" + city.getId() +
                ", name=" + name
                + "}" + super.toString();
    }

    public void addStop(Bus bus, String stopTime) {
        var stop = new BusStop(bus, this, stopTime);
        busStops.add(stop);
        bus.getBusStops().add(stop);
    }

    public void removeStop(Bus bus) {
        for (Iterator<BusStop> iterator = busStops.iterator();
             iterator.hasNext(); ) {
            BusStop stop = iterator.next();

            if (stop.getBusStation().getId().equals(this.getId()) && stop.getBus().getId().equals(bus.getId())) {
                iterator.remove();
                stop.getBus().getBusStops().remove(stop);
                stop.setBus(null);
                stop.setBusStation(null);
            }
        }
    }

    public void updateStop(Bus bus, String newStopTime) {
        for (BusStop stop : busStops) {
            if (stop.getBusStation().getId().equals(this.getId()) && stop.getBus().getId().equals(bus.getId())) {
                stop.setStopTime(newStopTime);
                break;
            }
        }
    }
}
