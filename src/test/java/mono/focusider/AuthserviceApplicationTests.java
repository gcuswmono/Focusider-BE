package mono.focusider;

import mono.focusider.domain.file.batch.FileBatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class AuthserviceApplicationTests {

    @Autowired
    FileBatch fileBatch;

    @Test
    void test() {
        fileBatch.deleteUnUsedFile();
    }
}
