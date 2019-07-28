import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class TestCaseGenerator{
  static Scanner sc = new Scanner(System.in);
  public static final int xy = 10+1;
  public static void main(String args[]){
    Random rand = new Random(44);
    int N = rand.nextInt(100)+2; //2<=N<=1000 #テストケースが大きくなるためここでは縮小
    int M = rand.nextInt(50)+1; //1<=M<=500
    int L  = rand.nextInt(3)+1; //Qの代わり
    int P =0;
    //int Q = rand.nextInt(101);

    int version = -1;
    boolean flag = true;
    if(args.length > 0){
      for(int i =0; i<args.length; i++){
        if(args[i].equals("-v") && flag){
          if(i+1<args.length){
            if(!Pattern.compile("^[0-9]*$").matcher(args[i+1]).matches()){
              System.out.println("オプションの後に正しいバージョンを入力してください");
              System.exit(0);
            }
            if(Integer.valueOf(args[i+1])>8){
              System.out.println("正しいバージョンを入力してください");
              System.exit(0);
            }
            version = Integer.valueOf(args[i+1]);
          }else{

            System.out.println("正しいバージョンを入力してください");
            System.exit(0);

          }
        }else{
          if(version == -1){
            System.out.println("正しいオプションを入力してください。");
            System.exit(0);
          }
        }
      }
    }else {
      System.out.println("実行したいプログラムの番号を入力してください");
      version = sc.nextInt();
    }

    switch(version){
      case 1:
      L = 0;
      P = 0;
      break;


      case 2:
      L = 0;
      P = 0;
      break;


      case 3:

      break;


      case 4:

      break;


      case 5:

      break;


      case 6:

      break;


      case 7:
      L  =0;
      P =rand.nextInt(3)+1;
      break;


      case 8:

      break;
    }

    System.out.println(N+" "+M+" "+P+" "+L);

    for(int i =0; i<N; i++){
      int x = rand.nextInt(100000);
      int y = rand.nextInt(100000);
      System.out.println(x+" "+y);
    }

    for(int i = 0; i<M; i++){
      int first = rand.nextInt(N)+1;
      int second = rand.nextInt(N)+1;
      System.out.println(first+" "+second);
    }

    for (int i =0; i<L; i++){
      int s = rand.nextInt(xy);
      int e = rand.nextInt(xy);
      int k = rand.nextInt(xy);
      int flag_s = rand.nextInt(2);
      int flag_e = rand.nextInt(2);
      if(flag_s == 1)System.out.print("C");
      System.out.print(s+" ");
      if(flag_e == 1)System.out.print("C");
      System.out.println(e+" "+k);
    }

    for(int i = 0; i<P; i++){
      int first = rand.nextInt(N)+1;
      int second = rand.nextInt(N)+1;
      System.out.println(first+" "+second);
    }

  }
}