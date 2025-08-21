package transportation.drivers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import transportation.drivers.dto.CarRequestDto;
import transportation.drivers.dto.CarUpdateDto;
import transportation.drivers.dto.CarResponseDto;
import transportation.drivers.entity.Car;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toEntity(CarRequestDto dto);

    CarResponseDto toDto(Car car);

    List<CarResponseDto> toDtoList(List<Car> cars);

    void updateEntityFromDto(CarUpdateDto dto, @MappingTarget Car car);
}
