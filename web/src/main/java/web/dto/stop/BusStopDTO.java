package web.dto.stop;

import common.domain.Bus;
import common.domain.BusStation;
import lombok.*;
import web.dto.BaseDTO;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusStopDTO extends BaseDTO<Long> {
    Bus bus;
    BusStation busStation;
    String stopTime;
}