package com.cinus.program;

import com.cinus.exception.ExceptionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class ProgramUtils {

    public static final String getPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.substring(0, name.indexOf('@'));
    }

    public static void writePID(String pid, String pidFile) throws IOException {
        File file = new File(pidFile);
        writePID(pid, file);
    }

    public static void writePID(String pid, File pidFile) throws IOException {
        if (!pidFile.exists()) {
            pidFile.getParentFile().mkdirs();
            if (!pidFile.createNewFile())
                throw new IOException("Can't create the pid file: " + pidFile.getAbsolutePath());
        }
        try (FileWriter fw = new FileWriter(pidFile)) {
            fw.write(pid);
            fw.flush();
        } catch (Exception e) {
            throw ExceptionUtils.utilException(e);
        }
    }

}
