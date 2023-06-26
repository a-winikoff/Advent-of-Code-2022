// Note: there are only 3 public methods (all static) in this class
// void Day7FolderObject.run(String[] terminalOut): creates the entire directory
// int Day7FolderObject.part1(): outputs the answer to Day 7 Part 1
// int Day7FolderObject.part2(): outputs the answer to Day 7 Part 2

import java.util.*;

public class Day7FolderObject{
  private Day7FolderObject parent;
  private Day7FolderObject[] children;
  private HashMap<String, Integer> files = new HashMap<String, Integer>(); // unused
  private int fileSize;
  private int dirSize;
  private int size;
  private static List<String> cd = new ArrayList<String> ();
  private static HashMap<String, Day7FolderObject> thisCD = new HashMap<>(); // would be public, but not actually necessary
  private static HashMap<Day7FolderObject, Integer> deeps = new HashMap<>();
  private static HashMap<Day7FolderObject, Integer> sizes = new HashMap<>();
  private static LinkedHashMap<Day7FolderObject, Integer> orderedSizes;
  private static String[] commands;
  private static Day7FolderObject root;

  // runtime methods
  public static void run(String[] terminalOut){ // this is the ONLY public "instantiation" method
    commands = terminalOut;
    root = new Day7FolderObject(); // Creates the root folder
    
    // First create all of the folder objects by running through all the commands
    for (int ind=2; ind<commands.length; ind++){ // ind=2 b/c root is taken care of for ind==0||1
      String comm = commands[ind];
      if (comm.substring(0,4).equals("$ cd"))
        CD(comm);
      else if (comm.substring(0,4).equals("$ ls")){
        Day7FolderObject.thisCD.get(cdToString()).doLS(lsInput(ind));
      }
    }

    // Then populate the sizes HashMap from the bottom of the directory up
    LinkedHashMap<Day7FolderObject, Integer> revOrderedDeeps = new LinkedHashMap<>();
    deeps.entrySet().stream() .sorted(Map.Entry.comparingByValue( Comparator.reverseOrder())) .forEachOrdered(folder->revOrderedDeeps.put( folder.getKey(),folder.getValue()));
    for (Day7FolderObject folder:revOrderedDeeps.keySet())
      folder.calculateSize();
  }
  private Day7FolderObject(){ // Root folder only
    cd.add("/");
    parent = null;
    dirSize=0;
    fileSize=0;
    thisCD.put(cdToString(), this);
    deeps.put(this, cd.size());
    doLS(lsInput(1)); // this call is unique to the root folder
  }
  private Day7FolderObject(Day7FolderObject parent, String dir){
    cd.add((dir+"/"));
    this.parent=parent;
    dirSize=0;
    fileSize=0;
    thisCD.put(cdToString(), this);
    deeps.put(this, cd.size());
    cd.remove(cd.size()-1);
  }
  private static void CD(String c){ // would be public, but not necessary
    if (c.substring(5).equals(".."))
      cd.remove(cd.size()-1);
    else if (c.substring(5).equals("/"))
      cd.add("/");
    else
      cd.add(c.substring(5)+"/");
  }
  private static String cdToString(){
    String r = "";
    for (String s:cd)
      r+=s;
    return r;
  }
  private static String[] lsInput(int lineIndex){
    for (int i=lineIndex+1; i<commands.length; i++)
      if (commands[i].substring(0,1).equals("$")){
        String[] r = new String[i-lineIndex-1];
        for (int k=lineIndex+1; k<i; k++)
          r[k-lineIndex-1] = commands[k];
        return r;
      }
    return new String[0];
  }
  private void doLS(String[] ls){ // would be public, but not necessary
    children = new Day7FolderObject[ls.length];
    int childInd = 0;
    for (String obj:ls){
      if (obj.substring(0,3).equals("dir"))
        children[childInd++] = new Day7FolderObject(this, obj.substring(4));
      else{
        int fileInt = Integer.parseInt(obj.substring(0,obj.indexOf(" ")));
        files.put(obj.substring(obj.indexOf(" ")+1), fileInt);
        fileSize+=fileInt;
      }
    }
    children = Arrays.stream(children) .filter(child->(child!=null)) .toArray(Day7FolderObject[]::new);
  }
  private void calculateSize(){
    size = fileSize+dirSize;
    sizes.put(this, size);
    if (parent!=null)
      parent.upDirSize(size);
  }
  private void upDirSize(int childSize){ // would be public, but not necessary
    dirSize+=childSize;
  }
  private static void orderSizes(){
    orderedSizes = new LinkedHashMap<>();
    sizes.entrySet() .stream() .sorted(Map.Entry.comparingByValue()) .forEachOrdered(x->orderedSizes.put(x.getKey(),x.getValue()));
  }

  // Part 1 and Part 2 output methods
  public static int part1(){
    if (commands==null){
      System.out.println("Error: Day7FolderObject.run(String[] terminalOut) has not been called yet.");
      return -1;
    }
    int r = 0;
    for (int dirSize:sizes.values())
      if (dirSize<100001)
        r+=dirSize;
    return r;
  }
  public static int part2(){
    if (commands==null){
      System.out.println("Error: Day7FolderObject.run(String[] terminalOut) has not been called yet.");
      return -1;
    }
    int rootSize = sizes.get(root);
    orderSizes();
    for (int dirSize:orderedSizes.values()){
      //System.out.println(dirSize+", "+(70000000-rootSize+dirSize));////////////////////////////////
      if (70000000-rootSize+dirSize>30000000)
        return dirSize;}
    System.out.println("Error: Update too big for the capacity of this device.");
    return -1;
  }
}