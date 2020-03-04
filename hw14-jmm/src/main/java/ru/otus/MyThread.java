package ru.otus;

public class MyThread extends Thread {

    private final Monitor monitor;

    public MyThread(String name, Monitor monitor) {
        super(name);
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for (int i = 1; i < 10; i++) {
            print(getName(), i);
        }
        for (int i = 10; i > 0; i--) {
            print(getName(), i);
        }
    }

    private void print(String threadName, int i) {
        synchronized (monitor) {
            while ((threadName.equals("t1") && monitor.flag) ||
                    (threadName.equals("t2") && !monitor.flag)) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(getName() + " " + i);
            if (threadName.equals("t1")) {
                monitor.flag = true;
            } else if (threadName.equals("t2")) {
                monitor.flag = false;
            }
            monitor.notify();
        }

    }
}
