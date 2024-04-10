package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hibernate-example");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nGłówne menu:");
            System.out.println("1. Wyświetlenie wszystkich wież");
            System.out.println("2. Wyświetlenie wież o wysokości większej niż podana");
            System.out.println("3. Dodanie nowej wieży");
            System.out.println("4. Usunięcie wieży (razem ze wszystkimi magami do niej przypisanymi)");
            System.out.println("5. Wybranie wieży poprzez index");
            System.out.println("6. Wyświetlenie wszystkich wież razem z magami");
            System.out.println("7. Wyświetlenie magów o poziomie większym niż podany");
            System.out.println("8. Wyjście");
            System.out.print("Wybierz opcję: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayAllTowers(em);
                    break;
                case 2:
                    displayTowersByHeight(em);
                    break;
                case 3:
                    addTower(em);
                    break;
                case 4:
                    deleteTowerWithMages(em);
                    break;
                case 5:
                    chooseTower(em);
                    break;
                case 6:
                    displayAllTowersAndMages(em);
                    break;
                case 7:
                    displayAllPowerfulMages(em);
                    break;
                case 8:
                    em.getTransaction().commit();
                    em.close();
                    emf.close();
                    System.exit(0);
                default:
                    System.out.println("Nieprawidłowa opcja. Wybierz ponownie.");
            }
        }
    }

    private static void displayAllTowersAndMages(EntityManager em) {
        Query query = em.createQuery("SELECT t FROM Tower t");
        List<Tower> allTowers = query.getResultList();
        System.out.println("Wszystkie wieże w bazie:");
        for (Tower tower : allTowers) {
            System.out.println("ID: " + tower.getId() + " Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
            displayMagesInTower(em, tower);
        }
    }

    private static void displayAllPowerfulMages(EntityManager em) {
        Query query = em.createQuery("SELECT t FROM Tower t");
        List<Tower> allTowers = query.getResultList();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj minimalny poziom maga: ");
        int minLevel = scanner.nextInt();
        for (Tower tower : allTowers) {
            displayPowerfulMagesInTower(em, tower, minLevel);
        }
    }

    private static void displayAllTowers(EntityManager em) {
        Query query = em.createQuery("SELECT t FROM Tower t");
        List<Tower> allTowers = query.getResultList();
        System.out.println("Wszystkie wieże w bazie:");
        for (Tower tower : allTowers) {
            System.out.println("ID: " + tower.getId() + " Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
        }
    }

    private static void displayTowersByHeight(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj minimalną wysokość wieży: ");
        int minHeight = scanner.nextInt();

        Query query = em.createQuery("SELECT t FROM Tower t WHERE t.height > :minHeight");
        query.setParameter("minHeight", minHeight);
        List<Tower> towers = query.getResultList();
        System.out.println("Wieże o wysokości większej niż " + minHeight + ":");
        for (Tower tower : towers) {
            System.out.println("ID: " + tower.getId() + " Nazwa: " + tower.getName() + ", Wysokość: " + tower.getHeight());
        }
    }

    private static void addTower(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj nazwę nowej wieży: ");
        String name = scanner.nextLine();
        System.out.print("Podaj wysokość nowej wieży: ");
        int height = scanner.nextInt();

        Tower tower = new Tower();
        tower.setName(name);
        tower.setHeight(height);

        em.persist(tower);

        System.out.println("Dodano nową wieżę.");
    }

    private static void deleteTowerWithMages(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ID wieży do usunięcia: ");
        Long towerId = scanner.nextLong();

        Tower tower = em.find(Tower.class, towerId);
        if (tower != null) {
            em.remove(tower);
            System.out.println("Wieżę usunięto pomyślnie.");
        } else {
            System.out.println("Nie znaleziono wieży o podanym ID.");
        }
    }

    private static void chooseTower(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ID wieży: ");
        Long towerId = scanner.nextLong();

        Tower tower = em.find(Tower.class, towerId);
        if (tower != null) {
            System.out.println("Wybrano wieżę: " + tower.getName());
            displayTowerMenu(em, tower);
        } else {
            System.out.println("Nie znaleziono wieży o podanym ID.");
        }
    }

    private static void displayTowerMenu(EntityManager em, Tower tower) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu dla wieży: " + tower.getName());
            System.out.println("1. Wyświetlenie wszystkich magów przypisanych do tej wieży");
            System.out.println("2. Wyświetlenie magów z tej wieży o poziomie większym niż podany");
            System.out.println("3. Dodanie maga do tej wieży");
            System.out.println("4. Usunięcie maga z tej wieży");
            System.out.println("5. Wyjście do poprzedniego menu");
            System.out.print("Wybierz opcję: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    displayMagesInTower(em, tower);
                    break;
                case 2:
                    System.out.print("Podaj minimalny poziom maga: ");
                    int minLevel = scanner.nextInt();
                    displayPowerfulMagesInTower(em, tower, minLevel);
                    break;
                case 3:
                    addMageToTower(em, tower);
                    break;
                case 4:
                    deleteMageFromTower(em, tower);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Nieprawidłowa opcja. Wybierz ponownie.");
            }
        }
    }

    private static void displayMagesInTower(EntityManager em, Tower tower) {
        List<Mage> mages = tower.getMages();
        if (mages != null) {
            System.out.println("Magowie przypisani do wieży " + tower.getName() + ":");
            for (Mage mage : mages) {
                System.out.println("ID: " + mage.getId() + " Imie: " + mage.getName() + ", Poziom: " + mage.getLevel());
            }
        } else {
            System.out.println("Nie ma magów przypisanych do wieży " + tower.getName() + ".");
        }
    }


    private static void displayPowerfulMagesInTower(EntityManager em, Tower tower, int minLevel) {
        Query query = em.createQuery("SELECT m FROM Mage m WHERE m.tower = :tower AND m.level > :minLevel");
        query.setParameter("tower", tower);
        query.setParameter("minLevel", minLevel);
        List<Mage> powerfulMages = query.getResultList();

        if (!powerfulMages.isEmpty()) {
            System.out.println("Mocni magowie w wieży " + tower.getName() + " o poziomie większym niż " + minLevel + ":");
            for (Mage mage : powerfulMages) {
                System.out.println("ID: " + mage.getId() + " Imie: " + mage.getName() + ", Poziom: " + mage.getLevel());
            }
        } else {
            System.out.println("Wieża " + tower.getName() + " nie ma przypisanych magów o poziomie większym niż " + minLevel + ".");
        }


    }

    private static void addMageToTower(EntityManager em, Tower tower) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj imię nowego maga: ");
        String name = scanner.nextLine();
        System.out.print("Podaj poziom nowego maga: ");
        int level = scanner.nextInt();

        Mage mage = new Mage();
        mage.setName(name);
        mage.setLevel(level);
        tower.addMage(mage);

        em.persist(mage);

        System.out.println("Dodano nowego maga do wieży " + tower.getName() + ".");

    }


    private static void deleteMageFromTower(EntityManager em, Tower tower) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj ID maga do usunięcia: ");
        List<Mage> mages = tower.getMages();
        Long mageId = scanner.nextLong();

        for (Iterator<Mage> iterator = mages.iterator(); iterator.hasNext(); ) {
            Mage mage = iterator.next();
            if (mage.getId().equals(mageId)) {
                iterator.remove();
                break;
            }
        }

        Mage mage = em.find(Mage.class, mageId);
        if (mage != null) {
            em.remove(mage);
            System.out.println("Mag został usunięty z wieży " + tower.getName() + ".");
        } else {
            System.out.println("Nie znaleziono maga o podanym ID.");
        }
    }
}
