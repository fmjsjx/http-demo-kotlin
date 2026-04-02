import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.springframework.boot") version "4.0.5"
    id("io.spring.dependency-management") version "1.1.7"
    val kotlinVersion = "2.3.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "com.github.fmjsjx.demo"
version = "1.0.0-SNAPSHOT"
description = "Game Demo HTTP Server in Kotlin"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

buildscript {
    repositories {
        maven {
            url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

repositories {
    maven {
        url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    }
    mavenCentral()
}

extra["kotlin-coroutines.version"] = "1.10.2"
extra["lettuce.version"] = "7.5.0.RELEASE"
extra["r2dbc-mysql.version"] = "1.4.1"
extra["netty.version"] = "4.2.12.Final"
extra["logback.version"] = "1.5.32"
extra["assertj.version"] = "3.27.7"

dependencies {

    implementation(platform("com.github.fmjsjx:libcommon-bom:4.1.6"))
    implementation(platform("com.github.fmjsjx:libnetty-bom:4.1.5"))
    implementation(platform("com.github.fmjsjx:myboot-bom:4.1.4"))
    implementation(platform("com.github.fmjsjx:bson-model-bom:2.2.3"))
    implementation(platform("com.github.fmjsjx:bson-model3-bom:3.0.0-alpha2"))
    implementation(platform("org.bouncycastle:bc-jdk18on-bom:1.83"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.github.fmjsjx:libcommon-util")
    implementation("com.github.fmjsjx:libcommon-bson-kotlin")
    implementation("com.github.fmjsjx:libcommon-collection")
    implementation("com.github.fmjsjx:libcommon-json-jackson2-kotlin")
    implementation("com.github.fmjsjx:libcommon-json-jackson3-kotlin")
    implementation("com.github.fmjsjx:libcommon-json-jsoniter-kotlin")
    implementation("com.github.fmjsjx:libcommon-json-fastjson2-kotlin")
    implementation("com.github.fmjsjx:libcommon-redis-kotlin")
    implementation("com.github.fmjsjx:libcommon-yaml")
    implementation("com.github.fmjsjx:libcommon-kotlin")
    implementation("com.github.fmjsjx:libnetty-http-client")
    implementation("com.github.fmjsjx:libnetty-http-server")
    implementation("com.github.fmjsjx:myboot-starter-redis")
    implementation("com.github.fmjsjx:myboot-starter-mongodb")
    implementation("com.github.fmjsjx:myboot-starter-r2dbc")
    implementation("org.bouncycastle:bcpkix-jdk18on")
    implementation("org.bouncycastle:bcprov-jdk18on")
    implementation("io.netty:netty-tcnative-boringssl-static::linux-x86_64")
    implementation("io.netty:netty-tcnative-boringssl-static::windows-x86_64")
    implementation("io.netty:netty-transport-native-epoll::linux-x86_64")
    // R2DBC MySQL
    implementation("io.asyncer:r2dbc-mysql")
    // BSON-model & java code generator
    implementation("com.github.fmjsjx:bson-model-core")
    compileOnly("com.github.fmjsjx:bson-model-generator")
    implementation("com.github.fmjsjx:bson-model3-core")
    compileOnly("com.github.fmjsjx:bson-model3-generator")
    compileOnly("org.jruby:jruby:10.0.3.0")
    // prometheus
    implementation("com.github.fmjsjx:libcommon-prometheus-client")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:5.0.1")
    testImplementation("io.mockk:mockk:1.14.9")
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_25
        freeCompilerArgs.addAll("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf(
        "-server",
        "-XX:+UseZGC",
        "-XX:+UseCompactObjectHeaders",
        "-XX:+EnableDynamicAgentLoading",
        "-Xshare:off",
        "--enable-native-access=ALL-UNNAMED",
        "--sun-misc-unsafe-memory-access=allow",
        classpath.find { "mockito-core" in it.name }?.let { "-javaagent:${it.absolutePath}" } ?: "",
    )
}
