package com.sv_profi.test.models.mappers;


import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.dto.MovementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovementMapper {

    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    @Mapping(target = "postOrder",ignore = true)
    MovementDto toDto(Movement movement);

    @Mapping(target = "postOrder",ignore = true)
    Movement toEntity(MovementDto movementDto);
}
