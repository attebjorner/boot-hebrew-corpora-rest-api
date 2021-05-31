package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;

import java.util.Map;
import java.util.Set;

public interface SentenceDao
{
    Sentence getById(long id);

    Set<Sentence> getByQuery(String queryString);
}
