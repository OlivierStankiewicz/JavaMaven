package org.example;
import java.util.Optional;


public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        Optional<Mage> optionalMage = repository.find(name);
        return optionalMage.map(mage -> "Name: " + mage.getName() + ", Level: " + mage.getLevel()).orElse("not found");
    }

    public String delete(String name) {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "not found";
        }
    }

    public String save(String name, int level) {
        try {
            repository.save(new Mage(name, level));
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}