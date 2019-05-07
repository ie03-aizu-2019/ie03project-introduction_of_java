import java.util.*;

class TestCaseGenerator{
  public static final int xy = 10+1;
  public static void main(String args[]){
    Random rand = new Random(1250029);
    int N = rand.nextInt(10)+2; //2<=N<=1000
    int M = rand.nextInt(10)+1; //1<=N<=500
    int P =0;
    int Q = rand.nextInt(101);
    System.out.println(N+" "+M+" "+P+" "+Q);
    for(int i =0; i<N; i++){
      int x = rand.nextInt(xy);
      int y = rand.nextInt(xy);
      System.out.println(x+" "+y);
    }
    for(int i = 0; i<M; i++);
     int first = rand.nextInt(N)+1;
     int second = rand.nextInt(N)+1;
    System.out.println(first+" "+second);
  }
}