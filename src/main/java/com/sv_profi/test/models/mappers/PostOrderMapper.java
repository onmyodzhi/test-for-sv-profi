package com.sv_profi.test.models.mappers;

import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.PostOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostOrderMapper {

    PostOrderMapper INSTANCE = Mappers.getMapper(PostOrderMapper.class);

    @Mapping(target = "displacementHistory", ignore = true)
    PostOrderDto toDto(PostOrder postOrder);

    @Mapping(target = "displacementHistory", ignore = true)
    PostOrder toEntity(PostOrderDto postOrderDto);
}
