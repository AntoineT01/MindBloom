package com.tanguylegoff.templateapp.dao.db;

import com.tanguylegoff.templateapp.dao.db.entities.Something;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DB Access for some things
 */
@Repository
public interface SomethingRepository extends JpaRepository<Something, Long> {

}