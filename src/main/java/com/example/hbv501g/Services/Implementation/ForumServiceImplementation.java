package com.example.hbv501g.Services.Implementation;

import com.example.hbv501g.Persistence.Entities.Forum;
import com.example.hbv501g.Persistence.Repositories.ForumRepository;
import com.example.hbv501g.Services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumServiceImplementation implements ForumService {

    ForumRepository forumRepository;

    @Autowired
    public ForumServiceImplementation(ForumRepository forumRepository){
        this.forumRepository = forumRepository;
    }
    @Override
    public Forum save(Forum forum) {
        return forumRepository.save(forum);
    }

    @Override
    public void delete(Forum forum) {
        forumRepository.delete(forum);
    }

    @Override
    public List<Forum> findAll() {
        return forumRepository.findAll();
    }

    @Override
    public Forum findById(long ID) {
        return forumRepository.findById(ID);
    }

    @Override
    public Forum edit(Forum forum, String name, String description, String category) {

        if (name != "") {
            forum.setName(name);
        }
        if (description != "") {
            forum.setDescription(description);
        }
        if (category != "") {
            forum.setCategory(category);
        }
        return forumRepository.save(forum);
    }
}