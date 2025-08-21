package transportation.drivers.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cars")
public class Car extends BaseEntity {

    private String carId;
    private String color;
    private String brand;
    private String licensePlate;

    private String driverId;

    public Car() {}

    public Car(String color, String brand, String licensePlate) {
        this.color = color;
        this.brand = brand;
        this.licensePlate = licensePlate;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getDriverId() {
        return driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getId() {
        return super.getId();
    }

    public boolean isDeleted() {
        return super.isDeleted();
    }
    public void setDeleted(boolean deleted) {
        super.setDeleted(deleted);
    }

    public Date getCreatedAt() {
        return super.getCreatedAt();
    }
    public void setCreatedAt(Date createdAt) {
        super.setCreatedAt(createdAt);
    }

    public Date getUpdatedAt() {
        return super.getUpdatedAt();
    }
    public void setUpdatedAt(Date updatedAt) {
        super.setUpdatedAt(updatedAt);
    }
}
