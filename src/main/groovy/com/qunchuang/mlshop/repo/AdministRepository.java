package com.qunchuang.mlshop.repo;


import com.qunchuang.mlshop.model.Administ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministRepository extends JpaRepository<Administ,String> {

    Optional<Administ>  findByUsername(String username);
}
