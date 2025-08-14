package transportation.passengers.mapper;

import transportation.passengers.dto.PassengerDto;
import transportation.passengers.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerDto toDto(Passenger entity);

    Passenger toEntity(PassengerDto dto);
}
