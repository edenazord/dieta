// Root build file
plugins {
    // Version catalogs or convention plugins could go here if needed
}

allprojects {
    tasks.withType<org.gradle.api.tasks.testing.Test> {
        useJUnitPlatform()
    }
}
