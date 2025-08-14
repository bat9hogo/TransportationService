package transportation.drivers.mapper;

import transportation.drivers.dto.*;
import transportation.drivers.entity.Driver;
import org.mapstruct.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import transportation.drivers.dto.DriverDto;
import transportation.drivers.entity.Driver;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapper.class})
public interface DriverMapper {
    DriverDto toDto(Driver driver);

    @Mapping(target = "cars", source = "cars")
    Driver toEntity(DriverDto dto);

    List<DriverDto> toDtoList(List<Driver> drivers);
}