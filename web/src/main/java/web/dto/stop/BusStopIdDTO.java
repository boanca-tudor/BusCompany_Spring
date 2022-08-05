package web.dto.stop;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusStopIdDTO{
    Long busId;
    Long stationId;
}
