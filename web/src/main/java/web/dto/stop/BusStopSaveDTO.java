package web.dto.stop;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode()
@ToString(callSuper = true)
public class BusStopSaveDTO {
    Long busId;
    Long stationId;
    String stopTime;
}
