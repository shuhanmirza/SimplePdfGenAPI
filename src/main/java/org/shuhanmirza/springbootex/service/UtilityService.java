package org.shuhanmirza.springbootex.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jpountz.xxhash.XXHashFactory;
import org.apache.commons.io.FileUtils;
import org.shuhanmirza.springbootex.util.Utility;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
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

        try (var hash64 = xxHashFactory.newStreamingHash64(Utility.XX_HASH_SEED)) {
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

    public CompletableFuture<String> createTextFile(String fileContent, String filePath) {
        try (var fileWriter = new FileWriter(filePath)) {
            fileWriter.write(fileContent);
            return CompletableFuture.completedFuture(filePath);
        } catch (IOException e) {
            log.error("Failed to create file with filePath - {}", filePath, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    public CompletableFuture<File> downloadFile(String urlString, String fileName, String path) {
        try {
            var url = new URL(urlString);
            ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

            var file = Files.createFile(Path.of(path, fileName)).toFile();

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            }

            log.info("File downloaded successfully | url - {} | filename - {} | path {}", urlString, fileName, path);

            return CompletableFuture.completedFuture(file);
        } catch (IOException exception) {
            log.error("Failed to download | url - {} | filename - {} | path {}", urlString, fileName, path);
            return CompletableFuture.failedFuture(exception);
        }
    }

    public CompletableFuture<String> readFileToBase64(String filePath) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
            return CompletableFuture.completedFuture(Base64.getEncoder().encodeToString(fileContent));
        } catch (IOException exception) {
            log.error("Failed to read file to base 64 | path {}", filePath, exception);
            return CompletableFuture.failedFuture(exception);
        }
    }

    public Mono<InputStream> readFileToInputStream(String filePath) {
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
            return Mono.just(new ByteArrayInputStream(fileContent));
        } catch (IOException exception) {
            log.error("Failed to read file to input stream | path {}", filePath, exception);
            return Mono.error(exception);
        }
    }

    public CompletableFuture<String> convertInputStreamToBase64(InputStream inputStream) {
        try {
            return CompletableFuture.completedFuture(Base64.getEncoder().encodeToString(inputStream.readAllBytes()));
        } catch (IOException exception) {
            log.error("Failed to convert InputStream to Base64", exception);
            return CompletableFuture.failedFuture(exception);
        }
    }

    @PostConstruct
    private void printTempFolderPath() {
        log.info("TEMP FOLDER PATH {}", Utility.getTempDirectoryPath());
    }
}
