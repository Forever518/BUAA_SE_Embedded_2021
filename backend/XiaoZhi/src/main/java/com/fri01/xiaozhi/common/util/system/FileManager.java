package com.fri01.xiaozhi.common.util.system;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 *     This class is used to help judge file's validity, transfer file format.
 * </p>
 *
 * @author forever518
 * @since 2021-05-04
 */
public abstract class FileManager {

    public static boolean isValidDirectory(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }

    public static boolean isValidFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    public static void makeDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void copyFile(String srcPath, String dstPath) throws IOException {
        File src = new File(srcPath);
        File dst = new File(dstPath);
        Files.copy(src.toPath(), dst.toPath());
    }

    public static void renameFile(String srcPath, String dstPath) throws IOException {
        File src = new File(srcPath);
        File dst = new File(dstPath);
        src.renameTo(dst);
    }

    public static String getFileString(String path) throws IOException {
        Path p = Paths.get(path);
        byte[] data = Files.readAllBytes(p);
        return new String(data, StandardCharsets.UTF_8);
    }

    public static void convertPgmToPng(String srcPath, String dstPath) throws IOException {
        File sourceFile = new File(srcPath);
        File destinationFile = new File(dstPath);
        BufferedImage sourceImage = ImageIO.read(sourceFile);
        Raster source = sourceImage.getRaster();
        BufferedImage destinationImage = new BufferedImage(sourceImage.getWidth(),
                sourceImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        destinationImage.setData(source);
        ImageIO.write(destinationImage, "png", destinationFile);
    }

    public static void writeToFile(String filePath, String s) throws Exception {
        new File(filePath).delete();
        BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
        out.write(s);
        out.close();
    }

}