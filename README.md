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

## Dropping sources to Dropbox
   Create an app in `Dropbox` and generate access token for the app from `Dropbox AppConsole`. Copy the access token and add it in your `gradle.properties` or just paste it in your `build.gradle`

### Add dropCode{} in your build.gradle

```gradle

dropCode {
    accessToken 'ACCESS_TOKEN_FROM_DROPBOX'
} 
```

### Run dropCode
Run the `dropCode` gradle task from terminal or Gradle Tools Window
```gradle
gradlew dropCode
```

That's all. Your Project Source will be zipped and dropped to Dropbox.


##### Change Source ZipFile name.
By default, DropCode will name the source zip with current date and time. You can change the name of source zip to be dropped in Dropbox. 
Follow the below snippet.

```gradle
dropCode {
    accessToken 'ACCESS_TOKEN_FROM_DROPBOX'
    dropFileName 'sourceFile'
}
```
