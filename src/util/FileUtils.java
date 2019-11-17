package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import model.FileMessage;

public class FileUtils {

    public static FileMessage fileToFileMessage(File f) throws IOException {
        FileMessage fm = new FileMessage();

        String filename = f.getName();
        Path path = f.toPath();

        fm.setFilename(FileUtils.getFilenameWithoutExtension(filename));
        fm.setFileType(FileUtils.getExtension(filename));
        fm.setOriginPath(f.getAbsolutePath());
        fm.setCreatedDate(FileUtils.getCreationDate(path));
        fm.setContent(FileUtils.getContent(path));
        fm.setFileSize(fm.getContent().length);

        return fm;
    }

    public static String getExtension(String filename) {

        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i >= 0) {
            extension = filename.substring(i + 1);
        }

        return extension;
    }

    public static byte[] getContent(Path path) throws IOException {

        BasicFileAttributes attr = null;
        attr = Files.readAttributes(path, BasicFileAttributes.class);

        return Files.readAllBytes(path);
    }

    public static Date getCreationDate(Path path) throws IOException {

        BasicFileAttributes attr = null;
        attr = Files.readAttributes(path, BasicFileAttributes.class);

        return new Date(attr.creationTime().toMillis());
    }

    public static String getFilenameWithoutExtension(String filename) {
        return filename.replaceFirst("[.][^.]+$", "");
    }

}
