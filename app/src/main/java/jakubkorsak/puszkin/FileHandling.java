package jakubkorsak.puszkin;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class FileHandling {
    /**
     * Odczytuje plik pod daną ścieżką
     *
     * @param fileName nazwa pliku webView domyślnej ścieżce aplikacji
     * @param context  kontekst sendera
     * @return zawartość pliku
     */
    static String readFileAsString(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null) stringBuilder.append(line);
        } catch (IOException ignored) {
        }
        return stringBuilder.toString();
    }

    /**
     * Tworzy/nadpisuje plik pod daną ścieżką
     * @param fileContents To co ma zapisać webView pliku
     * @param fileName nazwa pliku webView domyślnej ścieżce aplikacji
     * @param context kontekst sendera
     */
    static void writeStringAsFile(final String fileContents, String fileName, Context context) {
        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), fileName));
            out.write(fileContents);
            out.close();
        } catch (IOException ignored) {
        }
    }
}
