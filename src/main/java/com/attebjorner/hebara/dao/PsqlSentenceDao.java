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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PsqlSentenceDao implements SentenceDao
{
    private final Session session;

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

    public List<Sentence> getByQuery(String queryString, int page, int maxResults)
    {
        return session.createQuery("from Sentence where originalSentence like :query", Sentence.class)
                .setParameter("query", "%" + queryString + "%")
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    public List<Sentence> getByLemma(String lemma, int page, int maxResults)
    {
        return session.createQuery("select distinct s from Word w join w.sentences s where w.lemma = :lemma", Sentence.class)
                .setParameter("lemma", lemma)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByPos(String pos, int page, int maxResults)
    {
        return session.createQuery("select distinct s from Word w join w.sentences s where w.pos = :pos", Sentence.class)
                .setParameter("pos", pos)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByGram(Map<String, String> gram, int page, int maxResults)
    {
        Query<Sentence> query = buildGramQuery(
                new StringBuilder("select distinct s from Word w join w.sentences s where "), gram
        );
        return query.setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByLemmaPos(String lemma, String pos, int page, int maxResults)
    {
        return session.createQuery("select distinct s from Word w join w.sentences s where w.lemma = :lemma and w.pos = :pos", Sentence.class)
                .setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByLemmaGram(String lemma, Map<String, String> gram, int page, int maxResults)
    {
        Query<Sentence> query = buildGramQuery(
                new StringBuilder("select distinct s from Word w join w.sentences s where w.lemma = :lemma and "),
                gram
        );
        return query.setParameter("lemma", lemma)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByPosGram(String pos, Map<String, String> gram, int page, int maxResults)
    {
        Query<Sentence> query = buildGramQuery(
                new StringBuilder("select distinct s from Word w join w.sentences s where w.pos = :pos and "),
                gram
        );
        return query.setParameter("pos", pos)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    @Override
    public List<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram, int page, int maxResults)
    {
        Query<Sentence> query = buildGramQuery(
                new StringBuilder("select distinct s from Word w join w.sentences s where w.lemma = :lemma and w.pos = :pos and "),
                gram
        );
        return query.setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .setFirstResult(page * maxResults)
                .setMaxResults(maxResults)
                .getResultStream()
                .toList();
    }

    private Query<Sentence> buildGramQuery(StringBuilder queryString, Map<String, String> gram)
    {
        String[] properties = new String[gram.size()];
        int i;
        for (i = 0; i < properties.length; ++i)
        {
            properties[i] = "w.gram[:k" + i + "] = :v" + i;
        }
        queryString.append("(").append(String.join(" or ", properties)).append(")");
        Query<Sentence> query = session.createQuery(queryString.toString(), Sentence.class);
        i = 0;
        for (String key : gram.keySet())
        {
            query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
        }
        return query;
    }
}
