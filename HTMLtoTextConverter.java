package crawl8;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class HTMLtoTextConverter {
    private static final String HTML_FOLDER = "saved_pages";  
    private static final String TEXT_FOLDER = "converted_text";  

    public static void main(String[] args) {
        File inputFolder = new File(HTML_FOLDER);
        File outputFolder = new File(TEXT_FOLDER);

        // Create the output folder if it doesn't exist
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }

        // Process each HTML file
        File[] htmlFiles = inputFolder.listFiles((dir, name) -> name.endsWith(".html"));
        if (htmlFiles == null || htmlFiles.length == 0) {
            System.out.println("No HTML files found in " + HTML_FOLDER);
            return;
        }

        for (File file : htmlFiles) {
            convertHtmlToText(file);
        }
    }

    private static void convertHtmlToText(File htmlFile) {
        try {
            // Parse HTML content with Jsoup
            Document doc = Jsoup.parse(htmlFile, "UTF-8");

            // Extract plain text
            String textContent = doc.text();

            // Extract all URLs from <a> tags
            Elements links = doc.select("a[href]");
            StringBuilder urlList = new StringBuilder("\n\n--- Extracted URLs ---\n");
            for (Element link : links) {
                String url = link.absUrl("href");
                urlList.append(url).append("\n");
            }

            // Save text + URLs to a file
            File textFile = new File(TEXT_FOLDER + "/" + htmlFile.getName().replace(".html", ".txt"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
                writer.write(textContent);  // Write extracted text
                writer.write(urlList.toString());  // Append extracted URLs
            }

            System.out.println("Converted: " + htmlFile.getName() + " → " + textFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error processing file: " + htmlFile.getName() + " - " + e.getMessage());
        }
    }
}
