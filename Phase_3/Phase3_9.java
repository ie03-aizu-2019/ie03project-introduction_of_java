import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Phase3_9{
  public static void main(String args[]){

    Scanner sc = new Scanner(System.in);
    boolean defo = false;
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
        }else if(args[i].equals("-d")){
          defo = true;
        }else if(args[i].equals("-a")){
          version = 9;
          flag = false;
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
    if(version == -1)version = 9;

    if(defo)System.out.println("デフォルトのデータファイルで実行します");
    else System.out.println("各versionに合わせて入力してください");
    switch(version){
      case 1:
      Phase1_1 obj1 = new Phase1_1();
      System.out.println("Phase1_"+version+"を実行します。");
      obj1.run(defo);
      break;


      case 2:
      Phase1_2 obj2 = new Phase1_2();
      System.out.println("Phase1_"+version+"を実行します。");
      obj2.run(defo);
      break;


      case 3:
      Phase1_3 obj3 = new Phase1_3();
      System.out.println("Phase1_"+version+"を実行します。");
      obj3.run(defo);
      break;


      case 4:
      Phase1_4 obj4 = new Phase1_4();
      System.out.println("Phase1_"+version+"を実行します。");
      obj4.run(defo);
      break;


      case 5:
      Phase2_5 obj5 = new Phase2_5();
      System.out.println("Phase2_"+version+"を実行します。");
      obj5.run(defo);
      break;


      case 6:
      Phase2_6 obj6 = new Phase2_6();
      System.out.println("Phase2_"+version+"を実行します。");
      obj6.run(defo);
      break;


      case 7:
      Phase2_7 obj7 = new Phase2_7();
      System.out.println("Phase2_"+version+"を実行します。");
      obj7.run(defo);
      break;


      case 8:
      Phase2_8 obj8 = new Phase2_8();
      System.out.println("Phase2_"+version+"を実行します。");
      obj8.run(defo);
      break;

      case 9:
      System.out.println("プログラムを全て実行します");
      Phase1_1 obj11 = new Phase1_1();
      System.out.println("\nPhase1_"+1+"を実行します。");
      obj11.run(defo);

      Phase1_2 obj22 = new Phase1_2();
      System.out.println("\nPhase1_"+2+"を実行します。");
      obj22.run(defo);

      Phase1_3 obj33 = new Phase1_3();
      System.out.println("\nPhase1_"+3+"を実行します。");
      obj33.run(defo);

      Phase1_4 obj44 = new Phase1_4();
      System.out.println("\nPhase1_"+4+"を実行します。");
      obj44.run(defo);

      Phase2_5 obj55 = new Phase2_5();
      System.out.println("\nPhase2_"+5+"を実行します。");
      obj55.run(defo);

      Phase2_6 obj66 = new Phase2_6();
      System.out.println("\nPhase2_"+6+"を実行します。");
      obj66.run(defo);

      Phase2_7 obj77 = new Phase2_7();
      System.out.println("\nPhase2_"+7+"を実行します。");
      obj77.run(defo);

      Phase2_8 obj88 = new Phase2_8();
      System.out.println("\nPhase2_"+8+"を実行します。");
      obj88.run(defo);

      break;
    }
  }
}