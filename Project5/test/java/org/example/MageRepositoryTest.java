package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class MageRepositoryTest {
    private MageRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new MageRepository();
    }

    @Test
    public void testFindExistingMage() {
        Mage mage = new Mage("Gandalf", 20);
        repository.save(mage);

        Optional<Mage> result = repository.find("Gandalf");

        assertTrue(result.isPresent());
        assertEquals(mage, result.get());
    }

    @Test
    public void testFindNonExistingMage() {
        Optional<Mage> result = repository.find("Gandalf");

        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteExistingMage() {
        Mage mage = new Mage("Gandalf", 20);
        repository.save(mage);

        repository.delete("Gandalf");

        assertFalse(repository.find("Gandalf").isPresent());
    }

    @Test
    public void testDeleteNonExistingMage() {
        assertThrows(IllegalArgumentException.class, () -> repository.delete("Gandalf"));
    }

    @Test
    public void testSaveNewMage() {
        repository.save(new Mage("Gandalf", 20));

        assertTrue(repository.find("Gandalf").isPresent());
    }

    @Test
    public void testSaveExistingMage() {
        Mage mage = new Mage("Gandalf", 20);
        repository.save(mage);

        assertThrows(IllegalArgumentException.class, () -> repository.save(mage));
    }
}
