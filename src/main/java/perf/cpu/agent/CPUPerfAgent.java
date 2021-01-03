package perf.cpu.agent;

import perf.cpu.CPUPerformance;
import perf.cpu.schedule.CPUPerformanceGetScheduler;

import java.util.Arrays;
import java.util.Date;

public class CPUPerfAgent {
    private static int called;
    public static void premain(String agentArg) {
        CPUPerformanceGetScheduler scheduler = new CPUPerformanceGetScheduler();
        scheduler.start(CPUPerfAgent::printCpuPerf, 1000L);
    }

    private static void printCpuPerf(CPUPerformance cpuPerformance) {
        System.out.println(new Date(cpuPerformance.getTimestampMillis()) + ":" + cpuPerformance.cpuRate());
    }

}
