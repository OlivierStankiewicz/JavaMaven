package org.example;

class CalculationThread<T> extends Thread {
    private final TaskResource<T> taskResource;
    private final ResultsContainer<T> resultsContainer;

    private Integer progress = 0;

    public CalculationThread(TaskResource<T> taskResource, ResultsContainer<T> resultsContainer) {
        this.taskResource = taskResource;
        this.resultsContainer = resultsContainer;
    }

    @Override
    public void run() {
        while (true) {
            progress = 0;
            try {
                T task = taskResource.getTask();
                T result = task;

                if (task instanceof Integer) {
                    Integer integerTask = (Integer) task;
                    for(int i=1; i<=integerTask; i++)
                    {
                        progress = i*100 /integerTask;
                        Thread.sleep(50);
                    }
                    result = (T) Integer.valueOf(integerTask * integerTask);
                }

                resultsContainer.addResult(result);
            } catch (InterruptedException e) {
                if (progress > 0) {
                    System.out.println("Task compeleted in " + progress + "%");
                }
                break;
            }
        }
    }
}