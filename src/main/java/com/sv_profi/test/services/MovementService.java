package com.sv_profi.test.services;

import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.PostOrder;
import com.sv_profi.test.models.dto.MovementDto;
import com.sv_profi.test.models.dto.PostOrderDto;
import com.sv_profi.test.models.mappers.MovementMapper;
import com.sv_profi.test.models.mappers.PostOrderMapper;
import com.sv_profi.test.repositories.MovementRepository;
import com.sv_profi.test.repositories.PostOfficeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementService {

    final MovementRepository movementRepository;
    final PostOfficeService postOfficeService;

    public void save(MovementDto movementDto) {
        Movement movement = MovementMapper.INSTANCE.toEntity(movementDto);
        postOfficeService.savePostOffice(movementDto.getPostOffice());
        movement = movementRepository.save(movement);
        MovementMapper.INSTANCE.toDto(movement);
    }

    public List<MovementDto> findAllByPostOrder(PostOrderDto postOrderDto) {
        PostOrder postOrder = PostOrderMapper.INSTANCE.toEntity(postOrderDto);
        return movementRepository.findAllByPostOrder(postOrder)
                .stream()
                .map(MovementMapper.INSTANCE::toDto)
                .toList();
    }

    public void saveAll(List<MovementDto> movementDtos) {
        List<Movement> movementListToSave = movementDtos.stream()
                .map(MovementMapper.INSTANCE::toEntity)
                .toList();
        movementRepository.saveAll(movementListToSave);
    }
}

