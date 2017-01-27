package me.dlkanth.dropcode

import me.dlkanth.dropcode.dropbox.DropboxPush
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Zip

/**
 * Created by lakshmikanth on 1/27/2017.
 */
class DropCode implements Plugin<Project> {


    void apply(Project project) {

        project.extensions.create("dropCode", DropCodeExtn)

        project.afterEvaluate {

            if (project.dropCode.accessToken == null) {
                throw new GradleException("Dropcode accessToken cannot be null")
            }

            Zip dropCodeZip = project.tasks.create("dropCodeZipper", Zip)
            dropCodeZip.baseName = (project.dropCode.dropFileName == null) ?
                    "drop-code-" + new Date().format('yyMMdd-hh-mm-ss') : project.dropCode.dropFileName
            dropCodeZip.from(project.rootDir)
            dropCodeZip.destinationDir = project.buildDir
            dropCodeZip.excludes = ['.idea/', 'gradlew', 'gradlew.*', '*/*.iml', '.gradle/', 'build/', '*/build/', '*/*.zip', '*.zip', '*.iml']

            DropboxPush pushTask = project.tasks.create("dropCode", DropboxPush)
            pushTask.dependsOn dropCodeZip
            pushTask.accessToken = project.dropCode.accessToken
            pushTask.archiveFile = dropCodeZip.archivePath

        }

    }
}
