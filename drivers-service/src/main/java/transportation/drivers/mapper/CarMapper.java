package transportation.drivers.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import transportation.drivers.dto.CreateCarRequestDto;
import transportation.drivers.dto.UpdateCarRequestDto;
import transportation.drivers.dto.CarResponseDto;
import transportation.drivers.entity.Car;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toEntity(CreateCarRequestDto dto);

    CarResponseDto toDto(Car car);

    List<CarResponseDto> toDtoList(List<Car> cars);

    void updateEntityFromDto(UpdateCarRequestDto dto, @MappingTarget Car car);
}
