package com.sv_profi.test.controllers;

import com.sv_profi.test.models.dto.PostOfficeDto;
import com.sv_profi.test.services.PostOfficeService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/post-office")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOfficeController {
    final PostOfficeService postOfficeService;

    @GetMapping("/{id}")
    public ResponseEntity<PostOfficeDto> findPostOfficeById(@PathVariable Long id) {
        Optional<PostOfficeDto> postOfficeDto = postOfficeService.findPostOfficeById(id);
        return postOfficeDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Post office with id: " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<PostOfficeDto> createPostOffice(@RequestBody PostOfficeDto postOfficeDto) {
        Optional<PostOfficeDto> createdOffice = postOfficeService.savePostOffice(postOfficeDto);
        if (createdOffice.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdOffice.get().getId())
                    .toUri();

            return ResponseEntity.created(location).body(createdOffice.get());
        }
        throw new NoSuchElementException("Post office with id: " + postOfficeDto.getId() + " not found");
    }
}
