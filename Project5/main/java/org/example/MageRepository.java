package org.example;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Optional;



public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository() {
        this.collection = new ArrayList<>();
    }

    public Optional<Mage> find(String name) {
        return collection.stream()
                .filter(mage -> mage.getName().equals(name))
                .findFirst();
    }

    public void delete(String name) {
        Mage mageToRemove = find(name).orElseThrow(() -> new IllegalArgumentException("Mage not found"));
        collection.remove(mageToRemove);
    }

    public void save(Mage mage) {
        if (find(mage.getName()).isPresent()) {
            throw new IllegalArgumentException("Mage with name " + mage.getName() + " already exists");
        }
        collection.add(mage);
    }
}