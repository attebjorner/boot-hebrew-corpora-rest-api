package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;

import java.util.Map;
import java.util.Set;

public interface SentenceDao
{
    Sentence getById(long id);

    Set<Sentence> getByQuery(String queryString, int page, int maxResults);

    Set<Sentence> getByLemma(String lemma, int page, int maxResults);

    Set<Sentence> getByPos(String pos, int page, int maxResults);

    Set<Sentence> getByGram(Map<String, String> gram, int page, int maxResults);

    Set<Sentence> getByLemmaPos(String lemma, String pos, int page, int maxResults);

    Set<Sentence> getByLemmaGram(String lemma, Map<String, String> gram, int page, int maxResults);

    Set<Sentence> getByPosGram(String pos, Map<String, String> gram, int page, int maxResults);

    Set<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram, int page, int maxResults);
}
