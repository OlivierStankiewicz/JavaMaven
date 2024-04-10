package org.example;

import java.util.LinkedList;

class TaskResource<T> {
    private final LinkedList<T> queue = new LinkedList<>();

    public synchronized void addTask(T task) {
        queue.add(task);
        notify();
    }

    public synchronized T getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.poll();
    }
}
