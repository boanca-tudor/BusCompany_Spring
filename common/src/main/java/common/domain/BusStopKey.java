package common.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusStopKey implements Serializable {
    private Long busId;
    private Long busStationId;
}
