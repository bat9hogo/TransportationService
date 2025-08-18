package transportation.ratings.dto;

import jakarta.validation.constraints.*;

public class RatingRequestDto {

    @NotBlank(message = "tripId is required")
    private String tripId;

    @NotBlank(message = "driverId is required")
    private String driverId;

    @NotBlank(message = "passengerId is required")
    private String passengerId;

    @NotNull(message = "score is required")
    @Min(value = 1, message = "score must be at least 1")
    @Max(value = 5, message = "score must be at most 5")
    private Integer score;

    private String comment;

    public RatingRequestDto() {}

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
