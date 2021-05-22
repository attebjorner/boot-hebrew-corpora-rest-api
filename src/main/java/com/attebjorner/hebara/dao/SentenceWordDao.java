package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public interface SentenceWordDao<T>
{
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Word.class)
            .addAnnotatedClass(Sentence.class)
            .buildSessionFactory();

    public T getById(long id);
}
