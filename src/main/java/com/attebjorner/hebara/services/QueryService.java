package com.attebjorner.hebara.services;

import com.attebjorner.hebara.dao.SentenceDao;
import com.attebjorner.hebara.dao.WordDao;
import com.attebjorner.hebara.models.Sentence;
import com.attebjorner.hebara.models.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class QueryService
{
    private final WordDao wordDao;
    private final SentenceDao sentenceDao;

    @Autowired
    public QueryService(WordDao wordDao, SentenceDao sentenceDao)
    {
        this.wordDao = wordDao;
        this.sentenceDao = sentenceDao;
    }

    public Set<Sentence> getByWord(String word)
    {
        Set<Sentence> result = new HashSet<>();
        Set<Word> words = wordDao.getByWord(word);
        for (Word w : words)
        {
            result.addAll(w.getSentences());
        }
        return result;
    }

    public Set<Sentence> getByParameters(Map<String, Object> query)
    {
        Set<Sentence> result = new HashSet<>();
        Set<Word> words = new HashSet<>();
        if (query.containsKey("lemma") && query.containsKey("pos") && query.containsKey("gram"))
        {
            words.addAll(wordDao.getByLemma((String) query.get("lemma")));
            words.retainAll(wordDao.getByPos((String) query.get("pos")));
            words.retainAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
        } else if (query.containsKey("lemma") && query.containsKey("pos") && !query.containsKey("gram"))
        {
            words.addAll(wordDao.getByLemma((String) query.get("lemma")));
            words.retainAll(wordDao.getByPos((String) query.get("pos")));
        } else if (query.containsKey("lemma") && !query.containsKey("pos") && query.containsKey("gram"))
        {
            words.addAll(wordDao.getByLemma((String) query.get("lemma")));
            words.retainAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
        } else if (!query.containsKey("lemma") && query.containsKey("pos") && query.containsKey("gram"))
        {
            words.addAll(wordDao.getByPos((String) query.get("pos")));
            words.retainAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
        } else if (query.containsKey("lemma") && !query.containsKey("pos") && !query.containsKey("gram"))
        {
            words.addAll(wordDao.getByLemma((String) query.get("lemma")));
        } else if (!query.containsKey("lemma") && query.containsKey("pos") && !query.containsKey("gram"))
        {
            words.addAll(wordDao.getByPos((String) query.get("pos")));
        } else if (!query.containsKey("lemma") && !query.containsKey("pos") && query.containsKey("gram"))
        {
            words.addAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
        }
        for (Word w : words)
        {
            result.addAll(w.getSentences());
        }
        return result;
    }
}
