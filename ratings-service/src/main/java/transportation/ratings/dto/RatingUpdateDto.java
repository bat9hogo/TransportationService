package transportation.ratings.dto;

import jakarta.validation.constraints.*;

public class RatingUpdateDto {

    @NotNull(message = "score is required")
    @Min(value = 1, message = "score must be at least 1")
    @Max(value = 5, message = "score must be at most 5")
    private Integer score;

    private String comment;

    public RatingUpdateDto() {}

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
