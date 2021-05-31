package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;

import java.util.Map;
import java.util.Set;

public interface SentenceDao
{
    Sentence getById(long id);

    Set<Sentence> getByQuery(String queryString, int page);

    Set<Sentence> getByLemma(String lemma, int page);

    Set<Sentence> getByPos(String pos, int page);

    Set<Sentence> getByGram(Map<String, String> gram, int page);

    Set<Sentence> getByLemmaPos(String lemma, String pos, int page);

    Set<Sentence> getByLemmaGram(String lemma, Map<String, String> gram, int page);

    Set<Sentence> getByPosGram(String pos, Map<String, String> gram, int page);

    Set<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram, int page);
}
