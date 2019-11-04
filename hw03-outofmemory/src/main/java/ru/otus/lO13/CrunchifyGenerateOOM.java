package ru.otus.lO13;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrunchifyGenerateOOM {

    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    private static List<Long> allDuration = new ArrayList<>();
    private static List<Integer> count = new ArrayList<>();
    private static List<Long> arrays = new ArrayList<>();
    private static int countElement = 0;

    public static void main(String[] args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();
        try {
            executor.submit(() -> {
                try {
                    try {
                        generateOOM();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    countElement = arrays.size();
                    arrays.clear(); // Очищаем память
                }
            }).get();
        } catch (Throwable e) {
            long time = (System.currentTimeMillis() - beginTime) / 1000;
            long countOperationInMin = countElement / (time / 60);
            System.out.println("-----------------------------------------------------------------------------------------------------------\n"
                    + "Общее время на сборку (" + getSum(allDuration) + " ms) " + " Количество сборок= " + count.size() + "\n" +
                    "time (" + time + " s) " + "\n" +
                    "Количество добавленных элементов: " + countElement + "\n" +
                    "Количество операций в минуту: " + countOperationInMin + "\n" +
                    "-----------------------------------------------------------------------------------------------------------");
        }
        executor.shutdownNow();
    }

    public static void generateOOM() throws OutOfMemoryError, InterruptedException {
        while (true) {
            arrays.add(Long.MAX_VALUE);
            Thread.sleep(1);
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (int i = 0; i < gcbeans.size(); i++) {
            System.out.println("GC name:" + gcbeans.get(i).getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbeans.get(i);
            NotificationListener listener = (notification, handback) -> {

                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                    allDuration.add(duration);
                    count.add(1);
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    public static Long getSum(List<Long> longs) {
        long sum = 0;
        for (int i = 0; i < longs.size(); i++) {
            sum = sum + longs.get(i);
        }
        return sum;
    }

}
