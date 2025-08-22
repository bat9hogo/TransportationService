package transportation.trips.entity;

import org.springframework.data.cassandra.core.mapping.Column;

public abstract class BaseEntity {

    @Column("deleted")
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
