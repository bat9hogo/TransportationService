package transportation.drivers.entity;

import org.springframework.data.annotation.Id;
import java.util.Date;

public abstract class BaseEntity {

    @Id
    private String id;

    private boolean deleted = false;

    private Date createdAt = new Date();
    private Date updatedAt = new Date();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
