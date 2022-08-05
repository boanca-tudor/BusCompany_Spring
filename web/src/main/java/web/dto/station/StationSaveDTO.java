package web.dto.station;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StationSaveDTO {
    private Long cityId;
    private String name;
}
