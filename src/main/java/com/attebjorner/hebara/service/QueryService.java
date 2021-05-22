package com.attebjorner.hebara.service;

import com.attebjorner.hebara.dao.PsqlSentenceDao;
import com.attebjorner.hebara.dao.SentenceDao;
import com.attebjorner.hebara.dao.WordDao;
import com.attebjorner.hebara.dto.SentenceDto;
import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QueryService
{
    private final WordDao wordDao;
    private final SentenceDao sentenceDao;
    private final Map<Set<String>, Function<Object[], Set<Word>>> COMPLEX_QUERY_METHODS;

    @Autowired
    public QueryService(WordDao wordDao, SentenceDao sentenceDao)
    {
        this.wordDao = wordDao;
        this.sentenceDao = sentenceDao;
        COMPLEX_QUERY_METHODS = new HashMap<>();
        COMPLEX_QUERY_METHODS.put(Set.of("lemma"), x -> wordDao.getByLemma((String) x[0]));
        COMPLEX_QUERY_METHODS.put(Set.of("pos"), x -> wordDao.getByPos((String) x[0]));
        COMPLEX_QUERY_METHODS.put(Set.of("gram"), x -> wordDao.getByGram((Map<String, String>) x[0]));
        COMPLEX_QUERY_METHODS.put(Set.of("lemma", "pos"), x -> wordDao.getByLemmaPos((String) x[0], (String) x[1]));
        COMPLEX_QUERY_METHODS.put(Set.of("lemma", "gram"), x -> wordDao.getByLemmaGram((String) x[0], (Map<String, String>) x[1]));
        COMPLEX_QUERY_METHODS.put(Set.of("pos", "gram"), x -> wordDao.getByPosGram((String) x[0], (Map<String, String>) x[1]));
        COMPLEX_QUERY_METHODS.put(Set.of("lemma", "pos", "gram"), x -> wordDao.getByLemmaPosGram((String) x[0], (String) x[1], (Map<String, String>) x[2]));
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

    public Set<Sentence> getBySimpleQuery(String queryString)
    {
        return sentenceDao.getByQuery(queryString);
    }

    public Set<Sentence> getByParameters(Map<String, Object> query)
    {
        Set<Sentence> result = new HashSet<>();
        Set<Word> words = new HashSet<>(COMPLEX_QUERY_METHODS.get(query.keySet()).apply(query.values().toArray()));
        for (Word w : words)
        {
            result.addAll(w.getSentences());
        }
        return result;
    }

//    public Set<Sentence> getByParameters(Map<String, Object> query)
//    {
//        Set<Sentence> result = new HashSet<>();
//        Set<Word> words = new HashSet<>();
//        if (query.containsKey("lemma") && query.containsKey("pos") && query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByLemmaPosGram(
//                    (String) query.get("lemma"), (String) query.get("pos"),
//                    (Map<String, String>) query.get("gram")
//            ));
//        } else if (query.size() == 2)
//        {
//            if (query.containsKey("lemma") && query.containsKey("pos"))
//            {
//                words.addAll(wordDao.getByLemmaPos(
//                        (String) query.get("lemma"), (String) query.get("pos")
//                ));
//            } else if (query.containsKey("lemma") && query.containsKey("gram"))
//            {
//                words.addAll(wordDao.getByLemmaGram(
//                        (String) query.get("lemma"), (Map<String, String>) query.get("gram")
//                ));
//            } else if (query.containsKey("pos") && query.containsKey("gram"))
//            {
//                words.addAll(wordDao.getByPosGram(
//                        (String) query.get("pos"), (Map<String, String>) query.get("gram")
//                ));
//            }
//        } else if (query.size() == 1)
//        {
//            if (query.containsKey("lemma"))
//            {
//                words.addAll(wordDao.getByLemma((String) query.get("lemma")));
//            } else if (query.containsKey("pos"))
//            {
//                words.addAll(wordDao.getByPos((String) query.get("pos")));
//            } else if (query.containsKey("gram"))
//            {
//                words.addAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
//            }
//        }
//        for (Word w : words)
//        {
//            result.addAll(w.getSentences());
//        }
//        return result;
//    }

//    public Set<Sentence> getByParameters(Map<String, Object> query)
//    {
//        Set<Sentence> result = new HashSet<>();
//        Set<Word> words = new HashSet<>();
//        if (query.containsKey("lemma") && query.containsKey("pos") && query.containsKey("gram")
//                && query.get("gram") instanceof Map)
//        {
//            words.addAll(wordDao.getByLemmaPosGram(
//                    (String) query.get("lemma"), (String) query.get("pos"),
//                    (Map<String, String>) query.get("gram")
//            ));
//        } else if (query.containsKey("lemma") && query.containsKey("pos") && !query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByLemmaPos(
//                    (String) query.get("lemma"), (String) query.get("pos")
//            ));
//        } else if (query.containsKey("lemma") && !query.containsKey("pos") && query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByLemmaGram(
//                    (String) query.get("lemma"), (Map<String, String>) query.get("gram")
//            ));
//        } else if (!query.containsKey("lemma") && query.containsKey("pos") && query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByPosGram(
//                    (String) query.get("pos"), (Map<String, String>) query.get("gram")
//            ));
//        } else if (query.containsKey("lemma") && !query.containsKey("pos") && !query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByLemma((String) query.get("lemma")));
//        } else if (!query.containsKey("lemma") && query.containsKey("pos") && !query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByPos((String) query.get("pos")));
//        } else if (!query.containsKey("lemma") && !query.containsKey("pos") && query.containsKey("gram"))
//        {
//            words.addAll(wordDao.getByGram((Map<String, String>) query.get("gram")));
//        }
//        for (Word w : words)
//        {
//            result.addAll(w.getSentences());
//        }
//        return result;
//    }

    public Set<SentenceDto> getByWordDto(String word)
    {
        Set<SentenceDto> result = new HashSet<>();
        Set<Word> words = wordDao.getByWord(word);
        for (Word w : words)
        {
            for (Sentence s : w.getSentences())
            {
                result.add(new SentenceDto(
                        s.getId(), s.getOriginalSentence(), s.getTranslation(), s.allIndexesOf(w),
                        s.getWordList().stream().map(Word::getId).collect(Collectors.toList())
                ));
            }
        }
        return result;
    }
}
