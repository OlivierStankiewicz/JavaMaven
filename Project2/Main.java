package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TaskResource<Integer> taskResource = new TaskResource<>();
        ResultsContainer<Integer> resultsContainer = new ResultsContainer<>();

        int numThreads = args.length > 0 ? Integer.parseInt(args[0]) : 5;

        for (int i = 0; i < numThreads; i++) {
            CalculationThread<Integer> thread = new CalculationThread<>(taskResource, resultsContainer);
            thread.start();
        }

        while (true) {
            System.out.println("Enter a task (integer) or 'exit' to close the application:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    if (thread instanceof CalculationThread) {
                        thread.interrupt();
                    }
                }
                break;
            }

            try {
                int task = Integer.parseInt(input);
                taskResource.addTask(task);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer or 'exit'.");
            }
        }

        resultsContainer.displayResults();
        System.out.println("Application closed.");
        scanner.close();
    }
}
