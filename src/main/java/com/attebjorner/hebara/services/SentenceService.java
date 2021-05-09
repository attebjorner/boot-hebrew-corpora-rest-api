package com.attebjorner.hebara.services;

import com.attebjorner.hebara.dao.SentenceDao;
import com.attebjorner.hebara.models.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class SentenceService
{
    private final SentenceDao sentenceDAO;

    @Autowired
    public SentenceService(SentenceDao sentenceDAO)
    {
        this.sentenceDAO = sentenceDAO;
    }

    public Set<Sentence> getByWord(String word)
    {
        return sentenceDAO.getByWord(word);
    }

    public Set<Sentence> getByLemma(String lemma)
    {
        return sentenceDAO.getByLemma(lemma);
    }

    public Set<Sentence> getByGram(Map<String, String> gram)
    {
        return sentenceDAO.getByGram(gram);
    }
}
