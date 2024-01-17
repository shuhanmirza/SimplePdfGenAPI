package org.shuhanmirza.simplepdfgenapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Shuhan Mirza
 * @since 17/1/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PdfCleanUpService {

    private static long DELETE_FOLDER_DELAY_MINUTES = 15L;

    private Mono<Boolean> deleteFolder(String folderPath) {

        var folder = new File(folderPath);
        try {
            FileUtils.deleteDirectory(folder);
        } catch (IOException ioException) {
            log.error("Failed to delete {}", folderPath, ioException);
            return Mono.just(Boolean.FALSE);
        }

        log.info("Deleted {}", folder.getAbsolutePath());

        return Mono.just(Boolean.TRUE);
    }

    public void scheduleFolderForDeletion(String folderPath) {
        Schedulers.boundedElastic().schedule(() -> deleteFolder(folderPath).subscribe(), DELETE_FOLDER_DELAY_MINUTES, TimeUnit.MINUTES);
    }

}
