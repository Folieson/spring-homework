package com.folies.jpa;

import com.folies.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Integer> {
    List<Writer> findBySecondName(String secondName);
}
