package mono.focusider.domain.file.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.file.dto.info.FileUrlInfo;
import mono.focusider.domain.file.helper.FileHelper;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@EnableSchedulerLock(defaultLockAtMostFor = "PT1M")
@RequiredArgsConstructor
@Transactional
public class FileBatch {
    private final FileHelper fileHelper;

    @Scheduled(cron = "0 0 3 * * *")
    @SchedulerLock(
            name = "check_file_used", // 스케줄러 락 이름 지정. (이름이 동일한 스케줄러일 경우, 락의 대상이 된다.)
            lockAtLeastFor = "PT30S", // 락을 유지하는 시간을 설정한다. (9초로 설정했는데, 스케줄러 주기보다 약간 짧게 지정하는 것이 좋다.)
            lockAtMostFor = "PT30S" // 보통 스케줄러가 잘 동작하여 잘 종료된 경우 잠금을 바로 해제하게 되는데, 스케줄러 오류가 발생하면 잠금이 해제되지 않는다. 이런 경우 잠금을 유지하는 시간을 설정한다.
    )
    public void deleteUnUsedFile() {
        List<FileUrlInfo> files = fileHelper.findFilesByIsUsed();
        fileHelper.deleteS3Files(files);
        fileHelper.deleteFilesInDb();
    }
}
