package com.tux.mindbloom.dao.db;

import com.tux.mindbloom.dao.db.entities.Something;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DB Access for some things
 */
@Repository
public interface SomethingRepository extends JpaRepository<Something, Long> {

}