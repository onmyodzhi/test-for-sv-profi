package com.sv_profi.test.models.mappers;

import com.sv_profi.test.models.PostOffice;
import com.sv_profi.test.models.dto.PostOfficeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostOfficeMapper {

    PostOfficeMapper INSTANCE = Mappers.getMapper(PostOfficeMapper.class);

    PostOfficeDto toDto(PostOffice postOffice);

    PostOffice toEntity(PostOfficeDto postOfficeDto);
}
