package com.mymatch.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mymatch.entity.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByParticipantsHash(String hash);

    List<Conversation> findAllByParticipants_Id(Long senderId);

    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.id = :studentId")
    List<Conversation> findAllByParticipantId(@Param("studentId") Long studentId);
}
