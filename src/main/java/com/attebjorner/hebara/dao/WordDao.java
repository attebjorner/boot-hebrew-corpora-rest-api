package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.models.Word;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordDao implements SentenceWordDao<Word>
{
    private static Session session;

    static
    {
        session = null;
    }

    @Override
    public Word getById(long id)
    {
        Word result;
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

    public Set<Word> getByWord(String word)
    {
        Set<Word> result;
        try
        {
            openSession();
            result = session.createQuery("from Word where word = :word", Word.class)
                    .setParameter("word", word)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Word> getByLemma(String lemma)
    {
        Set<Word> result;
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

    public Set<Word> getByPos(String pos)
    {
        Set<Word> result;
        try
        {
            openSession();
            result = session.createQuery("from Word where pos = :pos", Word.class)
                    .setParameter("pos", pos)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Word> getByGram(Map<String, String> gram)
    {
        Set<Word> result;
        try
        {
            openSession();
            StringBuilder queryString = new StringBuilder("from Word where ");
            String[] properties = new String[gram.size()];
            for (int i = 0; i < properties.length; ++i)
            {
                properties[i] = "gram[:k" + i + "] = :v" + i;
            }
            queryString.append(String.join(" and ", properties));
            Query<Word> query = session.createQuery(queryString.toString(), Word.class);
            int i = 0;
            for (String key : gram.keySet())
            {
                query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
            }
            result = query.getResultStream().collect(Collectors.toSet());
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    public Set<Word> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram)
    {
        Set<Word> result;
        try
        {
            openSession();
            StringBuilder queryString = new StringBuilder("from Word where lemma = :lemma and pos = :pos ");
            String[] properties = new String[gram.size()];
            for (int i = 0; i < properties.length; ++i)
            {
                properties[i] = "gram[:k" + i + "] = :v" + i;
            }
            queryString.append(String.join(" and ", properties));
            Query<Word> query = session.createQuery(queryString.toString(), Word.class);
            int i = 0;
            for (String key : gram.keySet())
            {
                query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
            }
            result = query.setParameter("lemma", lemma)
                    .setParameter("pos", pos)
                    .getResultStream()
                    .collect(Collectors.toSet());
            session.getTransaction().commit();
        } finally
        {
            closeSessionAndFactory();
        }
        return result;
    }

    private Query<Word> buildGramQuery(StringBuilder queryString, Map<String, String> gram)
    {
        String[] properties = new String[gram.size()];
        for (int i = 0; i < properties.length; ++i)
        {
            properties[i] = "gram[:k" + i + "] = :v" + i;
        }
        queryString.append(String.join(" and ", properties));
        Query<Word> query = session.createQuery(queryString.toString(), Word.class);
        int i = 0;
        for (String key : gram.keySet())
        {
            query.setParameter("k" + i, key).setParameter("v" + i++, gram.get(key));
        }
        return query;
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
