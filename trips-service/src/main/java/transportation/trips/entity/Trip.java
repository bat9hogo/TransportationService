package transportation.trips.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("trips")
public class Trip extends BaseEntity {

    @PrimaryKey
    private String id;

    @Column("driver_id")
    private String driverId;

    @Column("passenger_id")
    private String passengerId;

    @Column("from_address")
    private String fromAddress;

    @Column("to_address")
    private String toAddress;

    @Column("status")
    private String status;

    @Column("order_time")
    private Instant orderTime;

    @Column("price")
    private BigDecimal price;

    public Trip() {}

    public Trip(
            String id, String driverId, String passengerId, String fromAddress,
            String toAddress, String status, Instant orderTime, BigDecimal price
    ) {
        this.id = id;
        this.driverId = driverId;
        this.passengerId = passengerId;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.status = status;
        this.orderTime = orderTime;
        this.price = price;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(Instant orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
