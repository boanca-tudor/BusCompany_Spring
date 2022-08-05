package web.dto.stop;

import lombok.*;
import web.dto.BaseDTO;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BusStopUpdateDTO extends BaseDTO<Long> {
    String stopTime;
}