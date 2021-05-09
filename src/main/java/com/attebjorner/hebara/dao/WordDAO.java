package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.models.Sentence;
import com.attebjorner.hebara.models.Word;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordDAO implements SentenceWordDAO<Word>
{
    private static Session session;

    static
    {
        session = null;
    }

    @Override
    public Word getById(long id)
    {
        Word result = null;
        try
        {
            openSession();
            result = session.get(Word.class, id);
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Word> getByLemma(String lemma)
    {
        Set<Word> result = new HashSet<>();
        try
        {
            openSession();
            result = session.createQuery("from Word where lemma = :lemma", Word.class)
                    .setParameter("lemma", lemma)
                    .getResultStream()
                    .collect(Collectors.toSet());
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
