package pl.kul.taskmanager.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.kul.taskmanager.security.dto.TokenDTO;
import pl.kul.taskmanager.security.entity.TokenEntity;

@Mapper
public interface TokenMapper {
    TokenMapper MAPPER = Mappers.getMapper(TokenMapper.class);

    @Mapping(target = "user", ignore = true)
    TokenEntity toEntity(TokenDTO customerDto);

    TokenDTO toDTO(TokenEntity customer);

    @Mapping(source = "email", target = "email")
    TokenDTO toDTO(TokenEntity customer, String email);
}
