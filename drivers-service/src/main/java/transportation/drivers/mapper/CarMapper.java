package transportation.drivers.mapper;

import org.mapstruct.Mapper;
import transportation.drivers.dto.CarDto;
import transportation.drivers.entity.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDto toDto(Car car);
    Car toEntity(CarDto dto);
}
