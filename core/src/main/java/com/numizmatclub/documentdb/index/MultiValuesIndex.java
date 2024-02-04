package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.RecordId;
import com.numizmatclub.documentdb.storage.StorageCollection;
import com.numizmatclub.documentdb.storage.StorageDocument;
import com.numizmatclub.documentdb.storage.StorageEngine;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.bson.BsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MultiValuesIndex implements Index {

    private static final Logger log = LoggerFactory.getLogger(MultiValuesIndex.class);

    private final String name;
    private final StorageEngine storageEngine;
    private final String collectionName;
    private final IndexEngine indexEngine;
    private final IndexDefinition indexDefinition;

    public MultiValuesIndex(StorageEngine storageEngine,
                            IndexMetadata indexMetadata,
                            IndexEngine indexEngine) {
        this.name = indexMetadata.getName();
        this.collectionName = indexMetadata.getCollection();
        this.storageEngine = storageEngine;
        this.indexEngine = indexEngine;
        this.indexDefinition = indexMetadata.getIndexDefinition();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<RecordId> get(IndexKey key) {
        return (Collection<RecordId>) indexEngine.get(key);
    }

    @Override
    public Stream<Map.Entry<IndexKey, Object>> stream() {
        return indexEngine.stream();
    }

    @Override
    public Index put(IndexKey key, RecordId value) {
        Collection<RecordId> records = get(key);
        if (records == null) {
            records = new ArrayList<>();
        }

        records.add(value);

        indexEngine.put(key, records);
        return this;
    }

    @Override
    public boolean remove(IndexKey key) {
        throw new UnsupportedOperationException("Currently remove operation unsupported");
    }

    @Override
    public long rebuild() {
        StorageCollection collection = storageEngine.getCollection(collectionName);
        if (collection == null) {
            throw new InvalidIndexCollectionNameException(
                    "Index " + name + " can not be rebuild for collection " + collectionName
            );
        }

        final int collectionId = collection.getId();
        ResultSet resultSet = storageEngine.read(new RecordId(collectionId, 0));
        List<IndexField> indexFields = indexDefinition.getFields();

        String indexedFields = indexFields.stream()
                .map(IndexField::name)
                .collect(Collectors.joining(","));

        log.info("Index rebuilding for fields: {}", indexedFields);
        long start = System.nanoTime();

        Set<IndexRow> indexRows = new TreeSet<>();

        long numEntries = 0;
        while (resultSet.hasNext()) {
            StorageDocument document = resultSet.next();

            List<? extends Comparable> params = indexFields.stream()
                    .map(field -> {
                        BsonValue bsonValue = document.traverse(indexFields.get(0).name());

                        if (bsonValue == null) {
                            return null;
                        }

                        return document.asObject(bsonValue);
                    })
                    .filter(Objects::nonNull)
                    .toList();

            indexRows.add(new IndexRow(indexFields, params, document.getPosition()));
        }

        for (IndexRow indexRow : indexRows) {
            IndexKey indexKey = indexDefinition.createIndexKey(indexRow.getParams());
            put(indexKey, new RecordId(collectionId, indexRow.getOffset()));
            numEntries++;
        }

        long totalSpentMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        log.info("Index rebuild has been completed. Total handled documents {}, Total spent time: {}ms", numEntries, totalSpentMs);
        return numEntries;
    }

    @Override
    public IndexDefinition getDefinition() {
        return indexDefinition;
    }

    @Override
    public int compareTo(Index o) {
        return o.compareTo(this);
    }
}
