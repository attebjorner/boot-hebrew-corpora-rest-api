package com.attebjorner.hebara.service;

import com.attebjorner.hebara.dao.PsqlSentenceDao;
import com.attebjorner.hebara.model.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class SentenceService
{
    private final PsqlSentenceDao psqlSentenceDAO;

    @Autowired
    public SentenceService(PsqlSentenceDao psqlSentenceDAO)
    {
        this.psqlSentenceDAO = psqlSentenceDAO;
    }

    public Set<Sentence> getByWord(String word)
    {
        return psqlSentenceDAO.getByWord(word);
    }

    public Set<Sentence> getByLemma(String lemma)
    {
        return psqlSentenceDAO.getByLemma(lemma);
    }

    public Set<Sentence> getByGram(Map<String, String> gram)
    {
        return psqlSentenceDAO.getByGram(gram);
    }
}
