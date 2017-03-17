package com.widyan.alamku.utils;

/**
 * Created by widyan on 3/17/2017.
 */

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CacheManager {
    public static String readAllCachedText(Context context, String filename) {
        final File file = new File(context.getCacheDir(), filename);
        return readAllText(file);
    }

    public static String readAllResourceText(Context context, int resourceId) {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        return readAllText(inputStream);
    }

    public static String readAllFileText(String file) {
        try {
            final FileInputStream inputStream = new FileInputStream(file);
            return readAllText(inputStream);
        } catch(Exception ex) {
            return null;
        }
    }

    public static String readAllText(File file){
        try {
            final FileInputStream inputStream = new FileInputStream(file);
            return readAllText(inputStream);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static String readAllText(InputStream inputStream) {
        final InputStreamReader inputreader = new InputStreamReader(inputStream);
        final BufferedReader buffreader = new BufferedReader(inputreader);

        String line;
        final StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }

    public static boolean writeAllCachedText(Context context, String filename, String text) {
        final File file = new File(context.getCacheDir(), filename);
        return writeAllText(file, text);
    }

    public static boolean writeAllFileText(String filename, String text) {
        try {
            final FileOutputStream outputStream = new FileOutputStream(filename);
            return writeAllText(outputStream, text);
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean writeAllText(File file, String text) {
        try {
            final FileOutputStream outputStream = new FileOutputStream(file);
            return writeAllText(outputStream, text);
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean writeAllText(OutputStream outputStream, String text) {
        final OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream);
        final BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);

        try {
            bufferedWriter.write(text);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }
}

