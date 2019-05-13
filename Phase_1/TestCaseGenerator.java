import java.util.*;

class TestCaseGenerator{
  public static final int xy = 10+1;
  public static void main(String args[]){
    Random rand = new Random(1250029);
    int N = rand.nextInt(10)+2; //2<=N<=1000
    int M = rand.nextInt(5)+1; //1<=M<=500
    int L  = rand.nextInt(3)+1; //Qの代わり
    int P =0;
    //int Q = rand.nextInt(101);
    System.out.println(N+" "+M+" "+P+" "+L);

    for(int i =0; i<N; i++){
      int x = rand.nextInt(xy);
      int y = rand.nextInt(xy);
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
    
  }
}