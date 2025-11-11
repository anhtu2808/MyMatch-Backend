package com.mymatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mymatch.entity.WebSocketSession;

@Repository
public interface WebSocketSessionRepository extends JpaRepository<WebSocketSession, Long> {
    void deleteBySocketSessionId(String socketSessionId);

    List<WebSocketSession> findAllByStudentIdIn(List<Long> studentIds);
}
