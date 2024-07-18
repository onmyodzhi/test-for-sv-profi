package com.sv_profi.test.services;

import com.sv_profi.test.models.PostOffice;
import com.sv_profi.test.models.dto.PostOfficeDto;
import com.sv_profi.test.models.mappers.PostOfficeMapper;
import com.sv_profi.test.repositories.PostOfficeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOfficeService {

    final PostOfficeRepository postOfficeRepository;

    public Optional<PostOfficeDto> findPostOfficeById(Long postOfficeId) {
        return postOfficeRepository.findById(postOfficeId)
                .map(PostOfficeMapper.INSTANCE::toDto);
    }

    public Optional<PostOfficeDto> savePostOffice(PostOfficeDto postOffice) {
        if (postOffice.getId() == null) {
            PostOffice savedPostOffice = postOfficeRepository.save(PostOfficeMapper.INSTANCE.toEntity(postOffice));
            return Optional.of(PostOfficeMapper.INSTANCE.toDto(savedPostOffice));
        }
        return Optional.empty();
    }
}
