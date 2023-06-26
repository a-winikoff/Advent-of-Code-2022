import java.io.File;
import java.io.IOException;
import java.util.*;

public class AdventMethods {

  public void day25() {
    System.out.println("Not implemented.");
  }

  public void day24() {
    System.out.println("Not implemented.");
  }

  public void day23() {
    System.out.println("Not implemented.");
  }

  public void day22() {
    System.out.println("Not implemented.");
  }

  public void day21() {
    System.out.println("Not implemented.");
  }

  public void day20() {
    System.out.println("Not implemented.");
  }

  public void day19() {
    System.out.println("Not implemented.");
  }

  public void day18() {
    System.out.println("Not implemented.");
  }

  public void day17() {
    System.out.println("Not implemented.");
  }

  public void day16() {
    System.out.println("Not implemented.");
  }

  public void day15() {
    System.out.println("Not implemented.");
  }

  public void day14() {
    System.out.println("Not implemented.");
  }

  public void day13() {
    System.out.println("Not implemented.");
  }

  public void day12() {
    System.out.println("Not implemented.");
  }

  public void day11() {
    System.out.println("Not implemented.");
  }

  public void day10() {
    System.out.println("Not implemented.");
  }

  public void day9() {
    System.out.println("Not implemented.");
  }

  public void day8() {
    System.out.println("Not implemented.");
  }

  public void day7() {
    File file = new File("Day 7.txt");
    stringValues = new String[getFileSize(file)];
    readValuesAsStrings(file);

    List<String> cd = new ArrayList<String> ();

    Map<String, Integer> dirDeep = new HashMap<>(); // Dir/Dir/, sizeOfCD
    Map<String, String[]> dirContentDirs = new HashMap<>(); // Dir/Dir/, String[] of Dir
    Map<String, Integer> dirContentFiles = new HashMap<>(); // Dir/Dir/, sum of filesizes
    
    for (int i=0; i<stringValues.length; i++){
      String s = stringValues[i];
      if (s.substring(0,4).equals("$ cd")){
        if (s.substring(5).equals(".."))
          cd.remove(cd.size()-1);
        else if (s.substring(5).equals("/"))
          cd.add("/");
        else
          cd.add(s.substring(5)+"/");
      }
      else if (s.equals("$ ls")){
        int count;
        String dir;
        if (cd.size()<2)
          dir = "/";
        else
          dir = cd.get(cd.size()-2)+cd.get(cd.size()-1);
        for (count=1; count<stringValues.length-i; count++)
          if (stringValues[i+count].substring(0,1).equals("$"))
            break; // Need a count
        String[] tDirs = new String[count];
        int dInd = 0;
        int[] tFiles = new int[count];
        int fInd = 0;
        for (int a=1; a<count; a++){
          String str = stringValues[i+a];
          if (str.substring(0,3).equals("dir"))
            tDirs[dInd++] = str.substring(4);
          else
            tFiles[fInd++] = Integer.parseInt(str.substring(0,str.indexOf(" ")));
        }
        tDirs = Arrays.stream(tDirs) .filter(strr->(strr!=null&&strr.length()>0)) .toArray(String[]::new);
        int tSum = 0;
        for (int size:tFiles)
          tSum+=size;
        dirDeep.put(dir, cd.size());
        dirContentDirs.put(dir, tDirs);
        dirContentFiles.put(dir, tSum);
      }
    }
    // Debugging in the following paragraph of code
    //System.out.println(Arrays.toString(dirContentDirs.get("vzgpfd/vzgpfd/")));
    //System.out.println( dirContentDirs.keySet().stream() .filter(key -> Arrays.asList(dirContentDirs.get(key)).contains("vzgpfd")) .collect(java.util.stream.Collectors.toList()));
    
    LinkedHashMap<String, Integer> orderedDirs = new LinkedHashMap<>();
    dirDeep.entrySet().stream() .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) .forEachOrdered(x->orderedDirs.put( x.getKey(),x.getValue()));

