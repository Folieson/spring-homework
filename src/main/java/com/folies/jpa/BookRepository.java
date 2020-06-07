package com.folies.jpa;

import com.folies.entity.Book;
import com.folies.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select max(release_date) from book where author = :writerId", nativeQuery = true)
    Date getLastBookReleaseDateByAuthorId(@Param("writerId") Integer writerId);

    boolean existsByName(String name);

    List<Book> findByReleaseDateIsNull();

    List<Book> findByWriterSecondName(String authorSecondName);

    List<Book> findByWriter(Writer writer);
}
