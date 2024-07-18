package com.sv_profi.test.repositories;

import com.sv_profi.test.models.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, Long>{
}