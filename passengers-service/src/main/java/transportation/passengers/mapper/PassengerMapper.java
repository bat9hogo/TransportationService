package transportation.passengers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import transportation.passengers.dto.PassengerRequestDto;
import transportation.passengers.dto.PassengerResponseDto;
import transportation.passengers.dto.PassengerUpdateDto;
import transportation.passengers.entity.Passenger;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Passenger toEntity(PassengerRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(PassengerUpdateDto dto, @MappingTarget Passenger passenger);

    PassengerResponseDto toResponseDto(Passenger passenger);

    List<PassengerResponseDto> toResponseDtoList(List<Passenger> passengers);
}
