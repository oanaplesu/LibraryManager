package com.librarymanager.repositories;

import com.librarymanager.entities.Book;
import com.librarymanager.entities.BookCopy;
import com.librarymanager.misc.BookCopyStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String>, JpaSpecificationExecutor<Book> {
    List<BookCopy> findByBookId(Long bookId);
    Optional<BookCopy> findByIdAndBookId(String id, Long bookId);

    @Query(nativeQuery = true,
            value = "SELECT branch_name as branchName, COUNT(DISTINCT bc.id) as nrTotal, COUNT(DISTINCT bc.id) - IFNULL(sum(l.not_returned), 0) as nrAvailable"
            + " FROM book_copy bc left join loan l on l.bookcopy_id = bc.id"
            + " where bc.book_id = ?1"
            + " group by branch_name order by branch_name")
    List<BookCopyStats> getBookCopyStatsForBookId(long bookId);
}
