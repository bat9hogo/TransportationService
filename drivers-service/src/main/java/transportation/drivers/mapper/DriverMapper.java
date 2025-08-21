package transportation.drivers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import transportation.drivers.dto.DriverRequestDto;
import transportation.drivers.dto.DriverResponseDto;
import transportation.drivers.dto.DriverUpdateDto;
import transportation.drivers.entity.Driver;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    Driver toEntity(DriverRequestDto dto);

    DriverResponseDto toResponseDto(Driver driver);

    List<DriverResponseDto> toResponseDtoList(List<Driver> drivers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(DriverUpdateDto dto, @MappingTarget Driver driver);
}
