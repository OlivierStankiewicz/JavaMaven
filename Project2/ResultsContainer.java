package org.example;

import java.util.LinkedList;

class ResultsContainer<T> {
    private final LinkedList<T> results = new LinkedList<>();

    public synchronized void addResult(T result) {
        results.add(result);
        System.out.print("Completed task ");
        System.out.println(result);
    }

    public synchronized void displayResults() {
        System.out.println("Results:");
        for (T result : results) {
            System.out.println(result);
        }
    }
}