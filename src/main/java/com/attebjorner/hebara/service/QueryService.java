package com.attebjorner.hebara.service;

import com.attebjorner.hebara.dao.SentenceDao;
import com.attebjorner.hebara.dto.SentenceDto;
import com.attebjorner.hebara.dto.WordDto;
import com.attebjorner.hebara.exception_handling.NoSentencesFoundException;
import com.attebjorner.hebara.model.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class QueryService
{
    private final SentenceDao sentenceDao;
    private static final Map<Set<String>, Function<Object[], List<Sentence>>> COMPLEX_QUERY_METHODS = new HashMap<>();

    @Autowired
    public QueryService(SentenceDao sentenceDao)
    {
        this.sentenceDao = sentenceDao;
        fillComplexQueryMethodsMap();
    }

    public List<SentenceDto> getByParameters(TreeMap<String, Object> query, int page, int maxResults)
    {
        List<Object> values = new ArrayList<>(query.values().stream().toList());
        values.add(page);
        values.add(maxResults);
        List<Sentence> sentences = COMPLEX_QUERY_METHODS.get(query.keySet()).apply(values.toArray());
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream()
                .map(x -> new SentenceDto(x.getId(), x.getOriginalSentence()))
                .toList();
    }

    public List<SentenceDto> getBySimpleQuery(String queryString, int page, int maxResults)
    {
        List<Sentence> sentences = sentenceDao.getByQuery(queryString, page, maxResults);
        if (sentences.isEmpty())
        {
            throw new NoSentencesFoundException("No sentences found");
        }
        return sentences.stream()
                .map(x -> new SentenceDto(x.getId(), x.getOriginalSentence()))
                .toList();
    }

    public List<WordDto> getWordlist(long id)
    {
        Sentence s = sentenceDao.getById(id);
        if (s == null)
        {
            throw new NoSentencesFoundException("Sentence with id " + id + " does not exist");
        }
        return s.getWordlist()
                .stream()
                .map(x -> new WordDto(
                        x.getWord(), x.getLemma(),
                        x.getPos(), x.getGram()
                )).toList();
    }

    private void fillComplexQueryMethodsMap()
    {
        COMPLEX_QUERY_METHODS.putAll(Map.of(
                Set.of("lemma"), x -> sentenceDao.getByLemma((String) x[0], (Integer) x[1], (Integer) x[2]),
                Set.of("pos"), x -> sentenceDao.getByPos((String) x[0], (Integer) x[1], (Integer) x[2]),
                Set.of("gram"), x -> sentenceDao.getByGram((Map<String, String>) x[0], (Integer) x[1], (Integer) x[2]),
                Set.of("lemma", "pos"), x -> sentenceDao.getByLemmaPos(
                        (String) x[0], (String) x[1], (Integer) x[2], (Integer) x[3]
                ),
                Set.of("lemma", "gram"), x -> sentenceDao.getByLemmaGram(
                        (String) x[1], (Map<String, String>) x[0], (Integer) x[2], (Integer) x[3]
                ),
                Set.of("pos", "gram"), x -> sentenceDao.getByPosGram(
                        (String) x[1], (Map<String, String>) x[0], (Integer) x[2], (Integer) x[3]
                ),
                Set.of("lemma", "pos", "gram"), x -> sentenceDao.getByLemmaPosGram(
                        (String) x[1], (String) x[2], (Map<String, String>) x[0], (Integer) x[3], (Integer) x[4]
                )
        ));
    }
}
