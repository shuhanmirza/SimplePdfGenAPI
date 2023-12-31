package org.shuhamirza.springbootex.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jpountz.xxhash.XXHashFactory;
import org.shuhamirza.springbootex.util.Utility;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
}
