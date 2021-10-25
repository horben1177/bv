package com.betvictor.task2.repository;

import com.betvictor.task2.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(value = "SELECT * from BV_HISTORY order by creationDate DESC LIMIT 10", nativeQuery = true)
    List<Message> findLast10Messages();
}
