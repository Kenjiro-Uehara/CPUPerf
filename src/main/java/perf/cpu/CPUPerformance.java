package perf.cpu;

public final class CPUPerformance {
    public static final CPUPerformance ZERO = new CPUPerformance(1, 0, 0, 0, 0, 0);
    private final int processorCount;
    private final long beginCpuTime;
    private final long endCpuTime;
    private final long beginUpTimeMillis;
    private final long endUpTimeMillis;
    private final long timestampMillis;

    public CPUPerformance(int processorCount, long beginCpuTime, long endCpuTime, long beginUpTimeMillis, long endUpTimeMillis, long timestampMillis) {
        this.processorCount = processorCount;
        this.beginCpuTime = beginCpuTime;
        this.endCpuTime = endCpuTime;
        this.beginUpTimeMillis = beginUpTimeMillis;
        this.endUpTimeMillis = endUpTimeMillis;
        this.timestampMillis = timestampMillis;
    }

    /**
     * return processorCount.
     *
     * @return processorCount
     */
    public int getProcessorCount() {
        return processorCount;
    }

    /**
     * return beginCpuTime.
     *
     * @return beginCpuTime
     */
    public long getBeginCpuTime() {
        return beginCpuTime;
    }

    /**
     * return endCpuTime.
     *
     * @return endCpuTime
     */
    public long getEndCpuTime() {
        return endCpuTime;
    }

    /**
     * return cpuTime.
     *
     * @return cpuTime
     */
    public long getCpuTime() {
        return endCpuTime - beginCpuTime;
    }

    /**
     * return timestampMillis.
     *
     * @return timestampMillis
     */
    public long getTimestampMillis() {
        return timestampMillis;
    }

    /**
     * return beginUpTimeMillis.
     *
     * @return beginUpTimeMillis
     */
    public long getBeginUpTimeMillis() {
        return beginUpTimeMillis;
    }

    /**
     * return endUpTimeMillis.
     *
     * @return endUpTimeMillis
     */
    public long getEndUpTimeMillis() {
        return endUpTimeMillis;
    }

    /**
     * 稼働時間をミリ秒粒度で返します。
     * @return 稼働時間(ミリ秒)
     */
    public long uptimeMillis() {
        return endUpTimeMillis - beginUpTimeMillis;
    }

    /**
     * CPU使用率をパーセント表現(0.0% - 100.0%)で返します。
     * @return CPU使用率(0.0% - 100.0%)
     */
    public double cpuRate() {
        // CPUごとの平均CPU使用時間 = CPU時間(ナノ秒) / CPU数
        // CPU使用率(0.0 - 1.0) = 平均CPU使用時間 / (稼働時間(ミリ秒) * 1000000)
        // CPU使用率(0.0% - 100.0%) = CPU使用率(0.0 - 1.0) * 100
        // 上記式を整理して以下の式となる
        return getCpuTime() / (uptimeMillis() * 10000D * processorCount);
    }
}
