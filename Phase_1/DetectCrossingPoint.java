import java.util.*;

public class DetectCrossingPoint{
  private static final double EPS = 1.0e-8;

  public static void main(String[] args) {

    int n, m, p, q, i, xp, xq, yp, yq;
    double detA, x, y;
    Scanner sc = new Scanner(System.in);

    n = sc.nextInt();
    m = sc.nextInt();
    p = sc.nextInt();
    q = sc.nextInt();

    int [][] axis = new int[n][2];

    //p1,q1,p2,q2
    int [][] axis4P = new int[n][2];
    int [] b = new int[m];
    int [] e = new int[m];

    //点の入力
    for(i = 0; i<n; i++){
      axis[i][0] = sc.nextInt();
      axis[i][1] = sc.nextInt();
    }

    //経路を結ぶ点の入力
    for(i = 0; i<m; i++){
      b[i] = sc.nextInt();
      e[i] = sc.nextInt();
    }

    //p1,q1,p2,q2を決定
    for(i = 0; i<m; i++){
      axis4P[2*i][0] = axis[b[i]-1][0];
      axis4P[2*i][1] = axis[b[i]-1][1];
      axis4P[2*i+1][0] = axis[e[i]-1][0];
      axis4P[2*i+1][1] = axis[e[i]-1][1];
    }

    // //４つの座標のテスト
    // for(i = 0; i<4; i++){
    //   System.out.println(axis4P[i][0]+""+axis4P[i][1]);
    // }

    //行列式の計算
    detA = (axis4P[1][0]-axis4P[0][0])*(axis4P[2][1]-axis4P[3][1])+(axis4P[3][0]-axis4P[2][0])*(axis4P[1][1]-axis4P[0][1]);

    //経路が平行（行列式が0）であればNA
    if(-(EPS)<=detA && detA<=EPS){
      System.out.println("NA");
      return;
    }

    //媒介変数の計算
    double s = ((axis4P[2][1]-axis4P[3][1])*(axis4P[2][0]-axis4P[0][0])+(axis4P[3][0]-axis4P[2][0])*(axis4P[2][1]-axis4P[0][1]))/detA;
    double t = ((axis4P[0][1]-axis4P[1][1])*(axis4P[2][0]-axis4P[0][0])+(axis4P[1][0]-axis4P[0][0])*(axis4P[2][1]-axis4P[0][1]))/detA;

    //端点は含めないため0<s<1,0<t<1
    if((0<s && s<1) && (0<t && t<1)){
      x = axis4P[0][0]+(axis4P[1][0]-axis4P[0][0])*s;
      y = axis4P[0][1]+(axis4P[1][1]-axis4P[0][1])*s;
      System.out.printf("%.5f %.5f\n",x,y);
    }else{
      System.out.println("NA");
    }

    //入力できているかのテスト
    //System.out.println(detA+ " " + s+ " " + t);
    // for(i = 0; i<axis.length; i++){
    //   for(int j = 0; j<axis[0].length; j++){
    //     if(j==1)System.out.print(" ");
    //     System.out.print(axis[i][j]);
    //   }
    //   System.out.println();
    // }
    //
    // for(i = 0; i<b.length; i++){
    //   System.out.println(b[i]+" "+e[i]);
    // }
  }
}