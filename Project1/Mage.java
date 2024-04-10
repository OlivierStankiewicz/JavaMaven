package org.example;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

public class Mage implements Comparable<Mage>{
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power, Set<Mage> apprentices)
    {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = apprentices;
    }

    @Override public int compareTo(Mage m)
    {
        int a = name.compareTo(m.name);
        if (a != 0)
        {
            return a;
        }

        if (level != m.level)
        {
            return level - m.level;
        }

        if (power != m.power)
        {
            if (power > m.power)
            {
                return 1;
            }

            return -1;
        }

        return 0;
    }

    public boolean equals(Mage m)
    {
        if (name == m.getName() && level == m.getLevel() && power == getPower())
            return true;

        return false;
    }

    @Override public int hashCode()
    {
        return Integer.hashCode(name.hashCode() + level + (int)power);
    }

    @Override public String toString()
    {
        return "Mage{name=" + name + ", level=" + level + ", power=" + power + "}";
    }

    public void printApprentices(int level)
    {
        if (apprentices != null)
        {
            for (Mage apprentice : apprentices) {
                String s = "-";
                String text = new String(new char[level]).replace("\0", s);
                System.out.print(text);
                System.out.println(apprentice);
                apprentice.printApprentices(level+1);
            }
        }
    }

    public void updateApprenticeStatistics(Map<Mage, Integer> statistics) {
        if (apprentices != null) {
            for (Mage apprentice : apprentices) {
                apprentice.updateApprenticeStatistics(statistics);
                statistics.put(this, statistics.getOrDefault(this, 0) + statistics.getOrDefault(apprentice, 0) + 1);
            }
        }

        else {
            statistics.put(this, 0);
        }
    }


    public String getName() {
        return name;
    }

    public int getLevel()
    {
        return level;
    }

    public double getPower()
    {
        return power;
    }

    public Set<Mage> getApprentices()
    {
        return apprentices;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setPower(double power)
    {
        this.power = power;
    }

    public void setApprentices(Set<Mage> apprentices)
    {
        this.apprentices = apprentices;
    }

}
