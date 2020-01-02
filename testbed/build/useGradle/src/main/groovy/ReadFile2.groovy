#!/usr/bin/env groovy
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

new File('CallMethod.groovy').eachLine('UTF-8') { println it }
println '----------'
new File('CallMethod.groovy').withReader('UTF-8') {
    reader -> reader.eachLine { println it }
}