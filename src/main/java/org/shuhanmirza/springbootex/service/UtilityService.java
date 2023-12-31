package org.shuhanmirza.springbootex.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jpountz.xxhash.XXHashFactory;
import org.shuhanmirza.springbootex.util.Utility;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * @author Shuhan Mirza
 * @since 31/12/23
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UtilityService {

    public CompletableFuture<String> getDigestOfString(String string) {
        var xxHashFactory = XXHashFactory.fastestInstance();

        var byteArrayInputStream = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));

        try (var hash64 = xxHashFactory.newStreamingHash64(Utility.getXxHashSeed())) {
            byte[] buf = new byte[8192];
            for (; ; ) {
                int read = byteArrayInputStream.read(buf);
                if (read == -1) {
                    break;
                }
                hash64.update(buf, 0, read);
            }

            return CompletableFuture.completedFuture(Long.toHexString(hash64.getValue()));

        } catch (IOException e) {
            log.error("Failed to get digest of {}", string, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public CompletableFuture<String> createTemporaryDirectory(String prefix) {
        try {
            var path = Files.createTempDirectory(prefix);

            return CompletableFuture.completedFuture(path.toFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to create temp directory with prefix - {}", prefix, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public CompletableFuture<String> downloadFile(String urlString, String fileName, String path) {
        try {
            var url = new URL(urlString);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

            var file = Files.createFile(Path.of(path, fileName)).toFile();

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            }

            log.info("File downloaded successfully | url - {} | filename - {} | path {}", urlString, fileName, path);

            return CompletableFuture.completedFuture(fileName);
        } catch (IOException exception) {
            log.error("Failed to download | url - {} | filename - {} | path {}", urlString, fileName, path);
            return CompletableFuture.failedFuture(exception);
        }
    }

    @PostConstruct
    private void printTempFolderPath() {
        log.info("TEMP FOLDER PATH {}", Utility.getTempDirectoryPath());
    }
}
