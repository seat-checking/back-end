package project.seatsence.src.store.dto.response.admin.basic;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreReservationUnitResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean space;
    private Boolean chair;
}
