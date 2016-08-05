package at.walternative.mockbug.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class SimpleServiceImpl implements SimpleService {

    @Override
    @Async
    public ListenableFuture<String> doTask() {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return AsyncResult.forValue("String");
    }
}
