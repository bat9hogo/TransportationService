package transportation.drivers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import transportation.drivers.dto.DriverDto;
import transportation.drivers.entity.Driver;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverDto toDto(Driver driver);
    Driver toEntity(DriverDto dto);
    List<DriverDto> toDtoList(List<Driver> drivers);
}
