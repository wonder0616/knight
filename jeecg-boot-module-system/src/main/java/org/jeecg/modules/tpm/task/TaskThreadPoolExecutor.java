package org.jeecg.modules.tpm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class TaskThreadPoolExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(TaskThreadPoolExecutor.class);

    private static final int SIZE = 16;

    private static final int MAX_SIZE = 32;

    private static final int MAX_COUNT = Integer.MAX_VALUE;

    private static final int TASK_TIMEOUT = 30;

    private ThreadPoolExecutor tpExecutor;

    public TaskThreadPoolExecutor() {
        this(SIZE, MAX_SIZE, MAX_COUNT, TASK_TIMEOUT);
    }

    public TaskThreadPoolExecutor(int size, int max, int count, int timeout) {
        tpExecutor = new ThreadPoolExecutor(size, max, timeout, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(count), new MyExecutorPolicy());
    }

    public void putTask(Runnable task) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("put task to executor, task is [{}] .", task.getClass());
        }

        try {
            tpExecutor.execute(task);
        } catch (Exception e) {
            LOG.error("putTask Fail: ", e);
        }
    }

    public static class MyExecutorPolicy implements RejectedExecutionHandler {
        /**
         * Creates a {@code DiscardPolicy}.
         */
        MyExecutorPolicy() {
        }

        /**
         * Does nothing, which has the effect of discarding task r.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            LOG.error("rejected execution.", e);
        }
    }
}
