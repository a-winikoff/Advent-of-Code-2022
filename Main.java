import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.*;

class Main {

static int[] values;
static String[] stringValues;

public static int getFileSize(File f) {
  //This method counts the number of lines in the data file
  //We need this so we know how big to make our array
  int count = 0;
  try { 
      
      Scanner sc = new Scanner(f); 
      while (sc.hasNext()) 
      { 
        String str = sc.nextLine(); 
        count++;
      }
      sc.close(); 
  } catch (IOException e){ 
       e.printStackTrace(); 
  }
    return count;
}

public static void readValuesAsNumbers(File f) {
  //This reads the numbers in the data file 
  //And stores them in the array "values"
  try { 
      Scanner sc = new Scanner(f); 
      int count = 0;
      while (sc.hasNext()) 
      { 
        int i = sc.nextInt(); 
        values[count++] = i;
      }
      sc.close(); 
    } catch (IOException e){ 
       e.printStackTrace(); 
    }

}
public static void readValuesAsNumbers(File f, boolean b) {
  //This reads the numbers in the data file 
  //And stores them in the array "values"
  //Stores new lines as 0
  try { 
      Scanner sc = new Scanner(f); 
      int count = 0;
      while (sc.hasNext()) 
      { 
        String s = sc.nextLine();
        if (s.equals(""))
          values[count++] = 0;
        else
          values[count++] = Integer.parseInt(s);
      }
      sc.close(); 
    } catch (IOException e){ 
       e.printStackTrace(); 
    }

}

  public static void readValuesAsStrings(File f) {
  //This reads the numbers in the data file 
  //And stores them in the array "stringValues"
  try { 
      Scanner sc = new Scanner(f); 
      int count = 0;
      while (sc.hasNext()) 
      { 
        String i = sc.nextLine(); 
        stringValues[count++] = i;
      }
      sc.close(); 
    } catch (IOException e){ 
       e.printStackTrace(); 
    }

}

//step 2 - add your methods here

public static void main(String[] args) {
    //step 1 - erase the data.txt file then copy and paste your personal test numbers into the data.txt file
    File file = new File ("data.txt");
    int fileSize = getFileSize(file);
    values = new int[fileSize]; //or stringValues = new String[fileSize];
    readValuesAsNumbers(file,true); //or readValuesAsStrings(file)
    //System.out.println(Arrays.toString(values));
    //Integer.valueOf(str)  converts a String to an Integer
    //Collections.sort(array) will sort an array in ascending order
     //We now have an array of data called "values" or "stringValues" that we can use to solve the challenge
    //step 3 - add your method calls here
    AdventMethods m = new AdventMethods();
    for (int i=0;i<26;i++){
      System.out.print("Day "+i+": ");
      if (i<10) System.out.print(" ");
      m.method(i);
    }
  }

}

