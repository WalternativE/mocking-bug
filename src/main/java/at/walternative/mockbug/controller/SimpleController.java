package at.walternative.mockbug.controller;

import at.walternative.mockbug.service.SimpleService;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

@RestController
public class SimpleController {

    private SimpleService simpleService;

    @Inject
    SimpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @RequestMapping(path = "/do-something", method = RequestMethod.GET)
    public DeferredResult<String> doRequest() throws ExecutionException, InterruptedException {
        DeferredResult<String> deferredResult = new DeferredResult<>(5000L);
        deferredResult.onTimeout(() -> {
            deferredResult.setErrorResult("Timeout!!!!");
        });

        simpleService.doTask().addCallback(new ListenableFutureCallback<String>() {

            @Override
            public void onFailure(Throwable ex) {
                deferredResult.setErrorResult("Exception: " + ex.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                deferredResult.setResult("Result: " + result);
            }
        });

        return deferredResult;
    }
}
