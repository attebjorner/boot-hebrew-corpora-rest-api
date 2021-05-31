package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PsqlSentenceDao implements SentenceDao
{
    private EntityManager entityManager;

    @Autowired
    public PsqlSentenceDao(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public Sentence getById(long id)
    {
        Session session = entityManager.unwrap(Session.class);
//        Sentence result = null;
//        result = session.get(Sentence.class, id);
        return session.get(Sentence.class, id);
    }

    public Set<Sentence> getByQuery(String queryString)
    {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from Sentence where originalSentence like :query", Sentence.class)
                .setParameter("query", "%" + queryString + "%")
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
