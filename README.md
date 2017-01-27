# Drop-Code
A Gradle plugin that pushes Gradle project's source to dropbox.

# Usage
Archives are available in maven central. To use it, add the following lines in build.gradle

```gradle

apply plugin: 'me.dlkanth.dropcode'

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'me.dlkanth:drop-code:1.0'
    }
}
```
