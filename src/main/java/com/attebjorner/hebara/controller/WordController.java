package com.attebjorner.hebara.controller;

import com.attebjorner.hebara.model.Word;
import com.attebjorner.hebara.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("words")
public class WordController
{
    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService)
    {
        this.wordService = wordService;
    }

    @GetMapping
    public Set<Word> getWordsByLemma(@RequestParam(required = false) String lemma)
    {
        if (lemma != null) return wordService.getByLemma(lemma);
        return new HashSet<>();
    }
}
