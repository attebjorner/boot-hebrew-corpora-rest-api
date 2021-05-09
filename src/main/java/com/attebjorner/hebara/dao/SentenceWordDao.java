package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.models.Sentence;
import com.attebjorner.hebara.models.Word;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public interface SentenceWordDao<T>
{
    SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Word.class)
            .addAnnotatedClass(Sentence.class)
            .buildSessionFactory();

    public T getById(long id);
}
