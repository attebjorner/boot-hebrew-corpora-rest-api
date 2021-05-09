package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.models.Sentence;
import com.attebjorner.hebara.models.Word;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SentenceDAO implements SentenceWordDAO<Sentence>
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
            StringBuilder query = new StringBuilder("from Word where ");
            String[] properties = new String[gram.size()];
            int i = 0;
            for (String key : gram.keySet())
            {
                properties[i++] = "gram['" + key + "'] = '" + gram.get(key) + "'";
//                words.addAll(
//                        session.createQuery("from Word where gram[:k] = :v", Word.class)
//                                .setParameter("k", key)
//                                .setParameter("v", gram.get(key))
//                                .getResultStream()
//                                .collect(Collectors.toSet())
//                );
            }
            query.append(String.join(" and ", properties));
            Set<Word> words = session.createQuery(query.toString(), Word.class)
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
