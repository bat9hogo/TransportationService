package transportation.ratings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import transportation.ratings.dto.CreateRatingRequestDto;
import transportation.ratings.dto.RatingResponseDto;
import transportation.ratings.entity.Rating;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "authorType", source = "authorType")
    Rating toEntity(CreateRatingRequestDto dto);

    @Mapping(target = "authorType", source = "authorType")
    RatingResponseDto toDto(Rating rating);
}
