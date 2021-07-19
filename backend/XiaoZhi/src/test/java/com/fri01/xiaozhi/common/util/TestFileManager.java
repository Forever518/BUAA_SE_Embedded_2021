package com.fri01.xiaozhi.common.util;

import com.fri01.xiaozhi.common.config.TestConfig;
import com.fri01.xiaozhi.common.util.system.FileManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class TestFileManager {

    public static Set<String> files = new HashSet<>();

    public static void createFakeFile(String srcFile, String file) throws IOException {
        if (FileManager.isValidFile(file)) {
            FileManager.copyFile(file, TestConfig.testPath + file);
            files.add(file);
        }
        FileManager.copyFile(srcFile, file);
    }

    public static void removeFakeFile(String file) {
    }

}
