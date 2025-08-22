package transportation.trips.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import transportation.trips.dto.CreateTripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.entity.Trip;
import transportation.trips.entity.TripStatus;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderTime", ignore = true)
    Trip toEntity(CreateTripRequestDto dto);

    @Mapping(source = "status", target = "status", qualifiedByName = "stringToTripStatus")
    TripResponseDto toDto(Trip trip);

    @Named("stringToTripStatus")
    default TripStatus stringToTripStatus(String status) {
        return status != null ? TripStatus.valueOf(status) : null;
    }

    default void updateEntityFromDto(CreateTripRequestDto dto, Trip entity) {
        if (dto.driverId() != null)
            entity.setDriverId(dto.driverId());

        if (dto.passengerId() != null)
            entity.setPassengerId(dto.passengerId());

        if (dto.fromAddress() != null)
            entity.setFromAddress(dto.fromAddress());

        if (dto.toAddress() != null)
            entity.setToAddress(dto.toAddress());

        if (dto.price() != null)
            entity.setPrice(dto.price());
    }
}
