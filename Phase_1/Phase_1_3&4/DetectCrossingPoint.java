import java.util.*;

public class DetectCrossingPoint{

  protected static final double EPS = 1.0e-8;

  protected static ArrayList<Axis> axis = new ArrayList<Axis>();
  protected static ArrayList<Segment> segment = new ArrayList<Segment>();
  // 変更 メソッド内の変数をメンバ変数に移動、input_x, input_yをdoubleに移動
  public static int n, m, p, q;
  private static double detA, x, y, input_x, input_y;
  private static Scanner sc = new Scanner(System.in);

  protected static String []s;
  protected static String []d;
  protected static int []k;

  public static void main(String[] args) {
    act(input());
  }
  //inputメソッド
  public static ArrayList<Segment> input(){

    //インプット部分
    n = sc.nextInt();
    m = sc.nextInt();
    p = sc.nextInt();
    q = sc.nextInt();

    //座標をn組
    // Axis [] axis = new Axis[n];

    //端点
    int [] b = new int[m];
    int [] e = new int[m];

    //線分のオブジェクトの配列をm組
    // Segment [] seg = new Segment[m];

    //点の入力
    for(int i = 0; i<n; i++){
      input_x = sc.nextDouble();
      input_y = sc.nextDouble();
      // 変更　axis[i] = new Axis(input_x, input_y);
      axis.add(new Axis(input_x, input_y));
    }

    //経路を結ぶ点の入力
    for(int i = 0; i<m; i++){
      b[i] = sc.nextInt();
      e[i] = sc.nextInt();
      //p1,q1,p2,q2を決定 実際にはp1,q1を結ぶ線分,p2,q2を結ぶ線分のインスタンスを作成
      // 変更　seg[i] = new Segment (axis[b[i]-1], axis[e[i]-1]);
      segment.add(new Segment(axis.get(b[i]-1), axis.get(e[i]-1)));
      segment.add(new Segment(axis.get(e[i]-1), axis.get(b[i]-1)));
    }

    s = new String[q];
    d = new String[q];
    k = new int[q];

    for(int i = 0; i<q; i++){
      s[i] = sc.next();
      d[i] = sc.next();
      k[i] = sc.nextInt();
    }

    return segment;
  }


  //セグメントの配列から二つの線分の交差点を返す このプログラム上では２つしか与えられないのでseg[0],seg[1]
  //継承クラスでオーバーライドする。
  public static void act(ArrayList<Segment> segment){

    Axis crossing = new Axis();
    //交差点検知のメソッド実行
    crossing = detect(segment.get(0), segment.get(1));

    //交差点があれば座標を表示なければNA
    if(crossing.x != -1 && crossing.y != -1){
      System.out.printf("%.5f %.5f\n",crossing.x,crossing.y);
    }else{
     System.out.println("NA");
    }
  }


  //二つの線分から交差点を求めるメソッド
  public static Axis detect(Segment s1, Segment s2){

    //返り値の宣言
    Axis crossing = new Axis(-1,-1);

    //行列式の計算
    double detA = (s1.q.x-s1.p.x)*(s2.p.y-s2.q.y)+(s2.q.x-s2.p.x)*(s1.q.y-s1.p.y);

    //経路が平行（行列式が0）であればNA
    if(-(EPS)<=detA && detA<=EPS){
      //System.out.println("detA = 0");
      return crossing;
    }

    //媒介変数の計算
    double s = ((s2.p.y-s2.q.y)*(s2.p.x-s1.p.x)+(s2.q.x-s2.p.x)*(s2.p.y-s1.p.y))/detA;
    double t = ((s1.p.y-s1.q.y)*(s2.p.x-s1.p.x)+(s1.q.x-s1.p.x)*(s2.p.y-s1.p.y))/detA;
    //System.out.println(s+", "+t);
    //端点は含めないため0<s<1,0<t<1
    if((0<s && s<1) && (0<t && t<1)){
      crossing.x = s1.p.x+(s1.q.x-s1.p.x)*s;
      crossing.y = s1.p.y+(s1.q.y-s1.p.y)*s;
      //System.out.printf("%.5f %.5f\n",x,y);
      return crossing;
    }else{
      //System.out.println("s,t error");
      return crossing;
    }
  }
}

//座標クラス
class Axis{
  public double x;
  public double y;

  public Axis(){
    this.x = 0;
    this.y = 0;
  }
  public Axis(double x, double y){
    this.x = x;
    this.y = y;
  }

  public double getX(){
    return this.x;
  }

  public double getY(){
    return this.y;
  }
}

//線分クラス
class Segment{
  public Axis p = new Axis(0,0);
  public Axis q = new Axis(0,0);

  public Segment(Axis p, Axis q){
    this.p = p;
    this.q = q;
  }
}
