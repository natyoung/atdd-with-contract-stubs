plugins {
    kotlin("jvm") version "2.0.21"
    id("io.quarkus") version "3.16.1"
}

group = "com.jago"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val quarkusPlatformGroupId = "io.quarkus.platform"
val quarkusPlatformArtifactId = "quarkus-bom"
val quarkusPlatformVersion = "3.16.1"

dependencies {
    implementation(enforcedPlatform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkus:quarkus-jdbc-h2:3.24.2")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin:3.24.2")
    implementation("com.zaxxer:HikariCP:6.3.0")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
    testImplementation("io.quarkiverse.pact:quarkus-pact-consumer:1.4.2")
    testImplementation("io.quarkiverse.pact:quarkus-pact-provider:1.4.2")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:3.0.0")
    testImplementation("au.com.dius.pact.provider:junit5:4.6.11")
    testImplementation(kotlin("test"))
    testImplementation("io.quarkus:quarkus-junit5-mockito:3.24.2")
    testImplementation("com.h2database:h2:2.3.232")
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    systemProperty("pact_do_not_track", "true")
    systemProperty("pact.verifier.publishResults", "false")
}
