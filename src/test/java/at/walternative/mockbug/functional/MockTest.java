package at.walternative.mockbug.functional;

import at.walternative.mockbug.service.SimpleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.CompletableToListenableFutureAdapter;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockTest {

    @MockBean
    private SimpleService simpleService;

    @Inject
    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        given(
                this.simpleService.doTask()
        ).willReturn(
                new CompletableToListenableFutureAdapter<String>(
                        CompletableFuture.completedFuture("Mocked")
                )
        );
    }

    @Test
    public void thatMockDoesNotBreak() {
        testRestTemplate.getForEntity("/do-something", String.class);

        verify(this.simpleService, times(1)).doTask();
    }
}
