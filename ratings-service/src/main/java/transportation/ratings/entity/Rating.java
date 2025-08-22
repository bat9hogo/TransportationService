package transportation.ratings.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Document(indexName = "ratings")
public class Rating extends BaseEntity{
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String tripId;

    @Field(type = FieldType.Keyword)
    private String driverId;

    @Field(type = FieldType.Keyword)
    private String passengerId;

    @Field(type = FieldType.Integer)
    private Integer score;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Keyword)
    private AuthorType authorType;

    public Rating() {}

    public Rating(
            String id, String tripId, String driverId, String passengerId,
            Integer score, String comment, AuthorType authorType
    ) {
        this.tripId = tripId;
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.score = score;
        this.comment = comment;
        this.authorType = authorType;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getDriverId() {
        return driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPassengerId() {
        return passengerId;
    }
    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public AuthorType getAuthorType() {
        return authorType;
    }
    public void setAuthorType(AuthorType authorType) {
        this.authorType = authorType;
    }
}
