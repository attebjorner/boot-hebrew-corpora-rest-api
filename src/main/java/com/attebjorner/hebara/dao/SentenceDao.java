package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Sentence;

import java.util.List;
import java.util.Map;

public interface SentenceDao
{
    Sentence getById(long id);

    List<Sentence> getByQuery(String queryString, int page, int maxResults);

    List<Sentence> getByLemma(String lemma, int page, int maxResults);

    List<Sentence> getByPos(String pos, int page, int maxResults);

    List<Sentence> getByGram(Map<String, String> gram, int page, int maxResults);

    List<Sentence> getByLemmaPos(String lemma, String pos, int page, int maxResults);

    List<Sentence> getByLemmaGram(String lemma, Map<String, String> gram, int page, int maxResults);

    List<Sentence> getByPosGram(String pos, Map<String, String> gram, int page, int maxResults);

    List<Sentence> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram, int page, int maxResults);
}
