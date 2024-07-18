package com.sv_profi.test.repositories;

import com.sv_profi.test.models.PostOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOrderRepository extends JpaRepository<PostOrder, Long> {
}