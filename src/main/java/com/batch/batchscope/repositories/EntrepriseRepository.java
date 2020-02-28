package com.batch.batchscope.repositories;

import com.batch.batchscope.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>, CrudRepository<Entreprise, Long> {
}
