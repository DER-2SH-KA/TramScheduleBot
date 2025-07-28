plugins {
    id("java")
    id("application")
}

group = "ru.der2shka"
version = "1.0.1"
var jackson_v = "2.18.3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass.set("ru.der2shka.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.telegram/telegrambots
    implementation("org.telegram:telegrambots:6.9.7.1")

    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.19.1")

    // Jackson
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    // implementation("com.fasterxml.jackson.core:jackson-databind:${jackson_v}")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    // implementation("com.fasterxml.jackson.core:jackson-core:${jackson_v}")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations
    // implementation("com.fasterxml.jackson.core:jackson-annotations:${jackson_v}")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.der2shka.Main"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().files.joinToString(" ") { it.name }
    }

    // Включаем зависимости в JAR
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveFileName.set("app.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}