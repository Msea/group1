import java.util.Scanner;
import java.io.*;

public class DataParser {

  public static void main(String[] args) throws IOException {

    PrintWriter printWriter;
    printWriter = new PrintWriter (new FileWriter("test.txt"));
    Scanner keyboard = new Scanner(System.in);
    //String[][] data = new String[8][8];
    int numberOfTrades = -1;
    String[][] data;
    String fromFile;
    //data = new String[cols][rows];


    Scanner scanFile = new Scanner(new FileReader ("Data Files/input1.csv"));
    while (scanFile.hasNext()){
      scanFile.next();
      numberOfTrades++;
    }

    data = new String[numberOfTrades][4];

    int counter = -1;
    Scanner scanFileAgain = new Scanner(new FileReader ("Data Files/input1.csv"));
    while (scanFileAgain.hasNext()){
      fromFile = scanFileAgain.next();
      if ( counter >= 0){
        data[counter] = fromFile.split(",");
      }
      counter++;
    }
    
    // printWriter.println(data[0][0]);
    // printWriter.println(data[0][1]);
    // printWriter.println(data[0][2]);
    // printWriter.println(data[0][3]);
    // printWriter.println(data[1][0]);
    // printWriter.println(data[1][1]);
    // printWriter.println(data[1][2]);
    // printWriter.println(data[1][3]);
    // printWriter.println(data[2][0]);
    // printWriter.println(data[2][1]);
    // printWriter.println(data[2][2]);
    // printWriter.println(data[2][3]);
    // printWriter.println(data[3][0]);
    // printWriter.println(data[3][1]);
    // printWriter.println(data[3][2]);
    // printWriter.println(data[3][3]);
    // printWriter.println(data[4][0]);
    // printWriter.println(data[4][1]);
    // printWriter.println(data[4][2]);
    // printWriter.println(data[4][3]);
    // printWriter.println(data[data.length-1][0]);
    // printWriter.println(data[data.length-1][1]);
    // printWriter.println(data[data.length-1][2]);
    // printWriter.println(data[data.length-1][3]);

    // printWriter.close();
    


  }



}

//to compile and run
//javac DataParser.java
//java DataParser