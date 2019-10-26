package ru.otus.lO13;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class CrunchifyGenerateOOM {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();
        CrunchifyGenerateOOM memoryTest = new CrunchifyGenerateOOM();
        memoryTest.generateOOM();
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
    }

    public void generateOOM() throws Exception {
        final List<String> arrays = new ArrayList<>();
        for (long i = 0; i < Long.MAX_VALUE; i++) {
            if (i != 0) {
                arrays.add(arrays.get((int) (i - 1)) + String.valueOf(i) + "a");
            } else {
                arrays.add(i + "a");
            }
            Thread.sleep(30);
        }
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        List<Long> allDuration = new ArrayList<>();
        List<Integer> countYang = new ArrayList<>();
        List<Integer> countOld = new ArrayList<>();
        List<Long> durationOld = new ArrayList<>();
        List<Long> durationYang = new ArrayList<>();
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
                    if (gcName.equals("Copy")) {
                        countYang.add(1);
                        durationYang.add(duration);
                    } else {
                        countOld.add(1);
                        durationOld.add(duration);
                    }
                    allDuration.add(duration);
                    int count = countYang.size() + countOld.size();
                    System.out.println("Общее время на сборку (" + getSum(allDuration) + " ms) " + " Количество сборок= " + count + "\n" +
                            " Время на yang сбоки (" + getSum(durationYang) + " ms) " + " Количество yang сборок= " + countYang.size() + "\n" +
                            " Время на old сбоки (" + getSum(durationOld) + " ms) " + " Количество old сборок= " + countOld.size());
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
