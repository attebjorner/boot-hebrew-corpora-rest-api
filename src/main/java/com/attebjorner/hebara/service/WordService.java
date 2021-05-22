package com.attebjorner.hebara.service;

import com.attebjorner.hebara.dao.PsqlWordDao;
import com.attebjorner.hebara.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WordService
{
    private final PsqlWordDao psqlWordDAO;

    @Autowired
    public WordService(PsqlWordDao psqlWordDAO)
    {
        this.psqlWordDAO = psqlWordDAO;
    }

    public Set<Word> getByLemma(String lemma)
    {
        return psqlWordDAO.getByLemma(lemma);
    }
}
