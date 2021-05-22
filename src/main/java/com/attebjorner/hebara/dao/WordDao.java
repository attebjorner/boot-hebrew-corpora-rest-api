package com.attebjorner.hebara.dao;

import com.attebjorner.hebara.model.Word;

import java.util.Map;
import java.util.Set;

public interface WordDao
{
    Word getById(long id);

    Set<Word> getByWord(String word);

    Set<Word> getByLemma(String lemma);

    Set<Word> getByPos(String pos);

    Set<Word> getByGram(Map<String, String> gram);

    Set<Word> getByLemmaPosGram(String lemma, String pos, Map<String, String> gram);

    Set<Word> getByLemmaPos(String lemma, String pos);

    Set<Word> getByLemmaGram(String lemma, Map<String, String> gram);

    Set<Word> getByPosGram(String pos, Map<String, String> gram);
}
