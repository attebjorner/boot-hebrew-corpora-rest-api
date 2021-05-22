package com.attebjorner.hebara.controller;

import com.attebjorner.hebara.dto.SentenceDto;
import com.attebjorner.hebara.model.Sentence;
import com.attebjorner.hebara.service.QueryService;
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
    public Set<Sentence> makeSimpleQuery(@RequestParam String query)
    {
        return queryService.getBySimpleQuery(query);
    }

    @GetMapping("complex")
    public Set<Sentence> makeComplexQuery(@RequestBody Map<String, Object> query)
    {
        return queryService.getByParameters(query);
    }

    @GetMapping("simpledto")
    public Set<SentenceDto> makeSimpleDto(@RequestParam String word)
    {
        return queryService.getByWordDto(word);
    }
}
