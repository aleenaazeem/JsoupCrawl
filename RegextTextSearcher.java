package crawl8;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class RegextTextSearcher {
    private static final String Direct = "converted_text"; // Folder with text files

    public static void main(String[] args) {
    	//here we are getting the file and running our code
        File folder = new File(Direct);
        if (!folder.exists()) {
        	// it will throw an error when the file isnt found
            System.out.println("Your file is not found: " + folder.getAbsolutePath());
            return;
        }

        //Here were are defining the regex patterns
        //the phone numbers are as +1528 or (800) etc
        String phonePattern = "\\+?\\d{1,3}[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}";
        // the email is hsidf then . then any charater and then we use + to indicate this pattern is repeating
        //the @ sign and letters repeating then we have + sign then \\ . com we have 2 or more
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        // for the url we are using https initally we have ? this because it indicates once the we have :// with
        //letters and . and special characters
        String urlPattern = "https?://[a-zA-Z0-9\\-._~:/?#@!$&'()*+,;=%]+";

        //Compiles the patterns into Pattern objects for later searching
        Pattern phoneRegex = Pattern.compile(phonePattern);
        Pattern emailRegex = Pattern.compile(emailPattern);
        Pattern urlRegex = Pattern.compile(urlPattern);

        // Process each text file
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    String content = Files.readString(file.toPath());
                    // Search and display results
                    extractMatches("Phone Numbers", phoneRegex, content);
                    extractMatches("Emails", emailRegex, content);
                    extractMatches("URLs", urlRegex, content);

                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
    }

    //In this method we are extracting matches in our code we will be finding the
    //phone numbers, emails and urls
    private static void extractMatches(String category, Pattern pattern, String content) {
        Matcher matcher = pattern.matcher(content);
        System.out.println(category + ":"); //here it will tell if its a phone number or email or url
        boolean found = false;
        while (matcher.find()) { //while our matcher is
            System.out.println(" - " + matcher.group());
            found = true;
        }
        if (!found) {
            System.out.println(" - No matches found."); //if not found then
        }
        System.out.println();
    }
}
