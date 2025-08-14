package transportation.drivers.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "drivers")
public class Driver extends BaseEntity {

    private String driverId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    private List<String> carIds;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<String> getCarIds() { return carIds; }
    public void setCarIds(List<String> carIds) { this.carIds = carIds; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getId() { return super.getId(); }
    public boolean isDeleted() { return super.isDeleted(); }
    public void setDeleted(boolean deleted) { super.setDeleted(deleted); }
    public Date getCreatedAt() { return super.getCreatedAt(); }
    public void setCreatedAt(Date createdAt) { super.setCreatedAt(createdAt); }
    public Date getUpdatedAt() { return super.getUpdatedAt(); }
    public void setUpdatedAt(Date updatedAt) { super.setUpdatedAt(updatedAt); }
}
