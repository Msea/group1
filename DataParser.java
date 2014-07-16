import java.util.Scanner;
import java.io.*;
//import java.util.Arrays; 

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
    
    
  String[][][][] test = sortData(data);

  for(int i=0; i<test.length; i++){
    printWriter.println("new symbol");
    for(int j=0; j<test[i].length; j++){
      printWriter.println("new exchange");
      for (int k=0; k<test[i][j].length; k++){
        printWriter.println("new row");
        for (int m=0; m<test[i][j][k].length; m++){
          printWriter.print(test[i][j][k][m]);
          printWriter.print(",");
        }
        printWriter.println("");
      }
    }
  }
printWriter.close();
  }

  //This method will sort data into a 4 dimesional array containing a an array for each symbol. Each symbol array will contain an array for each exchange within the symbol. Each exchange array will contain a row for every trade, and a row entry for every piece of data of the trade.
  public static String[][][][] sortData(String[][] data){
    String[][][][] sortedData = new String[data.length][data.length][data.length][4];

    for(int i =0; i < data.length; i++){
      boolean symbolExists = false;
      for (int j=0; j< sortedData.length; j++){
        if ((data[i][0]).equals(sortedData[j][0][0][0])){
          symbolExists = true;
          boolean exchangeExists = false;
          for (int k=0; k<(sortedData[j].length); k++){
            if ((data[i][1]).equals(sortedData[j][k][0][1])){
              exchangeExists = true;
              //In this case the Symbol, Exchange pair already exists at j,k. We will now look for the next available row in this array to place the entry in question (data[i]).
              boolean notYet = true;
              for (int m=0; notYet && m<(sortedData[j][k].length); m++){
                if (sortedData[j][k][m][0]==null){
                  notYet = false;//no need to continue looping
                  //we have found the next empty, so put it in:
                  // for (int n=0; n<4; n++){
                  //   sortedData[j][k][m][n] = data[i][n];
                  // }
                sortedData[j][k][m] = data[i];
                }
              }
            }
          }
          if (! exchangeExists){
            //In this case the Symbol already exists at j, but the exchange does not. We will look for the next available spot in this array to start a new subarray for this exchange.
            boolean notYet = true;
            for (int m=0; notYet && m<(sortedData[j].length); m++){
              if (sortedData[j][m][0][0]==null){
                notYet = false;//no need to continue looping
                //we have found the next empty, so put it in:
                // for (int n=0; n<4; n++){
                //   sortedData[j][m][0][n] = data[i][n];
                // }
                sortedData[j][m][0] = data[i];
              }
            }
          }
        }
      }
      if (! symbolExists){
        //In this case, the symbol does not yet exist, so we put it in the next avilable spot.
        boolean notYet = true;
        for (int m=0; (notYet && m<sortedData.length); m++){ 
          if (sortedData[m][0][0][0]==null){
            notYet = false;//no need to continue looping
            //we have found the next empty, so put it in:
            // for (int n=0; n<4; n++){
            //   sortedData[m][0][0][n] = data[i][n];
            // }
            sortedData[m][0][0] = data[i];
          }
        }
      }

    }
    String[][][][] constrictedSortedData = constrictData(sortedData);

    return constrictedSortedData;
  }

  //method takes a 4-d array, and removes all nested arrays with null initials (in our program that is just arrays with null arrays)
  public static String[][][][] constrictData(String[][][][] data){
    String[][][][] constrictedData = new String[data.length][data.length][data.length][0];
    boolean moreSymbols = true;
    boolean moreExchanges;
    boolean moreTrades;
    int numberOfSymbols;
    //first we loop through top level to remove extranious
    for (int i = 0; moreSymbols && i<data.length; i++){
      if (data[i][0][0][0]==null){
        //in this case we have found our last Symbol
        constrictedData = new String[i][data.length][data.length][0];
        moreSymbols = false;
      }
    }
    //next we loop through middle level to remove extranious
    for (int i=0; i<constrictedData.length; i++){
      moreExchanges = true;
      for (int j=0; moreExchanges && j<data[i].length; j++){
        if (data[i][j][0][0]==null){
          constrictedData[i] = new String[j][data.length][0];
          moreExchanges = false;
        }
      }
    }
    //then we loop through third level to remove extranious
    for (int i=0; i<constrictedData.length; i++){
      for (int j=0; j < constrictedData[i].length; j++){
        moreTrades = true;
        for (int k=0; moreTrades && k < data[i][j].length; k++){
          if (data[i][j][k][0]==null){
            constrictedData[i][j] = new String[k][4];
            for (int m=0; m<k; m++){
              constrictedData[i][j][m] = data[i][j][m];
            }
            moreTrades = false;
          }
        }
      }
    }
    return constrictedData;
  }



}

//to compile and run
//javac DataParser.java
//java DataParser