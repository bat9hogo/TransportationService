package transportation.trips.dto;

import transportation.trips.entity.TripStatus;

public class TripStatusUpdateDto {

    private TripStatus status;

    public TripStatus getStatus() {
        return status;
    }

    public void setStatus(TripStatus status) {
        this.status = status;
    }
}
