package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PsqlSentenceDao implements SentenceDao, SentenceWordDao<Sentence>
{
    private static Session session;

    static
    {
        session = null;
    }

    @Override
    public Sentence getById(long id)
    {
        Sentence result = null;
        try
        {
            openSession();
            result = session.get(Sentence.class, id);
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Sentence> getByQuery(String queryString)
    {
        session = factory.getCurrentSession();
        Set<Sentence> result = session.createQuery("from Sentence where originalSentence like :query", Sentence.class)
                .setParameter("query", "%" + queryString + "%")
                .getResultStream()
                .collect(Collectors.toSet());
        return result;
    }

    public Set<Sentence> getByWord(String word)
    {
        Set<Sentence> result = new HashSet<>();
        try
        {
            openSession();
            Set<Word> words = session.createQuery("from Word where word = :word", Word.class)
                    .setParameter("word", word)
                    .getResultStream()
                    .collect(Collectors.toSet());
            for (Word w : words)
            {
                result.addAll(w.getSentences());
            }
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Sentence> getByLemma(String lemma)
    {
        Set<Sentence> result = new HashSet<>();
        try
        {
            openSession();
            Set<Word> words = session.createQuery("from Word where lemma = :lemma", Word.class)
                    .setParameter("lemma", lemma)
                    .getResultStream()
                    .collect(Collectors.toSet());
            for (Word w : words)
            {
                result.addAll(w.getSentences());
            }
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Sentence> getByGram(Map<String, String> gram)
    {
        Set<Sentence> result = new HashSet<>();
        try
        {
            openSession();
            StringBuilder queryString = new StringBuilder("from Word where ");
//            String[] properties = new String[gram.size()];
//            for (int i = 0; i < properties.length; ++i)
//            {
//                properties[i] = "gram[:k" + i + "] = :v" + i;
//            }
//            queryString.append(String.join(" and ", properties));
//            Query<Word> query = session.createQuery(queryString.toString(), Word.class);
//            int i = 0;
//            for (String key : gram.keySet())
//            {
//                query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
//            }
//            Set<Word> words = query.getResultStream().collect(Collectors.toSet());
//            for (Word w : words)
//            {
//                result.addAll(w.getSentences());
//            }
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    private void openSession()
    {
        session = factory.getCurrentSession();
        session.beginTransaction();
    }

    private void closeSessionAndFactory()
    {
        if (session != null) session.close();
        factory.close();
    }
}
