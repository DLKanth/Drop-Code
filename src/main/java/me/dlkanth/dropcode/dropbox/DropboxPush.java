package me.dlkanth.dropcode.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by lakshmikanth on 1/27/2017.
 */
public class DropboxPush extends DefaultTask {

    public String accessToken;
    public File archiveFile;

    private DbxClientV2 dbxClient;

    public void setAccessToken (String accessToken) {
        this.accessToken = accessToken;
    }

    public void setArchiveFile (File archiveFile) {
        this.archiveFile = archiveFile;
    }

    @TaskAction
    public void run() {

        if (accessToken == null) {
            throw new GradleException("Dropcode Failed to push code... \n -------- accessToken cannot be null");
        }

        if (archiveFile == null) {
            throw new GradleException("Dropcode Failed to push code... \n -------- source files cannot be null -------");
        }

        try {

            System.out.println("Initializing Dropbox to dropcode...");

            DbxRequestConfig dbxRequest = DbxRequestConfig.newBuilder("Dropcode source drop")
                    .withHttpRequestor(OkHttp3Requestor.INSTANCE)
                    .build();
            dbxClient = new DbxClientV2(dbxRequest, accessToken);

            System.out.println("Dropping code to Dropbox...");

            InputStream in = new FileInputStream(archiveFile);
            FileMetadata metadata = dbxClient.files().uploadBuilder("/" + archiveFile.getName() + ".zip")
                    .uploadAndFinish(in);

            if (metadata != null) {
                System.out.println("Code Dropped to Dropbox!");
            }

        } catch (Exception e) {
            String message = e.getMessage();
            if (message != null && message.contains("conflict") && message.contains("file")) {
                System.out.println("Drop FileName already exists.. Try again with a different name.");
            } else {
                System.err.println("Some error occurred... \n Dropcode Failed");
            }
        }

    }
}
