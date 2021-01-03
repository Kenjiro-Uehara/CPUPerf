package perf.cpu;

import perf.cpu.schedule.CPUPerformanceGetScheduler;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CpuPerfMain {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(CpuPerfMain::runMethod);
            thread.start();
            threads.add(thread);
        }

//        while (true) {
//            System.out.println(cpuPerformanceGetter.getCPUPerformance().cpuRate());
//            Thread.sleep(1000);
//        }
    }

    private static void runMethod() {
        long n = 0;
        for (long i = 0; i < 10000000000L; i++) {
            n++;
        }
    }
}