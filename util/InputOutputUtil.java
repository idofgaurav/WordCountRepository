package com.floow.challenge.wordcount.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;

/**
 * The Class InputOutputUtil.
 * 
 * @author Gaurav Singhal
 * 
 * InputOutputUtil provides a set of static methods for reading and writing text. 
 *
 */
public class InputOutputUtil {

    public final static char END_OF_FILE = (char)0xFFFF; 

    public final static char END_OF_LINE = '\n';          
    
    public static boolean readSelectedFile() {
        if (dialog == null)
            dialog = new JFileChooser();
        dialog.setDialogTitle("Select File for Processing:");
        
        int option = dialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return false;
        
        File selectedFile = dialog.getSelectedFile();
        BufferedReader newin;
        try {
            newin = new BufferedReader( new FileReader(selectedFile) );
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Can't open file \"" + selectedFile.getName() + "\" for input.\n"
                             + "(Error :" + e + ")");
        }
        if (!readingStandardInput) { 
            try {
                in.close();
            }
            catch (Exception e) {
            }
        }
        emptyBuffer(); 
        in = newin;
        inputFileName = selectedFile.getName();
        readingStandardInput = false;
        return true;
    }
    
    
    public static char getAnyChar() { 
        return readChar(); 
    }

    public static char peek() { 
        return lookChar();
    }
    
    public static String getln() {
        StringBuffer s = new StringBuffer(100);
        char ch = readChar();
        while (ch != '\n') {
            s.append(ch);
            ch = readChar();
        }
        return s.toString();
    }
    
    private static String inputFileName; 
    
    private static JFileChooser dialog; 
    
    private final static BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in)); 

    private static BufferedReader in = standardInput; 
    
    private static boolean readingStandardInput = true;
    
    private static String buffer = null;  
    private static int pos = 0;           
    
    
    private static char lookChar() { 
        if (buffer == null || pos > buffer.length())
            fillBuffer();
        if (buffer == null)
            return END_OF_FILE;
        else if (pos == buffer.length())
            return '\n';
        else 
            return buffer.charAt(pos);
    }
    
    private static char readChar() { 
        char ch = lookChar();
        if (buffer == null) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Attempt to read past end-of-file in standard input???");
            else
                throw new IllegalArgumentException("Attempt to read past end-of-file in file \"" + inputFileName + "\".");
        }
        pos++;
        return ch;
    }
        
    private static void fillBuffer() {    
        try {
            buffer = in.readLine();
        }
        catch (Exception e) {
            if (readingStandardInput)
                throw new IllegalArgumentException("Error while reading standard input???");
            else if (inputFileName != null)
                throw new IllegalArgumentException("Error while attempting to read from file \"" + inputFileName + "\".");
            else
                throw new IllegalArgumentException("Errow while attempting to read form an input stream.");
        }
        pos = 0;
    }
    
    private static void emptyBuffer() {   
        buffer = null;
    }
    
        
}