    Map<String, Integer> dirSize = new HashMap<String, Integer>(); // Dir/Dir/, total size of files
    for (String dir:orderedDirs.keySet()){
      String[] tDirs = dirContentDirs.get(dir);
      int sum = dirContentFiles.get(dir);
      if (tDirs.length>0)
        for (String d:tDirs){
          if (dirSize.get( dir.substring(dir.indexOf("/")+1) +d+"/")!=null)
            sum += dirSize.get( dir.substring(dir.indexOf("/")+1) +d+"/");
          //else
          //  System.out.println(dir);
        }
      dirSize.put(dir,sum);
    }
    // After running through, two directories didn't work for me: "/" and "vzgpfd/vzgpfd/". I'll do those manually here.
    int sum = dirContentFiles.get("/");
    for (String d:dirContentDirs.get("/"))
      if (dirSize.get("/"+d+"/")!=null)
        sum += dirSize.get("/"+d+"/");
    dirSize.put("/", sum);

    int part1 = 0;
    for (int dirSum:dirSize.values())
      if (dirSum<100001)
        part1+=dirSum;

    LinkedHashMap<String, Integer> orderedSizes = new LinkedHashMap<>();
    dirSize.entrySet() .stream() .sorted(Map.Entry.comparingByValue()) .forEachOrdered(x->orderedSizes.put(x.getKey(),x.getValue()));

    int part2 = -1;
    //System.out.println(orderedSizes.get("/")+"; "+Arrays.toString(dirContentDirs.get("/"))+"; ["+orderedSizes.get("/qffvbf/")+", "+orderedSizes.get("/qjjgh/")+", "+orderedSizes.get("/vpjqpqfm/")+"]; "+dirContentFiles.get("/"));
    //System.out.println(orderedSizes.get("/qffvbf/")+"; "+Arrays.toString(dirContentDirs.get("/qffvbf/"))+"; "+dirContentFiles.get("/qffvbf/"));
    for (int dirVal:orderedSizes.values())
      if (70000000-orderedSizes.get("/")+dirVal>30000000){
        part2 = dirVal;
        break;
      }

    //System.out.println(part1+"*; "+part2+""); //1397<x<3347728

