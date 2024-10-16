package com.example.hbv501g.Persistence.Repositories;

import com.example.hbv501g.Persistence.Entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumRepository extends JpaRepository<Forum, Long> {
    Forum save(Forum forum);
    Forum setName(Forum forum, String name);
    Forum setDescription(Forum forum, String description);
    Forum setCategory(Forum forum, String category);
    void delete(Forum forum);
    List<Forum> findAll();
    Forum findById(long forum_id);
}