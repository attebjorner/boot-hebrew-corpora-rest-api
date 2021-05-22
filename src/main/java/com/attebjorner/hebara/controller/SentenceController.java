package com.attebjorner.hebara.controller;

import com.attebjorner.hebara.model.LanguageType;
import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("sentences")
public class SentenceController
{
    private final SentenceService sentenceService;

    @Autowired
    public SentenceController(SentenceService sentenceService)
    {
        this.sentenceService = sentenceService;
    }

    @GetMapping
    public Set<Sentence> getSentences(@RequestParam(required = false) String word,
                                      @RequestParam(required = false) String lemma,
                                      @RequestBody(required = false) Map<String, String> gram)
    {
        if (word != null) return sentenceService.getByWord(word);
        if (lemma != null) return sentenceService.getByLemma(lemma);
        if (!gram.isEmpty()) return sentenceService.getByGram(gram);
        return new HashSet<>(Set.of(new Sentence("hui", "hui", LanguageType.HEB)));
    }

//    @RequestMapping("word")
//    public Set<Sentence> getByWord(@RequestParam String word)
//    {
//        return sentenceService.getByWord(word);
//    }
}