    // I gave up on the idea of doing it all in only one method.
    // Instead, here's the whole thing in only one class.
    Day7FolderObject.run(stringValues);
    System.out.println(Day7FolderObject.part1()+"*; "+Day7FolderObject.part2()+" (Still not working!!!) [I had to do this in another class ;(]");
  }

  public void day6() {
    File file = new File("Day 6.txt");
    stringValues = new String[14]; // getFileSize(file)
    readValuesAsStrings(file);

    String s = stringValues[0]; // Stream
    stringValues[0] = s.substring(0,1);
    stringValues[1] = s.substring(1,2);
    stringValues[2] = s.substring(2,3);

    int i;
    for (i=0; i<s.length()-3; i++){
      for (int l=0; l<4; l++)
        stringValues[l] = s.substring(i+l,i+l+1); // I'm going to take advantage of the array space

      boolean b = true;
      checkEquals:
      for (int n=0; n<4; n++)
        for (int r=n+1; r<4; r++)
          if (stringValues[n].equals(stringValues[r])){
            b = false;
            break checkEquals;
          }

      if (b)
        break;
    }

    // Part 2
    int j;
    for (j=0; j<s.length()-13; j++){
      for (int l=0; l<14; l++)
        stringValues[l] = s.substring(j+l,j+l+1);

      boolean b = true;
      checkEquals:
      for (int n=0; n<14; n++)
        for (int r=n+1; r<14; r++)
          if (stringValues[n].equals(stringValues[r])){
            b = false;
            break checkEquals;
          }

      if (b)
        break;
    }

    System.out.println((i+4)+"*; "+(j+14)+"*");
  }

  public void day5() {
    File file = new File("Day 5.txt");
    stringValues = new String[1000];
    readValuesAsStrings(file);

    int[][] a = new int[502][3];
    for (int i=0; i<a.length; i++){
      String s = stringValues[i];
      a[i][0] = Integer.parseInt(s.substring(s.indexOf("e")+2,s.indexOf("f")-1));
      a[i][1] = Integer.parseInt(s.substring(s.indexOf("f")+5,s.lastIndexOf("o")-2));
      a[i][2] = Integer.parseInt(s.substring(s.lastIndexOf("o")+2));
    }

    List<Character> l1 = new ArrayList<Character> ();
    List<Character> l2 = new ArrayList<Character> ();
    List<Character> l3 = new ArrayList<Character> ();
    List<Character> l4 = new ArrayList<Character> ();
    List<Character> l5 = new ArrayList<Character> ();
    List<Character> l6 = new ArrayList<Character> ();
    List<Character> l7 = new ArrayList<Character> ();
    List<Character> l8 = new ArrayList<Character> ();
    List<Character> l9 = new ArrayList<Character> ();
    List<Character>[] aList = new List[9];
    aList[0]=l1;
    aList[1]=l2;
    aList[2]=l3;
    aList[3]=l4;
    aList[4]=l5;
    aList[5]=l6;
    aList[6]=l7;
    aList[7]=l8;
    aList[8]=l9;

    char[][] init = {
      {'Z','J','N','W','P','S'},
      {'G','S','T'},
      {'V','Q','R','L','H'},
      {'V','S','T','D'},
      {'Q','Z','T','D','B','M','J'},
      {'M','W','T','J','D','C','Z','L'},
      {'L','P','M','W','G','T','J'},
      {'N','G','M','T','B','F','Q','H'},
      {'R','D','G','C','P','B','Q','W'}
    };
    for (int l=0; l<9; l++)
      for (int i=0; i<init[l].length; i++)
        aList[l].add(init[l][i]);

    for (int[] line:a)
      for (int iterations=0; iterations<line[0]; iterations++)
        aList[line[2]-1].add( aList[line[1]-1].remove(aList[line[1]-1].size()-1));

    String message = "";
    for (List<Character> list:aList)
      message+=list.get(list.size()-1);
    
    System.out.print(message+"*; ");

    //Part 2:
    l1 = new ArrayList<Character> ();
    l2 = new ArrayList<Character> ();
    l3 = new ArrayList<Character> ();
    l4 = new ArrayList<Character> ();
    l5 = new ArrayList<Character> ();
    l6 = new ArrayList<Character> ();
    l7 = new ArrayList<Character> ();
    l8 = new ArrayList<Character> ();
    l9 = new ArrayList<Character> ();
    List<Character> temp = new ArrayList<Character> ();

    for (int l=0; l<9; l++)
      for (int i=0; i<init[l].length; i++)
        aList[l].add(init[l][i]);

    for (int[] line:a){
      for (int iterations=0; iterations<line[0]; iterations++)
        temp.add( aList[line[1]-1].remove(aList[line[1]-1].size()-1));
      for (int iterations=0; iterations<line[0]; iterations++)
        aList[line[2]-1].add( temp.remove(temp.size()-1));
    }

    message = "";
    for (List<Character> list:aList)
      message+=list.get(list.size()-1);

    System.out.println(message+"* (This uses an \"unchecked List\" variable)");
  }

  public void day4() {
    File file = new File("Day 4.txt");
    stringValues = new String[1000];
    readValuesAsStrings(file);

    int count = 0;
    int[][] a = new int[1000][4];
    for (int i=0; i<1000; i++){
      String s = stringValues[i];
      a[i][0] = Integer.parseInt(s.substring(0,s.indexOf("-")));
      a[i][1] = Integer.parseInt(s.substring(s.indexOf("-")+1,s.indexOf(",")));
      a[i][2] = Integer.parseInt(s.substring(s.indexOf(",")+1,s.lastIndexOf("-")));
      a[i][3] = Integer.parseInt(s.substring(s.lastIndexOf("-")+1));
    }

    for (int i=0; i<1000; i++){
      if (a[i][1]>=a[i][3]&&a[i][0]<=a[i][2]) // if bounds of 2 are within bounds of 1
          count++;
      else if (a[i][3]>=a[i][1]&&a[i][2]<=a[i][0]) // if bounds of 1 are within bounds of 2
          count++;
    }

    int count2 = 0;
    for (int i=0; i<1000; i++){
      if (a[i][1]>=a[i][2]&&a[i][0]<=a[i][2]) // if lower bound of 2 is within bounds of 1
        count2++;
      else if (a[i][3]>=a[i][0]&&a[i][2]<=a[i][0]) // if lower bound of 1 is within bounds of 2
        count2++;
    }
    
    System.out.println(count+"*; "+count2+"*");
  }

  public void day3() {
    File file = new File("Day 3.txt");
    stringValues = new String[300];
    readValuesAsStrings(file);

    String[] s1 = new String[300];
    String[] s2 = new String[300];
    int[] a = new int[300];

    for (int i=0; i<300; i++){
      s1[i] = stringValues[i].substring(0,stringValues[i].length()/2);
      s2[i] = stringValues[i].substring(stringValues[i].length()/2);

      charCheck:
      for (int j=0; j<s1[i].length(); j++)
        for (int k=0; k<s2[i].length(); k++)
          if (s1[i].charAt(j)==s2[i].charAt(k)){
            if (Character.isUpperCase(s1[i].charAt(j)))
              a[i] = s1[i].charAt(j)-38;
            else
              a[i] = s1[i].charAt(j)-96;
            break charCheck;
          }
    }

    int priorities = 0;
    for (int i:a)
      priorities+=i;
    
    
    System.out.print(priorities+"*; ");

    //Part 2

    int[] b = new int[100];
    String[] sv = stringValues;

    for (int i=0; i<300; i+=3){
      charCheck:
      for (int j=0; j<sv[i].length(); j++)
        for (int k=0; k<sv[i+1].length(); k++)
          for (int m=0; m<sv[i+2].length(); m++)
            if (sv[i].charAt(j)==sv[i+1].charAt(k)&&sv[i].charAt(j)==sv[i+2].charAt(m)){
              if (Character.isUpperCase(sv[i].charAt(j)))
                b[i/3] = sv[i].charAt(j)-38;
              else
                b[i/3] = sv[i].charAt(j)-96;
              break charCheck;
            }
    }

    priorities = 0;
    for (int i:b)
      priorities+=i;
    
    System.out.println(priorities+"*");
  }

  public void day2() {
    Map<String, Integer> m = new HashMap<String, Integer>();
    m.put("A X", 4); // Let A = Opponent Rock
    m.put("A Y", 8); // Let B = Opponent Paper
    m.put("A Z", 3); // Let C = Opponent Scissors
    m.put("B X", 1);
    m.put("B Y", 5); // Let X = Your Rock
    m.put("B Z", 9); // Let Y = Your Paper
    m.put("C X", 7); // Let Z = Your Scissors
    m.put("C Y", 2);
    m.put("C Z", 6);

    Map<String, Integer> m1 = new HashMap<String, Integer>();
    m1.put("A X", 3); // Let X = Your Loss
    m1.put("A Y", 4); // Let Y = Your Draw
    m1.put("A Z", 8); // Let Z = Your Win
    m1.put("B X", 1);
    m1.put("B Y", 5);
    m1.put("B Z", 9);
    m1.put("C X", 2);
    m1.put("C Y", 6);
    m1.put("C Z", 7);

    File file = new File("Day 2.txt");
    stringValues = new String[2500];
    readValuesAsStrings(file);
    
    int[] scores = new int[2500];
    for (int i=0;i<2500;i++)
      scores[i] = m.get(stringValues[i]);

    int count = 0;
    for (int i:scores)
      count+=i;

    scores = new int[2500];
    for (int i=0;i<2500;i++)
      scores[i] = m1.get(stringValues[i]);

    int count1 = 0;
    for (int i:scores)
      count1+=i;
    
    System.out.println(count+"*; "+count1+"*");
  }

  public void day1() {
    File file = new File("Day 1.txt");
    int fileSize = getFileSize(file);
    values = new int[fileSize]; // or stringValues = new String[fileSize];
    readValuesAsNumbers(file, true); // or readValuesAsStrings(file)

    List<Integer> elves = new ArrayList<Integer>();
    int count = 0;

    for (int c:values){
      count+=c;
      if (c==0){
        elves.add(count);
        count=0;
      }
    }
    elves.add(count);

    int[] elvesArray = new int[elves.size()];
    for(int i=0; i<elves.size(); i++)
      elvesArray[i] = elves.get(i);
    
    Arrays.sort(elvesArray);

    System.out.print(elvesArray[elvesArray.length-1]+"*; ");

    //Part 2
    System.out.println((elvesArray[elvesArray.length-1]+elvesArray[elvesArray.length-2]+elvesArray[elvesArray.length-3])+"*");
  }
  
  public void day0() {
    System.out.println("(See the method array in AdventMethods.java)");
  }

  // This is how I've been calling the methods this whole time.
  interface methodCall {
    void method();
  }

  public void method(int index) {
    methods[index].method();
  }

  private methodCall[] methods = new methodCall[] { new methodCall() {
    public void method() {
      day0();
    }
  }, new methodCall() {
    public void method() {
      day1();
    }
  }, new methodCall() {
    public void method() {
      day2();
    }
  }, new methodCall() {
    public void method() {
      day3();
    }
  }, new methodCall() {
    public void method() {
      day4();
    }
  }, new methodCall() {
    public void method() {
      day5();
    }
  }, new methodCall() {
    public void method() {
      day6();
    }
  }, new methodCall() {
    public void method() {
      day7();
    }
  }, new methodCall() {
    public void method() {
      day8();
    }
  }, new methodCall() {
    public void method() {
      day9();
    }
  }, new methodCall() {
    public void method() {
      day10();
    }
  }, new methodCall() {
    public void method() {
      day11();
    }
  }, new methodCall() {
    public void method() {
      day12();
    }
  }, new methodCall() {
    public void method() {
      day13();
    }
  }, new methodCall() {
    public void method() {
      day14();
    }
  }, new methodCall() {
    public void method() {
      day15();
    }
  }, new methodCall() {
    public void method() {
      day16();
    }
  }, new methodCall() {
    public void method() {
      day17();
    }
  }, new methodCall() {
    public void method() {
      day18();
    }
  }, new methodCall() {
    public void method() {
      day19();
    }
  }, new methodCall() {
    public void method() {
      day20();
    }
  }, new methodCall() {
    public void method() {
      day21();
    }
  }, new methodCall() {
    public void method() {
      day22();
    }
  }, new methodCall() {
    public void method() {
      day23();
    }
  }, new methodCall() {
    public void method() {
      day24();
    }
  }, new methodCall() {
    public void method() {
      day25();
    }
  } };

  // And here are the file input methods.
  static int[] values;
  static String[] stringValues;

  public static int getFileSize(File f) {
    // This method counts the number of lines in the data file
    // We need this so we know how big to make our array
    int count = 0;
    try {

      Scanner sc = new Scanner(f);
      while (sc.hasNext()) {
        String str = sc.nextLine();
        count++;
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return count;
  }

  public static void readValuesAsNumbers(File f) {
    // This reads the numbers in the data file
    // And stores them in the array "values"
    try {
      Scanner sc = new Scanner(f);
      int count = 0;
      while (sc.hasNext()) {
        int i = sc.nextInt();
        values[count++] = i;
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void readValuesAsNumbers(File f, boolean b) {
    // This reads the numbers in the data file
    // And stores them in the array "values"
    // Stores new lines as 0 if boolean is true
    try {
      Scanner sc = new Scanner(f);
      int count = 0;
      while (sc.hasNext()) {
        if (b) {
          String s = sc.nextLine();
          if (s.equals(""))
            values[count++] = 0;
          else
            values[count++] = Integer.parseInt(s);
        }
        else {
          int i = sc.nextInt();
          values[count++] = i;
        }
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void readValuesAsStrings(File f) {
    // This reads the numbers in the data file
    // And stores them in the array "stringValues"
    try {
      Scanner sc = new Scanner(f);
      int count = 0;
      while (sc.hasNext()) {
        String i = sc.nextLine();
        stringValues[count++] = i;
      }
      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}