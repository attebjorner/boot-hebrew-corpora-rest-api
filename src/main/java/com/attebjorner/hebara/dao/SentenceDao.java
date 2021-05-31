package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;

import java.util.Map;
import java.util.Set;

public interface SentenceDao
{
    Sentence getById(long id);

    Set<Sentence> getByQuery(String queryString);

    Set<Sentence> getByLemma(String lemma);

    Set<Sentence> getByPos(String pos);

    Set<Sentence> getByGram(Map<String, String> gram);

    Set<Sentence> getByLemmaPos(String lemma, String pos);

    Set<Sentence> getByLemmaGram(String lemma, Map<String, String> gram);

    Set<Sentence> getByPosGram(String pos, Map<String, String> gram);

    Set<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram);
}
