package transportation.ratings.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "ratings")
public class Rating {

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

    @Field(type = FieldType.Boolean)
    private boolean deleted = false;

    @Field(type = FieldType.Date)
    private Date createdAt;

    @Field(type = FieldType.Date)
    private Date updatedAt;

    public Rating() {}

    public Rating(String id, String tripId, String driverId, String passengerId,
                  Integer score, String comment, boolean deleted,
                  Date createdAt, Date updatedAt) {
        this.id = id;
        this.tripId = tripId;
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.score = score;
        this.comment = comment;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
