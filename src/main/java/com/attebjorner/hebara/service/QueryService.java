package com.attebjorner.hebara.service;

import com.attebjorner.hebara.dao.SentenceDao;
import com.attebjorner.hebara.dao.WordDao;
import com.attebjorner.hebara.dto.SentenceDto;
import com.attebjorner.hebara.dto.WordDto;
import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QueryService
{
    private final WordDao wordDao;
    private final SentenceDao sentenceDao;
    private final Map<Set<String>, Function<Object[], Set<Word>>> COMPLEX_QUERY_METHODS = new HashMap<>();

    @Autowired
    public QueryService(WordDao wordDao, SentenceDao sentenceDao)
    {
        this.wordDao = wordDao;
        this.sentenceDao = sentenceDao;
        fillComplexQueryMethodsMap();
    }

    public Set<SentenceDto> getBySimpleQuery(String queryString)
    {
        return sentenceDao.getByQuery(queryString)
                .stream()
                .map(x -> new SentenceDto(x.getId(), x.getOriginalSentence()))
                .collect(Collectors.toSet());
    }

    public Set<SentenceDto> getByParameters(Map<String, Object> query)
    {
        Set<SentenceDto> result = new HashSet<>();
        Set<Word> words = new HashSet<>(
                COMPLEX_QUERY_METHODS.get(query.keySet())
                        .apply(query.values().toArray())
        );
        for (Word w : words)
        {
            result.addAll(w.getSentences()
                    .stream()
                    .map(x -> new SentenceDto(x.getId(), x.getOriginalSentence()))
                    .collect(Collectors.toSet())
            );
        }
        return result;
    }

    public List<WordDto> getWordlist(long id)
    {
        return sentenceDao.getById(id).getWordlist()
                .stream()
                .map(x -> new WordDto(
                        x.getWord(), x.getLemma(),
                        x.getPos(), x.getGram()
                )).toList();
    }

    private void fillComplexQueryMethodsMap()
    {
        COMPLEX_QUERY_METHODS.putAll(Map.of(
                Set.of("lemma"), x -> wordDao.getByLemma((String) x[0]),
                Set.of("pos"), x -> wordDao.getByPos((String) x[0]),
                Set.of("gram"), x -> wordDao.getByGram((Map<String, String>) x[0]),
                Set.of("lemma", "pos"), x -> wordDao.getByLemmaPos((String) x[0], (String) x[1]),
                Set.of("lemma", "gram"), x -> wordDao.getByLemmaGram((String) x[1], (Map<String, String>) x[0]),
                Set.of("pos", "gram"), x -> wordDao.getByPosGram((String) x[1], (Map<String, String>) x[0]),
                Set.of("lemma", "pos", "gram"), x -> wordDao.getByLemmaPosGram(
                        (String) x[1], (String) x[2], (Map<String, String>) x[0]
                )
        ));
    }
}
