package org.example;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args)
    {
        MageComparator mageCmp;
        Set<Mage> mages;
        Set<Mage> apprentices1;
        Set<Mage> apprentices2;
        Set<Mage> apprentices3;
        Map<Mage, Integer> apprenticeStatistics;

        if (args.length == 0)
            return;

        int type = Integer.parseInt(args[0]);

        if ( type == 1 )            // Bez sortowania
        {
            mages = new HashSet<Mage>();
            apprentices1 = new HashSet<Mage>();
            apprentices2 = new HashSet<Mage>();
            apprentices3 = new HashSet<Mage>();
            apprenticeStatistics = new HashMap<>();
        }

        else if ( type == 2 )        // Sortowanie naturalne
        {
            mages = new TreeSet<Mage>();
            apprentices1 = new TreeSet<Mage>();
            apprentices2 = new TreeSet<Mage>();
            apprentices3 = new TreeSet<Mage>();
            apprenticeStatistics = new TreeMap<>();
        }

        else if ( type == 3 )       // Sortowanie alternatywne
        {
            mageCmp = new MageComparator();
            mages = new TreeSet<Mage>(mageCmp);
            apprentices1 = new TreeSet<Mage>(mageCmp);
            apprentices2 = new TreeSet<Mage>(mageCmp);
            apprentices3 = new TreeSet<Mage>(mageCmp);
            apprenticeStatistics = new TreeMap<>(mageCmp);
        }

        else
        {
            System.out.println("Niepoprawny tryb sortowania");
            return;
        }

        // przykladowe dane
        apprentices1.add(new Mage("Abraham", 7, 9.99, null));
        apprentices1.add(new Mage("Artur", 4, 8.54, null));
        mages.add(new Mage("Marcin", 10, 9.81, apprentices1));

        mages.add(new Mage("Łgeniusz", 3, 3.14, null));

        apprentices3.add(new Mage("Archibald", 5, 7.1, null));
        apprentices3.add(new Mage("Władzimir", 2, 6.66, null));

        apprentices2.add(new Mage("Moromir", 9, 1.5, apprentices3));
        apprentices2.add(new Mage("Zorzeu", 6, 1.6, null));

        mages.add(new Mage("Wojciech", 12, 2.7, apprentices2));


        mages.add(new Mage("Vtold", 5, 1.618, null));
        mages.add(new Mage("Łgeniusz", 8, 0.07, null));

        // wypisanie danych zgodnie ze struktura
        for (Mage mage : mages)
        {
            System.out.println("-"+mage);
            mage.printApprentices(2);
        }

        // skonstruowanie mapy mag-ilosc podwladnych
        for (Mage mage : mages)
            mage.updateApprenticeStatistics(apprenticeStatistics);

        // wypisanie mapy
        System.out.println();
        for (Map.Entry<Mage, Integer> entry : apprenticeStatistics.entrySet())
            System.out.println(entry.getKey() + " - Number of Apprentices: " + entry.getValue());

    }
}