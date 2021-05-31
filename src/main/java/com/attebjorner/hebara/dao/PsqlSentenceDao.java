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

    public Set<Sentence> getByQuery(String queryString, int page)
    {
        return session.createQuery("from Sentence where originalSentence like :query", Sentence.class)
                .setParameter("query", "%" + queryString + "%")
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    public Set<Sentence> getByLemma(String lemma, int page)
    {
        return session.createQuery("select distinct sentences from Word where lemma = :lemma", Object.class)
                .setParameter("lemma", lemma)
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByPos(String pos, int page)
    {
        return session.createQuery("select distinct sentences from Word where pos = :pos", Object.class)
                .setParameter("pos", pos)
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByGram(Map<String, String> gram, int page)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select distinct w.sentences from Word w where "), gram
        );
        return query.setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaPos(String lemma, String pos, int page)
    {
        return session.createQuery("select distinct sentences from Word where lemma = :lemma and pos = :pos", Object.class)
                .setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaGram(String lemma, Map<String, String> gram, int page)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select distinct w.sentences from Word w where w.lemma = :lemma and "),
                gram
        );
        return query.setParameter("lemma", lemma)
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByPosGram(String pos, Map<String, String> gram, int page)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select distinct w.sentences from Word w where w.pos = :pos and "),
                gram
        );
        return query.setParameter("pos", pos)
                .setFirstResult(page)
                .setMaxResults(10)
                .getResultStream()
                .map(x -> (Sentence) x)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram, int page)
    {
        Query<Object> query = buildGramQuery(
                new StringBuilder("select distinct w.sentences from Word w where w.lemma = :lemma and w.pos = :pos and "),
                gram
        );
        return query.setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .setFirstResult(page)
                .setMaxResults(10)
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
