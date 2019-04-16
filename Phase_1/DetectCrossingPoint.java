import java.util.*;

public class DetectCrossingPoint{
  private static final double EPS = 1.0e-8;

  public static void main(String[] args) {

    int n, m, p, q, i, input_x, input_y;
    double detA, x, y;
    Scanner sc = new Scanner(System.in);

    n = sc.nextInt();
    m = sc.nextInt();
    p = sc.nextInt();
    q = sc.nextInt();

    Axis [] axis = new Axis[n];

    //p1,q1,p2,q2
    Axis [] axis4P = new Axis[n];
    int [] b = new int[m];
    int [] e = new int[m];

    //点の入力
    for(i = 0; i<n; i++){
      // axis[i][0 = sc.nextInt();
      // axis[i].y = sc.nextInt();
      input_x = sc.nextInt();
      input_y = sc.nextInt();
      axis[i] = new Axis(input_x, input_y);
    }

    //経路を結ぶ点の入力
    for(i = 0; i<m; i++){
      b[i] = sc.nextInt();
      e[i] = sc.nextInt();
    }

    //p1,q1,p2,q2を決定
    for(i = 0; i<m; i++){
      axis4P[2*i] = axis[b[i]-1];
      axis4P[2*i+1] = axis[e[i]-1];
    }

    // //４つの座標のテスト
    // for(i = 0; i<4; i++){
    //   System.out.println(axis4P[i].x+""+axis4P[i].y);
    // }

    //行列式の計算
    detA = (axis4P[1].x-axis4P[0].x)*(axis4P[2].y-axis4P[3].y)+(axis4P[3].x-axis4P[2].x)*(axis4P[1].y-axis4P[0].y);

    //経路が平行（行列式が0）であればNA
    if(-(EPS)<=detA && detA<=EPS){
      System.out.println("NA");
      return;
    }

    //媒介変数の計算
    double s = ((axis4P[2].y-axis4P[3].y)*(axis4P[2].x-axis4P[0].x)+(axis4P[3].x-axis4P[2].x)*(axis4P[2].y-axis4P[0].y))/detA;
    double t = ((axis4P[0].y-axis4P[1].y)*(axis4P[2].x-axis4P[0].x)+(axis4P[1].x-axis4P[0].x)*(axis4P[2].y-axis4P[0].y))/detA;

    //端点は含めないため0<s<1,0<t<1
    if((0<s && s<1) && (0<t && t<1)){
      x = axis4P[0].x+(axis4P[1].x-axis4P[0].x)*s;
      y = axis4P[0].y+(axis4P[1].y-axis4P[0].y)*s;
      System.out.printf("%.5f %.5f\n",x,y);
    }else{
      System.out.println("NA");
    }
  }
}

class Axis{
  public int x;
  public int y;

  public Axis(int x, int y){
    this.x = x;
    this.y = y;
  }
}