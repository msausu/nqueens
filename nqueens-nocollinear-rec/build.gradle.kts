
plugins {
    java
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.apache.commons:commons-math3:3.6.1")
    testImplementation("org.apache.commons:commons-compress:1.9")
    testImplementation("org.apache.commons:commons-csv:1.9.0")
    testImplementation("org.tukaani:xz:1.9")
    testImplementation("org.testng:testng:7.4.0")
}

group = "usu.msa.pos"
version = "1.0.1"
description = "NQueens no Collinear (Recursive)"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useTestNG()
    systemProperty("samples_dir", "${project.rootDir}/src/test/resources/samples/")
}

tasks.register("fatJar", Jar::class.java) {
    archiveClassifier.set("")
    archiveFileName.set("nqueens-nocollinear-rec.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    val mainClass = "App"
    manifest {
      attributes("Main-Class" to mainClass)
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}

tasks {
    "build" {
        dependsOn("fatJar")
    }
}
