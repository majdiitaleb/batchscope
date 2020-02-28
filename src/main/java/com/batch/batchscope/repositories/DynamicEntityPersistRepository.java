package com.batch.batchscope.repositories;

import com.batch.batchscope.model.DynamicEntityPersist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DynamicEntityPersistRepository extends JpaRepository<DynamicEntityPersist, Long>, CrudRepository<DynamicEntityPersist, Long> {
}
