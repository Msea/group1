import java.util.Scanner;
import java.io.*;

public class DataParserDriver {
  public static void main(String[] args) throws IOException {
    
    Scanner keyboard = new Scanner(System.in);
    String inputFile;
    String outputFile;

    if (args.length >=2){
      inputFile = args[0];
      outputFile = args[1];
    }
    else{
      System.out.println("Please enter the name and path of the input file");
      inputFile = keyboard.next();
      System.out.println("Please enter the name and path of the output file");
      outputFile = keyboard.next();
    }
    DataParser parse = new DataParser(inputFile, outputFile);
  }
}