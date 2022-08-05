package common.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "city")
@AttributeOverride(name = "id", column = @Column(name = "city_id"))
public class City extends BaseEntity<Long> {
    @Column
    private String name;
    @Column
    private Integer population;

    @OneToMany(mappedBy = "city", fetch = FetchType.EAGER)
    private Collection<BusStation> busStations;

    public City() {

    }

    public City(String name, Integer population) {
        this.name = name;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof City
                && getName().equals(((City) obj).getName())
                && Objects.equals(getPopulation(), ((City) obj).getPopulation());

    }

    @Override
    public String toString(){
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population
                +"}" + super.toString();
    }
}
