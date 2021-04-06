package com.cinus.runtime;

import com.cinus.exception.ExceptionUtils;
import com.cinus.io.IOUtils;

public class RuntimeUtils {

    public static ExecResult excuteCmd(String command) {
        Process process;
        ExecResult result = new ExecResult(-1) ;
        try {
            process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            result.setCode(exitCode);
            result.setError(IOUtils.read(process.getErrorStream()));
            result.setResult(IOUtils.read(process.getInputStream()));
            return result;
        } catch (Throwable t) {
            ExceptionUtils.throwUtilException(t);
        }
        return result;
    }


    public static void addShutdownHook(Thread hook) {
        Runtime.getRuntime().addShutdownHook(hook);
    }
}
