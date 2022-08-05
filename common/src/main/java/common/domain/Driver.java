package common.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
@AttributeOverride(name = "id", column = @Column(name = "driver_id"))
public class Driver extends BaseEntity<Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "cnp", unique = true)
    private String cnp;

    public Driver(String name, String cnp) {
        this.name = name;
        this.cnp = cnp;
    }

    public Driver() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", cnp='" + cnp + '\'' +
                '}';
    }
}
