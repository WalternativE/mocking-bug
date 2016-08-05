package at.walternative.mockbug.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public interface SimpleService {

    @Async
    ListenableFuture<String> doTask();

}
