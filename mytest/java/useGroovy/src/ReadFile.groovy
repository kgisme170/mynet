#!/usr/bin/env groovy
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

Path file = Paths.get("CallMethod.groovy");
Charset charset = Charset.forName("UTF-8");
try {
    BufferedReader reader = Files.newBufferedReader(file, charset)
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }

} catch (IOException e) {
    e.printStackTrace();
}