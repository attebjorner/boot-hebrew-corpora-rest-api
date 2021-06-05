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
    public List<SentenceDto> makeSimpleQuery(@RequestBody Map<String, String> query,
                                            @RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer maxResults)
    {
        return queryService.getBySimpleQuery(
                query.get("query"), (page == null) ? 0 : page - 1, (maxResults == null) ? 10 : maxResults
        );
    }

    @GetMapping("complex")
    public List<SentenceDto> makeComplexQuery(@RequestBody TreeMap<String, Object> query,
                                             @RequestParam(required = false) Integer page,
                                             @RequestParam(required = false) Integer maxResults)
    {
        return queryService.getByParameters(
                query, (page == null) ? 0 : page - 1, (maxResults == null) ? 10 : maxResults
        );
    }

    @GetMapping("wordlist/{id}")
    public List<WordDto> getWordlist(@PathVariable long id)
    {
        return queryService.getWordlist(id);
    }
}
