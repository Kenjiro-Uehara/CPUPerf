package perf.cpu.schedule;

import perf.cpu.CPUPerformance;
import perf.cpu.CPUPerformanceGetter;

import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 定期的にCPUパフォーマンスを取得するタイマー処理を起動します。
 * 指定されたインターバルでCPUPerformanceを取得し、登録されたハンドラを呼び出します。
 * 内部で起動するスレッドはデーモンスレッドであるため、アプリ終了までパフォーマンス取得を続行する場合は停止処理は不要です。
 */
public class CPUPerformanceGetScheduler {
    private ScheduledExecutorService executorService;

    public void start(Consumer<CPUPerformance> handler, long intervalMillis) {
        executorService = Executors.newSingleThreadScheduledExecutor(this::createThread);
        executorService.scheduleAtFixedRate(new TaskExecutor(new CPUPerformanceGetter(), handler),
                                            intervalMillis, intervalMillis, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executorService.shutdown();
    }

    private Thread createThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;
    }

    private static class TaskExecutor implements Runnable {
        private final CPUPerformanceGetter cpuPerformanceGetter;
        private final Consumer<CPUPerformance> handler;

        public TaskExecutor(CPUPerformanceGetter cpuPerformanceGetter, Consumer<CPUPerformance> handler) {
            this.cpuPerformanceGetter = cpuPerformanceGetter;
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.accept(cpuPerformanceGetter.getCPUPerformance());
        }
    }
}