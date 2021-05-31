package com.attebjorner.hebara.controller;

import com.attebjorner.hebara.dto.SentenceDto;
import com.attebjorner.hebara.dto.WordDto;
import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.service.QueryService;
import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@RestController
@RequestMapping("query")
public class QueryController
{
    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService)
    {
        this.queryService = queryService;
    }

    @GetMapping("simple")
    public Set<SentenceDto> makeSimpleQuery(@RequestParam String query)
    {
        return queryService.getBySimpleQuery(query);
//        if (res.isEmpty()) throw new Exception();
//        else return res;
    }

    @GetMapping("complex")
    public Set<SentenceDto> makeComplexQuery(@RequestBody TreeMap<String, Object> query)
    {
        return queryService.getByParameters(query);
    }

    @GetMapping("wordlist/{id}")
    public List<WordDto> getWordlist(@PathVariable long id)
    {
        return queryService.getWordlist(id);
    }
}
