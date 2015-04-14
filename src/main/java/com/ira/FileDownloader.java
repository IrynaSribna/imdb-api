package com.ira;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Created by Iryna on 4/13/15.
 */
public class FileDownloader {

    private URL imdbFileUrl;
    private Path targetFilePath;

    public FileDownloader(URL imdbFileUrl, Path targetFilePath) {
        this.imdbFileUrl = imdbFileUrl;
        this.targetFilePath = targetFilePath;
    }

    public void getImdbFile() throws IOException {
        Files.copy(imdbFileUrl.openStream(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

    }
}
