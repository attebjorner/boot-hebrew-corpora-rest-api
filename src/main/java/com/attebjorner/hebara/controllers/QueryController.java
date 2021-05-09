package com.attebjorner.hebara.controllers;

import com.attebjorner.hebara.models.Sentence;
import com.attebjorner.hebara.services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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
    public Set<Sentence> makeSimpleQuery(@RequestParam String word)
    {
        return queryService.getByWord(word);
    }

    @GetMapping("complex")
    public Set<Sentence> makeComplexQuery(@RequestBody Map<String, Object> query)
    {
        return queryService.getByParameters(query);
    }
}
