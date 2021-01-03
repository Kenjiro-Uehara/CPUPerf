package perf.cpu.agent;

import perf.cpu.CPUPerformance;
import perf.cpu.schedule.CPUPerformanceGetScheduler;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class CPUPerfAgent {
    private static CPUPerformanceGetScheduler scheduler;

    public static void premain(String agentArg) {
        if (scheduler != null) {
            return;
        }

        Properties properties = loadProperty(agentArg);
        long intervalMillis = getLong(properties, "INTERVAL_MILLIS", 1000L);

        scheduler = new CPUPerformanceGetScheduler();
        scheduler.start(CPUPerfAgent::printCpuPerf, intervalMillis);
        Runtime.getRuntime().addShutdownHook(new Thread(CPUPerfAgent::shutdown));
    }

    private static void printCpuPerf(CPUPerformance cpuPerformance) {
        System.out.println(new Date(cpuPerformance.getTimestampMillis()) + ":" + cpuPerformance.cpuRate());
    }

    private static void shutdown() {
        scheduler.stop();
        printCpuPerf(scheduler.getCpuPerformanceGetter().getCPUPerformance());
    }

    private static Properties loadProperty(String filename) {
        Properties properties = new Properties();
        if (filename == null) {
            return properties;
        }

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            properties.load(classLoader.getResourceAsStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return properties;
        }
        return properties;
    }

    private static long getLong(Properties properties, String name, long defaultValue) {
        String value = properties.getProperty(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}