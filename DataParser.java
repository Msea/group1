import java.util.Scanner;
import java.io.*;
import java.util.Arrays; 

public class DataParser {

  public static void main(String[] args) throws IOException {

    Scanner keyboard = new Scanner(System.in);
    //String[][] data = new String[8][8];
    int numberOfTrades = -1;
    String[][] data;
    String fromFile;
    //data = new String[cols][rows];

//put all this in method
    Scanner scanFile = new Scanner(new FileReader ("Data Files/sample-input.csv"));
    while (scanFile.hasNext()){
      scanFile.next();
      numberOfTrades++;
    }

    data = new String[numberOfTrades][4];

    int counter = -1;
    Scanner scanFileAgain = new Scanner(new FileReader ("Data Files/sample-input.csv"));
    while (scanFileAgain.hasNext()){
      fromFile = scanFileAgain.next();
      if ( counter >= 0){
        data[counter] = fromFile.split(",");
      }
      counter++;
    }
String[][][][] test = sortData(data);
formatOutput("test.txt", test);


  
  }

  //This method will sort data into a 4 dimesional array containing a an array for each symbol. Each symbol array will contain an array for each exchange within the symbol. Each exchange array will contain a row for every trade, and a row entry for every piece of data of the trade.
  public static String[][][][] sortData(String[][] data){
    String[][][][] sortedData = new String[data.length][data.length][data.length][4];

    for(int i =0; i < data.length; i++){//loop through data level
      boolean symbolExists = false;//assume no symbol in sorted yet matches one in data[i]
      for (int j=0; j< sortedData.length; j++){//loop through top level of sorted looking for symbol
        if ((data[i][0]).equals(sortedData[j][0][0][0])){//we have found symbol!
          symbolExists = true;
          boolean exchangeExists = false;//assume the symbol, exchange pair of data[i] doesn't exist yet in sorted
          for (int k=0; k<(sortedData[j].length); k++){//loop through second level of sorted with that symbol, looking for the exchange
            if ((data[i][1]).equals(sortedData[j][k][0][1])){//found exchange
              exchangeExists = true;
              //In this case the Symbol, Exchange pair already exists at j,k. We will now look for the next available row in this array to place the entry in question (data[i]).
              boolean notYet = true;
              for (int m=0; notYet && m<(sortedData[j][k].length); m++){
                if (sortedData[j][k][m][0]==null){
                  notYet = false;//no need to continue looping
                  //we have found the next empty, so put it in:
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
            sortedData[m][0][0] = data[i];
          }
        }
      }

    }
    String[][][][] constrictedSortedData = constrictData(sortedData);
    String[][][][] alphabetizedConstrictedSortedData = alphabatizeData(constrictedSortedData);

    return alphabetizedConstrictedSortedData;
  }

  //method takes a 4-d array, and removes all nested arrays with null initials (in our program that is just arrays with null arrays)
  public static String[][][][] constrictData(String[][][][] data){
    String[][][][] constrictedData = new String[data.length][0][0][0];
    boolean moreSymbols = true;
    boolean moreExchanges;
    boolean moreTrades;
    int numberOfSymbols;
    //first we loop through top level to remove extranious
    for (int i = 0; moreSymbols && i<data.length; i++){
      if (data[i][0][0][0]==null){
        //in this case we have found our last Symbol
        constrictedData = new String[i][data.length][0][0];
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

  public static String[][][][] alphabatizeData(String[][][][] data){
    String[] toSort;
    String[] toSortUpper = new String[data.length];
    int largestLength = 0;
    //determine needed starting dimensions for alphebatized
    for (int i=0; i<data.length; i++){
      if (data[i].length > largestLength){
        largestLength = data[i].length;
      }
    }
    String[][][][] alphabetized = new String[data.length][largestLength][0][4];

    for (int i=0; i<data.length; i++){
      toSortUpper[i] = data[i][0][0][0];//this is the symbol
    }
    Arrays.sort(toSortUpper);

    for (int i=0;i<data.length;i++){
      toSort = new String[data[i].length];
      for (int j=0; j<data[i].length;j++){
        toSort[j] = data[i][j][0][1];//this is the exchange letter
      }
      Arrays.sort(toSort);
      //now let's put them back in order
      //first find the thing in the top level data, that goes with Uppersorted[i]
        boolean stillLookingUpper = true; 
        for (int n=0; stillLookingUpper && n<data.length; n++){
          if (data[n][0][0][0].equals(toSortUpper[i])){
            //found it
            stillLookingUpper = false;
            for (int j=0; j<data[i].length;j++){
            //lets find the thing in data[i], that goes with sorted[j], and copy that into alphebatized[i][j]
            boolean stillLooking = true;
            for (int k=0; stillLooking && k<data[i].length; k++){
              if (data[n][k][0][1].equals(toSort[j])){
                //found it
                alphabetized[i][j] = new String[data[n][k].length][4];
                 for(int m=0; m<data[n][k].length;m++){
                  alphabetized[i][j][m] = data[n][k][m];
                }
                stillLooking = false;
              }
            }
          }
        }
      }
    }

    return alphabetized;
  }

  public static double averagePriceSymExchgn(String[][] data){
    double sum = 0;
    for (int i=0;i<data.length; i++){
      sum+=Double.parseDouble(data[i][2]);
    }
    float q = data.length;
    if (q == 0){q=1;}
    double ave = sum/q;
    double rounded = Math.round(ave*100)/100.0;
    return rounded;
  }

  public static double averagePriceSym(String[][][] data){
    double sum = 0;
    double entries = 0;
    for (int i=0;i<data.length; i++){
      for (int j=0;j<data[i].length; j++){
        sum += Double.parseDouble(data[i][j][2]);
        entries +=1;
      }
    }
    if (entries == 0){entries=1;}
    double ave = sum/entries;
    double rounded = (Math.round(ave*100))/100.0;
    return rounded;
  }

  public static float minPriceSymExchgn(String[][] data){
    float min = Float.parseFloat(data[0][2]);
    for (int i=0;i<data.length; i++){
      float f = Float.parseFloat(data[i][2]);
      if (f< min){
        min = f;
      }
    }
    return min;
  }

  public static float minPriceSym(String[][][] data){
    float min = Float.parseFloat(data[0][0][2]);
    for (int i=0;i<data.length; i++){
      for (int j=0;j<data[i].length; j++){
        float f = Float.parseFloat(data[i][j][2]);
        if (f< min){
          min = f;
        }
      }
    }
    return min;
  }

  public static float maxPriceSymExchgn(String[][] data){
    float max = 0;
    for (int i=0;i<data.length; i++){
      float f = Float.parseFloat(data[i][2]);
      if (f>max){
        max = f;
      }
    }
    return max;
  }

  public static float maxPriceSym(String[][][] data){
    float max = 0;
    for (int i=0;i<data.length; i++){
      for (int j=0;j<data[i].length; j++){
        float f = Float.parseFloat(data[i][j][2]);
        if (f>max){
          max = f;
        }
      }
    }
    return max;
  }

  public static int totalQuantSymExchgn(String[][] data){
    int total = 0;
    for (int i=0;i<data.length; i++){
      total +=Integer.parseInt(data[i][3]);
    }
    return total;
  }

  public static int totalQuantSym(String[][][] data){
    int total = 0;
    for (int i=0;i<data.length; i++){
      for (int j=0;j<data[i].length; j++){
      total +=Integer.parseInt(data[i][j][3]);
      }
    }
    return total;
  }

  public static void formatOutput(String fileName, String[][][][] data) throws IOException{
    PrintWriter printWriter;
    printWriter = new PrintWriter (new FileWriter(fileName));
    String toPrint;

    printWriter.println("Symbol,Exchange,Min Price,Avg Price,Max Price,Total Qty");
    
    for(int i=0; i<data.length; i++){
      //calculate values for this symbol first
      toPrint = data[i][0][0][0] + ",,";
      printWriter.print(toPrint);
      printWriter.printf("%.2f,", minPriceSym(data[i]));
      printWriter.printf("%.2f,", averagePriceSym(data[i]));
      printWriter.printf("%.2f,", maxPriceSym(data[i]));
      printWriter.println(Integer.toString(totalQuantSym(data[i])));
      for(int j=0; j<data[i].length; j++){
      // calculate values for exchange
        toPrint = data[i][j][0][0] + ",";
        toPrint += data[i][j][0][1]+",";
        printWriter.print(toPrint);
        printWriter.printf("%.2f,", minPriceSymExchgn(data[i][j]));
        printWriter.printf("%.2f,", averagePriceSymExchgn(data[i][j]));
        printWriter.printf("%.2f,", maxPriceSymExchgn(data[i][j]));
        printWriter.println(Integer.toString(totalQuantSymExchgn(data[i][j])));
      }
    }
    printWriter.close();
  }

}

//to compile and run
//javac DataParser.java
//java DataParser