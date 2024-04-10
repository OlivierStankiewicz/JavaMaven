package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;

public class MageControllerTest {
    @Test
    public void testFindExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.when(repository.find("Gandalf")).thenReturn(Optional.of(new Mage("Gandalf", 20)));

        assertEquals("Name: Gandalf, Level: 20", controller.find("Gandalf"));
    }

    @Test
    public void testFindNonExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.when(repository.find("Gandalf")).thenReturn(Optional.empty());

        assertEquals("not found", controller.find("Gandalf"));
    }

    @Test
    public void testDeleteExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.doNothing().when(repository).delete("Gandalf");

        assertEquals("done", controller.delete("Gandalf"));
    }

    @Test
    public void testDeleteNonExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.doThrow(new IllegalArgumentException()).when(repository).delete("Gandalf");

        assertEquals("not found", controller.delete("Gandalf"));
    }

    @Test
    public void testSaveNewMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.doNothing().when(repository).save(Mockito.any(Mage.class));

        assertEquals("done", controller.save("Gandalf", 20));
    }

    @Test
    public void testSaveExistingMage() {
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Mockito.doThrow(new IllegalArgumentException()).when(repository).save(Mockito.any(Mage.class));

        assertEquals("bad request", controller.save("Gandalf", 20));
    }
}