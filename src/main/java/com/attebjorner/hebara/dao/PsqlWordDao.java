package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Word;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class PsqlWordDao implements WordDao, SentenceWordDao<Word>
{
//    TODO: dao is interface and then impl and in service we use interface
    private static Session session;

    static
    {
        session = null;
    }
//TODO: transactional to service level
    @Override
    @Transactional
    public Word getById(long id)
    {
        Word result;
        session = factory.getCurrentSession();
        result = session.get(Word.class, id);
        return result;
    }

    @Transactional
    public Set<Word> getByWord(String word)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        result = session.createQuery("from Word where word = :word", Word.class)
                .setParameter("word", word)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByLemma(String lemma)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        result = session.createQuery("from Word where lemma = :lemma", Word.class)
                .setParameter("lemma", lemma)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByPos(String pos)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        result = session.createQuery("from Word where pos = :pos", Word.class)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByGram(Map<String, String> gram)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where "), gram
        );
        result = query.getResultStream().collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where lemma = :lemma and pos = :pos and "), gram
        );
        result = query.setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByLemmaPos(String lemma, String pos)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        result = session.createQuery("from Word where lemma = :lemma and pos = :pos", Word.class)
                .setParameter("lemma", lemma)
                .setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByLemmaGram(String lemma, Map<String, String> gram)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where lemma = :lemma and "), gram
        );
        result = query.setParameter("lemma", lemma)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    @Transactional
    public Set<Word> getByPosGram(String pos, Map<String, String> gram)
    {
        Set<Word> result;
        session = factory.getCurrentSession();
        Query<Word> query = buildGramQuery(
                new StringBuilder("from Word where pos = :pos and "), gram
        );
        result = query.setParameter("pos", pos)
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    private Query<Word> buildGramQuery(StringBuilder queryString, Map<String, String> gram)
    {
        String[] properties = new String[gram.size()];
        for (int i = 0; i < properties.length; ++i)
        {
            properties[i] = "gram[:k" + i + "] = :v" + i;
        }
        queryString.append("(").append(String.join(" or ", properties)).append(")");
        Query<Word> query = session.createQuery(queryString.toString(), Word.class);
        int i = 0;
        for (String key : gram.keySet())
        {
            query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
        }
        return query;
    }
}
