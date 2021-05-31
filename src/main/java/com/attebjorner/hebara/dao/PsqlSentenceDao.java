package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PsqlSentenceDao implements SentenceDao
{
    private Session session;

    @Autowired
    public PsqlSentenceDao(EntityManager entityManager)
    {
        session = entityManager.unwrap(Session.class);
    }

    @Override
    public Sentence getById(long id)
    {
        return session.get(Sentence.class, id);
    }

    public Set<Sentence> getByQuery(String queryString)
    {
        return session.createQuery("from Sentence where originalSentence like :query", Sentence.class)
                .setParameter("query", "%" + queryString + "%")
                .getResultStream()
                .collect(Collectors.toSet());
    }

    public Set<Sentence> getByLemma(String lemma)
    {
        return session.createQuery("select sentences from Word where lemma = :lemma", Object.class)
                .setParameter("lemma", lemma)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByPos(String pos)
    {
        return session.createQuery("select sentences from Word where pos = :pos", Object.class)
                .setParameter("pos", pos)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByGram(Map<String, String> gram)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select w.sentences from Word w where "), gram
        );
        return query.getResultStream().map(x -> (Sentence) x).collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaPos(String lemma, String pos)
    {
        return session.createQuery("select sentences from Word where lemma = :lemma and pos = :pos", Object.class)
                .setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaGram(String lemma, Map<String, String> gram)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select w.sentences from Word w where w.lemma = :lemma and "), gram
        );
        return query.setParameter("lemma", lemma)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByPosGram(String pos, Map<String, String> gram)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select w.sentences from Word w where w.pos = :pos and "), gram
        );
        return query.setParameter("pos", pos)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select w.sentences from Word w where w.lemma = :lemma and w.pos = :pos and "), gram
        );
        return query.setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    private Query<Object> buildGramQuery(StringBuilder queryString, Map<String, String> gram)
    {
        String[] properties = new String[gram.size()];
        int i;
        for (i = 0; i < properties.length; ++i)
        {
            properties[i] = "w.gram[:k" + i + "] = :v" + i;
        }
        queryString.append("(").append(String.join(" or ", properties)).append(")");
        Query<Object> query = session.createQuery(queryString.toString(), Object.class);
        i = 0;
        for (String key : gram.keySet())
        {
            query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
        }
        return query;
    }
}
