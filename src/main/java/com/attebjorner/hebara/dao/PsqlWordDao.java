package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PsqlWordDao implements WordDao
{
    private Session session;

    @Autowired
    public PsqlWordDao(EntityManager entityManager)
    {
        session = entityManager.unwrap(Session.class);
    }

    @Override
    public Word getById(long id)
    {
        return session.get(Word.class, id);
    }

    @Override
    public Set<Word> getByWord(String word)
    {
        return session.createQuery("from Word where word = :word", Word.class)
                .setParameter("word", word)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByLemma(String lemma)
    {
        return session.createQuery("from Word where lemma = :lemma", Word.class)
                .setParameter("lemma", lemma)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByPos(String pos)
    {
        return session.createQuery("from Word where pos = :pos", Word.class)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByGram(Map<String, String> gram)
    {
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where "), gram
        );
        return query.getResultStream().collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByLemmaPos(String lemma, String pos)
    {
        return session.createQuery("from Word where lemma = :lemma and pos = :pos", Word.class)
                .setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByLemmaGram(String lemma, Map<String, String> gram)
    {
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where lemma = :lemma and "), gram
        );
        return query.setParameter("lemma", lemma)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByPosGram(String pos, Map<String, String> gram)
    {
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where pos = :pos and "), gram
        );
        return query.setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Word> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram)
    {
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where lemma = :lemma and pos = :pos and "), gram
        );
        return query.setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    private Query<Word> buildGramQuery(StringBuilder queryString, Map<String, String> gram)
    {
        String[] properties = new String[gram.size()];
        int i;
        for (i = 0; i < properties.length; ++i)
        {
            properties[i] = "gram[:k" + i + "] = :v" + i;
        }
        queryString.append("(").append(String.join(" or ", properties)).append(")");
        Query<Word> query = session.createQuery(queryString.toString(), Word.class);
        i = 0;
        for (String key : gram.keySet())
        {
            query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
        }
        return query;
    }
}
