package perf.cpu;

import java.lang.management.*;
import java.util.stream.LongStream;

/**
 * CPUのパフォーマンス情報を取得します。
 */
public class CPUPerformanceGetter {
    private final OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
    private final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

    private final int processorCount;

    private CPUPerformance previousCpuPerformance;

    public CPUPerformanceGetter() {
        this.processorCount = osMXBean.getAvailableProcessors();
        this.previousCpuPerformance = getCpuPerformance(CPUPerformance.ZERO);
    }

    public CPUPerformance getCPUPerformance() {
        previousCpuPerformance = getCpuPerformance(previousCpuPerformance);
        return previousCpuPerformance;
    }

    public CPUPerformance getPreviousCPUPerformance() {
        return previousCpuPerformance;
    }

    private CPUPerformance getCpuPerformance(CPUPerformance previous) {
        return new CPUPerformance(processorCount, previous.getEndCpuTime(), getVmCpuTime(), previous.getEndUpTimeMillis(), getUptime(), System.currentTimeMillis());
    }

    private long getUptime() {
        return runtimeMXBean.getUptime();
    }

    private long getVmCpuTime() {
        if (osMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osMXBean).getProcessCpuTime();
        } else {
            // 直接はCPU時間を取れないので、存在する全スレッドのCPU時間を足して返す
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            return LongStream.of(threadMXBean.getAllThreadIds())
                    .map(threadMXBean::getThreadCpuTime)
                    .filter(n -> n > 0)
                    .sum();
        }
    }
}