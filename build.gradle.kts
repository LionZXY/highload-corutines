import  org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines

group = "ru.lionzxy.technopark"
version = "1.0-SNAPSHOT"

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.10"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }

}

apply {
    plugin("java")
    plugin("kotlin")
}


val kotlin_version: String by extra

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.1")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}