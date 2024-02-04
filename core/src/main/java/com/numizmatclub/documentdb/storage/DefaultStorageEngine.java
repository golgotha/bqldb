package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.RecordId;
import com.numizmatclub.documentdb.storage.result.ResultSet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultStorageEngine implements StorageEngine {

    private final String path;
    private final Map<String, StorageCollection> collectionsMap = new HashMap<>();
    private final List<StorageCollection> collections = new ArrayList<>();

    public DefaultStorageEngine(String path) {
        this.path = path;
    }

    @Override
    public void open() throws IOException {
        openCollections();
    }

    @Override
    public void createCollection(String name) {
        synchronized (this) {
            String fileName = name + StorageCollection.COLLECTION_EXTENSION;
            Path collectionPath = Path.of(this.path, fileName);
            try {
                Files.createFile(Path.of(this.path, fileName));
                openCollection(collectionPath);
            } catch (IOException e) {
                throw new CollectionCreationException("Unable to create a collection", e);
            }
        }
    }

    @Override
    public Set<String> getCollectionNames() {
        return collections.stream()
                .map(StorageCollection::getName)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public StorageCollection getCollection(String collectionName) {
        return collectionsMap.get(collectionName);
    }

    @Override
    public RawDocument readOne(RecordId recordId) {
        int collectionId = recordId.getCollectionId();
        StorageCollection collection = collections.get(collectionId);
        return collection.readOne(recordId.getCurrentPosition());
    }

    @Override
    public ResultSet read(RecordId recordId) {
        int collectionId = recordId.getCollectionId();
        StorageCollection collection = collections.get(collectionId);
        return collection.read(recordId.getCurrentPosition());
    }

    private void openCollections() throws IOException {
        Files.list(Path.of(this.path))
                .filter(p -> p.toFile().isFile() && p.getFileName()
                        .toString().endsWith(StorageCollection.COLLECTION_EXTENSION))
                .forEach(this::openCollection);
    }

    private void openCollection(Path path) {
        int pos = collections.size();
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i) == null) {
                pos = i;
                break;
            }
        }

        openCollection(path, pos);
    }

    private void openCollection(Path path, int pos) {
        final File file = path.toFile();
        final String collectionName = extractCollectionName(file);
        StorageCollection collection = CollectionFactory.createCollection(collectionName, pos);
        collection.configure(() -> this.path);

        collectionsMap.put(collectionName, collection);
        collections.add(collection);
    }

    private String extractCollectionName(File file) {
        String collectionFileName = file.getName();
        int index = collectionFileName.indexOf(StorageCollection.COLLECTION_EXTENSION);
        return collectionFileName.substring(0, index);
    }
}
