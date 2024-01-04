package com.adelekand.speer.repository;

import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CustomNoteRepositoryImpl implements CustomNoteRepository {
    private final MongoTemplate mongoTemplate;

    public CustomNoteRepositoryImpl(MongoTemplate mongoTemplate) {
       this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Note> findByCreatorAndByRegexpName(User userId, String titleRegex) {
        Criteria userCriteria =  new Criteria().orOperator(Criteria.where("creator").is(userId), Criteria.where("sharedWith").is(userId));
        Criteria regexCriteria = Criteria.where("title").regex(titleRegex);
        Criteria criteria = new Criteria().andOperator(userCriteria, regexCriteria);

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Note.class);
    }
}