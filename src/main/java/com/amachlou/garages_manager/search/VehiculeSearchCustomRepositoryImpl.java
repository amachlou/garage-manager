package com.amachlou.garages_manager.search;

import com.amachlou.garages_manager.model.VehiculeDocument;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class VehiculeSearchCustomRepositoryImpl implements VehiculeSearchCustomRepository {
    
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<VehiculeDocument> fullTextSearch(String term) {

        var QueryTermSearch = NativeQuery.builder()
                                        .withQuery(q -> q
                                                .match(m -> m
                                                        .field("model")
                                                        .query(term)
                                                )
                                        )
                                        .build();

        SearchHits<VehiculeDocument> hits = elasticsearchOperations.search(QueryTermSearch, VehiculeDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeDocument> findAllWithFields() {
        Query query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .withSourceFilter(new FetchSourceFilter(true,
                        new String[] {"model", "type", "brand"}, null
                ))
                .withMaxResults(100)
                .build();

        return elasticsearchOperations
                .search(query, VehiculeDocument.class)
                .stream()
                .map(hit -> hit.getContent())
                .toList();
    }

    @Override
    public List<VehiculeDocument> searchByTerm(String term) {
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .multiMatch(mm -> mm
                                .query(term)
                                .fields("model", "type", "brand")
                        )
                )
                .withSourceFilter(new FetchSourceFilter(
                        true, new String[] {"model", "type", "brand"}, null
                ))
                .withMaxResults(100)
                .build();

        return elasticsearchOperations
                .search(query, VehiculeDocument.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
