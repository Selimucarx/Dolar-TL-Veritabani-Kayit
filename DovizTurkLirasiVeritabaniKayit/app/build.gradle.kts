/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.2/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("org.json:json:20210307")
    implementation("com.google.guava:guava:31.1-jre")
    implementation ("org.apache.httpcomponents:httpclient:4.5.13") 
    implementation ("mysql:mysql-connector-java:8.0.17") 
    implementation ("org.quartz-scheduler:quartz:2.3.2")
}



// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("gunlukdovizkuruservisi.App")
}
