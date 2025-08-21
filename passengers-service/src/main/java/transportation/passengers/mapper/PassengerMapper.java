package transportation.passengers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import transportation.passengers.dto.CreatePassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.UpdatePassengerRequestDto;
import transportation.passengers.entity.Passenger;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Passenger toEntity(CreatePassengerRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(UpdatePassengerRequestDto dto, @MappingTarget Passenger passenger);

    PassengerResponseDto toResponseDto(Passenger passenger);

    List<PassengerResponseDto> toResponseDtoList(List<Passenger> passengers);
}
