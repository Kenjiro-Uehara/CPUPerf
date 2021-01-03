package perf.cpu;

import perf.cpu.schedule.CPUPerformanceGetScheduler;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpuPerfMain {
    public static void main(String[] args) throws InterruptedException {
        CPUPerformanceGetter cpuPerformanceGetter = new CPUPerformanceGetter();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(CpuPerfMain::runMethod);
            thread.start();
            threads.add(thread);
        }

        CPUPerformanceGetScheduler scheduler = new CPUPerformanceGetScheduler();
        scheduler.start(CpuPerfMain::printCpuPerf, 1000L);
        Thread.sleep(1000L * 30);
        scheduler.stop();
//        long[] threadIds = threadMXBean.getAllThreadIds();
//        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);

//        System.out.println(cpuPerformanceGetter.getPreviousCPUPerformance().cpuRate());
//        while (true) {
//            System.out.println(cpuPerformanceGetter.getCPUPerformance().cpuRate());

//            for (int i = 0; i < threadIds.length; i++) {
//                long threadId = threadIds[i];
//                long cpuTime = threadMXBean.getThreadCpuTime(threadId);
//                String name = threadInfos[i] != null ? threadInfos[i].getThreadName() : "null";
//                System.out.println(threadId + ":" + name + ":" + cpuTime);
//            }

//            Thread.sleep(1000);

//            for (Thread thread : threads) {
//                if (thread.isAlive()) {
//                    thread.join();
//                    System.out.println(thread.getName() + "is joined");
//                }
//            }
//        }
    }

    private static void printCpuPerf(CPUPerformance cpuPerformance) {
        System.out.println(new Date(cpuPerformance.getTimestampMillis()) + ":" + cpuPerformance.cpuRate());
    }

    private static long getVmCpuTime(OperatingSystemMXBean osMXBean) {
        if (osMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osMXBean).getProcessCpuTime();
        } else {
            return 0L;
        }
    }

    private static void runMethod() {
        long n = 0;
        for (long i = 0; i < 10000000000L; i++) {
            n++;
        }
    }
}