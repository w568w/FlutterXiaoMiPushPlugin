group = "top.huic.xiao_mi_push_plugin"
version = "1.0-SNAPSHOT"

buildscript {
    val kotlinVersion = "2.4.0"
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:9.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

val localMavenDir = layout.buildDirectory.dir("local-maven").get().asFile
val aarFile = file("libs/com.xiaomi.push-sdk-7.12.4.aar")
val (aarGroup, aarArtifact, aarVersion) =
    aarFile.nameWithoutExtension.split("-").also {
        require(it.size == 3) { "AAR name must be <group>-<artifact>-<version>.aar" }
    }
val artifactDir =
    localMavenDir
        .resolve(aarGroup.replace('.', '/'))
        .resolve(aarArtifact)
        .resolve(aarVersion)
val artifactName = "$aarArtifact-$aarVersion"

artifactDir.mkdirs()
copy {
    from(aarFile)
    into(artifactDir)
    rename { "$artifactName.aar" }
}
artifactDir.resolve("$artifactName.pom").writeText(
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0">
      <modelVersion>4.0.0</modelVersion>
      <groupId>$aarGroup</groupId>
      <artifactId>$aarArtifact</artifactId>
      <version>$aarVersion</version>
      <packaging>aar</packaging>
    </project>
    """.trimIndent(),
)

rootProject.allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri(localMavenDir) }
    }
}

plugins {
    id("com.android.library")
}

android {
    namespace = "top.huic.xiao_mi_push_plugin"
    compileSdk = 36

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-proguard-rules.txt")
    }

    lint {
        disable += "InvalidPackage"
    }

}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.14.0")
    implementation("$aarGroup:$aarArtifact:$aarVersion")
}
