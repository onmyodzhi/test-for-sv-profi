package com.sv_profi.test.repositories;

import com.sv_profi.test.models.Movement;
import com.sv_profi.test.models.PostOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findAllByPostOrder(PostOrder postOrder);
}