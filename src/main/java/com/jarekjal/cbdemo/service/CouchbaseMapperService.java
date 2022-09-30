package com.jarekjal.cbdemo.service;

import com.couchbase.transactions.TransactionGetResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.convert.translation.TranslationService;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CouchbaseMapperService {
    private final MappingCouchbaseConverter converter;
    private final TranslationService translationService;

    public <T> Pair<String, Object> toCouchbaseObject(T entity) {
        CouchbaseDocument document = new CouchbaseDocument();
        this.converter.write(entity, document);
        return Pair.of(document.getId(), document.export());
    }

    public <T> T toEntity(String entityKey, TransactionGetResult couchbaseResult, Class<T> entityClass) {
        return this.toEntity(entityKey, couchbaseResult.contentAsBytes(), entityClass);
    }

    public <T> T toEntity(String entityKey, byte[] content, Class<T> entityClass) {
        CouchbaseDocument document = new CouchbaseDocument(entityKey);
        CouchbaseDocument translatedDocument = (CouchbaseDocument)this.translationService.decode(new String(content, StandardCharsets.UTF_8), document);
        return this.converter.read(entityClass, translatedDocument);
    }
}