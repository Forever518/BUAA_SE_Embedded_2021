package com.fri01.xiaozhi.common.util.system;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 *     This class is to help get process id, get sub-process ids, and send SIGINT to a process.
 * </p>
 *
 * @author forever518
 * @since 2021-05-06
 */
public abstract class ProcessManager {

    public static String getProcessId(Process process) throws Exception {
        Class<?> c = Class.forName("java.lang.UNIXProcess");
        Field field = c.getDeclaredField("pid");
        field.setAccessible(true);
        return String.valueOf(field.get(process));
    }

    public static String[] getSubProcessIds(String ppid) throws Exception {
        String[] commands = new String[]{"/bin/bash", "-c",
                "ps -ef | awk '$3==" + ppid + " {print $2}'"};
        Process process = Runtime.getRuntime().exec(commands);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        while ((temp = bufferedReader.readLine()) != null) {//循环读取缓冲区中的数据
            stringBuilder.append(temp).append("\n");
        }
        process.waitFor();
        process.destroy();
        return stringBuilder.toString().split("\n");
    }

    public static void sigintProcessById(String pid) throws Exception {
        String[] commands = new String[]{"/bin/bash", "-c", "kill -2 " + pid};
        Process process = Runtime.getRuntime().exec(commands);
        process.waitFor();
        process.destroy();
    }

}
