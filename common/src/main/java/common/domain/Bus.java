package common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "bus")
@AttributeOverride(name = "id", column = @Column(name = "bus_id"))
public class Bus extends BaseEntity<Long> {
    @Column(name = "model_name")
    private String modelName;

    @OneToMany(mappedBy = "bus",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Getter
    @JsonIgnore
    private Collection<BusStop> busStops;

    @OneToOne(cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", unique = true)
    private Driver driver;

    public Bus() {

    }

    public Bus(String modelName) {
        this.modelName = modelName;
    }

    public Bus(String modelName, Driver driver) {
        this.modelName = modelName;
        this.driver = driver;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Driver getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Bus &&
                getModelName().equals(((Bus) o).getModelName());
    }

    @Override
    public String toString() {
        return "Bus{" +
                "modelName='" + modelName + '\'' +
                ", driver=" + driver +
                '}';
    }
}