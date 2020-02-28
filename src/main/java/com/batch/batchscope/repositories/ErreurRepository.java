package com.batch.batchscope.repositories;


import com.batch.batchscope.model.Erreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ErreurRepository extends JpaRepository<Erreur, Long>, CrudRepository<Erreur, Long> {

}
