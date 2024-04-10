package org.example;
import java.util.Comparator;
public class MageComparator implements Comparator<Mage>{
    @Override public int compare(Mage m1, Mage m2)
    {
        if (m1.getLevel() != m2.getLevel())
        {
           return m1.getLevel() - m2.getLevel();
        }

        int a = m1.getName().compareTo(m2.getName());
        if (a != 0)
        {
            return a;
        }

        if (m1.getPower() != m2.getPower())
        {
            if (m1.getPower() > m2.getPower())
            {
                return 1;
            }

            return -1;
        }

        return 0;
    }
}
