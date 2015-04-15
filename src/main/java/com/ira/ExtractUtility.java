package com.ira;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

/**
 * Created by Iryna on 4/14/15.
 */
public class ExtractUtility {

    private final static int BUFFER = 2048;

    public void extractFile(Path sourceFilePath, Path destinationDir) throws IOException {

        if (Files.isDirectory(sourceFilePath, LinkOption.NOFOLLOW_LINKS)) {
            throw new IllegalArgumentException("The provided source is not a file");
        }

        FileInputStream fin = new FileInputStream(sourceFilePath.toFile());
        BufferedInputStream in = new BufferedInputStream(fin);
        GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
        TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

        TarArchiveEntry entry = null;

        /** Read the tar entries using the getNextEntry method **/

        while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {

            System.out.println("Extracting: " + entry.getName());

            /** If the entry is a directory, create the directory. **/

            if (entry.isDirectory()) {

                File f = new File(destinationDir + entry.getName());
                f.mkdirs();
            }
            /**
             * If the entry is a file,write the decompressed file to the disk
             * and close destination stream.
             **/
            else {
                int count;
                byte data[] = new byte[BUFFER];

                FileOutputStream fos = new FileOutputStream(destinationDir
                        + entry.getName());
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);
                while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.close();
            }
        }

        /** Close the input stream **/
        tarIn.close();
        System.out.println("untar completed successfully!!");

    }
}
