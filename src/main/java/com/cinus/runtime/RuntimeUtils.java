package com.cinus.runtime;

import com.cinus.exception.UtilException;
import com.cinus.io.IOUtils;

public class RuntimeUtils {

    public static ExecResult excuteCmd(String command) {
        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            ExecResult result = new ExecResult(exitCode);
            result.setError(IOUtils.read(process.getErrorStream()));
            result.setResult(IOUtils.read(process.getInputStream()));
            return result;
        } catch (Throwable t) {
            throw new UtilException(t);
        }
    }


    public static void addShutdownHook(Thread hook) {
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
