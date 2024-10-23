import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    val kotlinVersion = "2.0.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

group = "com.github.fmjsjx.demo"
version = "1.0.0-SNAPSHOT"
description = "Game Demo HTTP Server in Kotlin"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

@Suppress("UnstableApiUsage")
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven {
        url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    }
    mavenCentral()
}

extra["mongodb.version"] = "5.2.0"
extra["kotlin-coroutines.version"] = "1.9.0"
extra["r2dbc-mysql.version"] = "1.3.0"

dependencies {

    implementation(platform("com.github.fmjsjx:libcommon-bom:3.9.0"))
    implementation(platform("com.github.fmjsjx:libnetty-bom:3.7.2"))
    implementation(platform("com.github.fmjsjx:myboot-bom:3.3.1"))
    implementation(platform("com.github.fmjsjx:bson-model-bom:2.2.0"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.github.fmjsjx:libcommon-util")
    implementation("com.github.fmjsjx:libcommon-bson-kotlin")
    implementation("com.github.fmjsjx:libcommon-collection")
    implementation("com.github.fmjsjx:libcommon-json-jackson2-kotlin")
    implementation("com.github.fmjsjx:libcommon-json-jsoniter-kotlin")
    implementation("com.github.fmjsjx:libcommon-json-fastjson2-kotlin")
    implementation("com.github.fmjsjx:libcommon-redis-kotlin")
    implementation("com.github.fmjsjx:libcommon-yaml")
    implementation("com.github.fmjsjx:myboot-starter-redis") {
        exclude(group = "org.apache.commons", module = "commons-pool2")
    }
    implementation("com.github.fmjsjx:myboot-starter-mongodb")
    implementation(group = "io.netty", name = "netty-tcnative-boringssl-static", classifier = "linux-x86_64")
    implementation(group = "io.netty", name = "netty-tcnative-boringssl-static", classifier = "windows-x86_64")
    implementation(group = "io.netty", name = "netty-transport-native-epoll", classifier = "linux-x86_64")
    val bcJavaLtsVersion = "2.73.6"
    implementation("org.bouncycastle:bcpkix-lts8on:$bcJavaLtsVersion")
    implementation("org.bouncycastle:bcprov-lts8on:$bcJavaLtsVersion")
    implementation("com.github.fmjsjx:libnetty-http-server")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.github.fmjsjx:libcommon-kotlin")
    implementation("com.github.fmjsjx:libnetty-http-client")
    implementation("com.github.fmjsjx:bson-model-core")
    // R2DBC
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.asyncer:r2dbc-mysql")
    // java code generator
    compileOnly("com.github.fmjsjx:bson-model-generator")
    compileOnly("org.jruby:jruby:9.4.8.0")
    // prometheus
    implementation("com.github.fmjsjx:libcommon-prometheus-client")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("io.mockk:mockk:1.13.12")
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
        freeCompilerArgs.addAll("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs = listOf("-XX:+EnableDynamicAgentLoading")
}
