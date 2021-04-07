package com.cinus.io;


import com.cinus.array.ArrayUtils;
import com.cinus.charset.CharsetUtils;
import com.cinus.exception.ExceptionUtils;
import com.cinus.thirdparty.Constants;
import com.cinus.thirdparty.binary.StringUtils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;


public class IOUtils {


    private static final int DEFAULT_BUFFER_SIZE = 1024;


    public static int copy(Reader reader, Writer writer) throws IOException {
        return copy(reader, writer, DEFAULT_BUFFER_SIZE);
    }


    public static int copy(Reader reader, Writer writer, int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];
        int count = 0;
        int readSize;
        while ((readSize = reader.read(buffer, 0, bufferSize)) >= 0) {
            writer.write(buffer, 0, readSize);
            count += readSize;
        }
        writer.flush();
        return count;
    }


    public static int copy(InputStream in, OutputStream out) throws IOException {
        return copy(in, out, DEFAULT_BUFFER_SIZE);
    }


    public static int copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        for (int n; (n = in.read(buffer)) != -1; ) {
            out.write(buffer, 0, n);
            count += n;
        }
        out.flush();
        return count;
    }


    public static long copy(FileInputStream in, FileOutputStream out) throws IOException {
        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();
        return inChannel.transferTo(0, inChannel.size(), outChannel);
    }


    public static BufferedReader getReader(InputStream in, String charset) throws IOException {
        InputStreamReader reader;
        if (StringUtils.isBlank(charset)) {
            reader = new InputStreamReader(in);
        } else {
            reader = new InputStreamReader(in, charset);
        }

        return new BufferedReader(reader);
    }


    public static String getString(InputStream in, String charset) throws IOException {
        final long len = in.available();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }
        byte[] bytes = new byte[(int) len];
        in.read(bytes);
        return new String(bytes, charset);
    }


    public static <T extends Collection<String>> T getLines(InputStream in, String charset, T collection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            collection.add(line);
        }
        return collection;
    }


    public static String getString(FileChannel fileChannel, String charset) throws IOException {
        final MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()).load();
        return CharsetUtils.str(buffer, charset);
    }


    public static ByteArrayInputStream toStream(String content, String charset) {
        if (content == null) {
            return null;
        }
        byte[] data;
        try {
            data = StringUtils.isBlank(charset) ? content.getBytes() : content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.utilException(String.format("Invalid charset [%s] !", charset), e);
        }
        return new ByteArrayInputStream(data);
    }


    public static void write(OutputStream out, String charset, boolean isCloseOut, Object... contents) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    osw.write(content.toString());
                }
            }
        } catch (Exception e) {
            throw new IOException("Write content to OutputStream error!", e);
        } finally {
            if (isCloseOut) {
                osw.close();
            }
        }
    }

    public static String read(InputStream is) throws IOException {
        return read(is, null);
    }


    public static String read(InputStream is, EndChecker checker) throws IOException {
        if (is == null) return null;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            String line = br.readLine();
            sb.append(line).append(Constants.ENTER);
            if (checker != null && checker.isEnd(line)) {
                break;
            }
        }
        if (sb.length() > 0) {
            sb.delete(sb.length() - Constants.ENTER.length(), sb.length());
        }
        return sb.toString();
    }


    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw ExceptionUtils.utilException(e);
            }
        }
    }


    public static boolean checkFileExist(String pathname) {
        return new File(pathname).exists();
    }

    public static void echo(Object content, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            System.out.println(content);
        }
        System.out.println(String.format(content.toString(), args));
    }
}
