import java.util.*;
import java.math.*;

public class Bridge extends ListCrossingPoint {
  public static StringBuilder bl = new StringBuilder();
  public static final int INFTY = (int)Double.POSITIVE_INFINITY;

  protected static ArrayList<Path> Path = new ArrayList<Path>();
  private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);

  //bridge探索のための宣言
  static int[] parent, prenum, lowest;
  static int timer=0;
  static boolean[] visited;
  static int V;
  static boolean[][] G;
  static PriorityQueue<State> pq=new PriorityQueue<>();

  public static void main(String args[]){
    act(input());
    // 始点、終点、最短距離の数をPathに入れる
    InputPath();
    divide();

    for(int i = 0; i<Path.size(); i++){

      OutBridge(Path.get(i).s, Path.get(i).d);
    }

  }

  // 交点を出力しない
  public static void act(ArrayList<Segment> segment){

    Axis crossing = new Axis();

    for(int i = 0; i<segment.size()-1; i++){
      for(int j =i+1; j<segment.size(); j++){

        //交差点検知のメソッド実行, *継承クラスのメソッド
        Segment seg_i = segment.get(i);
        Segment seg_j = segment.get(j);
        crossing = detect(seg_i, seg_j);

        //交差点があれば座標を表示なければNA
        if(crossing.x != -1 && crossing.y != -1){
          // System.out.printf("%.5f %.5f\n",crossing.x,crossing.y);
          Crossing_List.add(new Axis(crossing.x, crossing.y));
          count++;
        }else{
          //System.out.println("NA");
        }
      }
    }

    //Arrayを第一のソートキーにx座標,第二のソートキーにy座標を指定し、ソート後表示。
    Collections.sort(Crossing_List, new Compare());
    for(Axis c : Crossing_List) {
      if(axisExist(c, axis)==false){
        axis.add(new Axis(c.x, c.y));
        // System.out.printf("%.5f %.5f\n",c.x,c.y);
      }
    }
  }



  // 線分を分割するためのメソッド
  public static void divide(){

    // 線分上の交差点を入れる為のArrayList // c.get(i)にi番目の線分の交差点のArrayListが入る
    ArrayList<ArrayList<Axis>> c = new ArrayList<ArrayList<Axis>>();

    // cを空のArrayListで初期化
    for(int i = 0; i<1000; i++){
      c.add(new ArrayList<Axis>());
    }

    // 元々の線分のサイズを定数にする
    final int segsize = segment.size();
    // segment[i]上に交差点があるかどうかの判定用
    int [] flagc = new int[segsize];

    for(int i = 0; i<segsize; i++){
      // 線分
      Segment seg = segment.get(i);

      // 現在の線分の情報と地点を出力
      // System.out.println("p: x=" + seg.p.x+" y=" + seg.p.y+" q: x=" + seg.q.x+" y=" + seg.q.y);
      // for(Axis d : axis) {
      //   System.out.printf("%.5f %.5f\n",d.x,d.y);
      // }

      // 線分上に交差点があるか探す
      for(int j = 0; j<axis.size(); j++){
        // System.out.println("i: "+i+" j: "+j+" flag: "+flagc[i]);

        Axis crossing = axis.get(j);
        double fx;

        fx = setDigit((seg.q.y-seg.p.y)*(crossing.x-seg.p.x)/(seg.q.x-seg.p.x)+seg.p.y,2);
        if((setDigit(crossing.y,2) == fx)&&(setDigit(seg.p.y,2)!=fx)&&(setDigit(seg.q.y,2)!=fx)){
          if(axisExist(crossing, c.get(i)) == false){
            // 交差点があったらflagcを1にして交差点情報を保存
            flagc[i] = 1;
            c.get(i).add(crossing);
          }
        }

        fx = setDigit((seg.p.y-seg.q.y)*(crossing.x-seg.q.x)/(seg.p.x-seg.q.x)+seg.q.y,2);
        if((setDigit(crossing.y,2) == fx)&&(setDigit(seg.p.y,2)!=fx)&&(setDigit(seg.q.y,2)!=fx)){
          if(axisExist(crossing, c.get(i)) == false){
            // 交差点があったらflagcを1にして交差点情報を保存
            flagc[i] = 1;
            c.get(i).add(crossing);
          }
        }

      }

    }

    // System.out.println("-----線分上に交差点がある場合は、交差点と各点で分割したsegmentを入れる-----");
    for(int i = 0; i<segsize; i++){

      Segment seg = segment.get(i);
      // flagc=1(線分上に交差点がある)の場合
      if(flagc[i]==1){
        // System.out.println("\ni: "+i+" flag: "+flagc[i]);

        // segment[i]上にある交差点のリスト
        ArrayList<Axis> Crossinglist = c.get(i);
        // リストをソート
        Collections.sort(Crossinglist, new Compare());

        // 線分の二つの端点p,qの座標がp>qだった場合Crossinglistを逆順にする（線分を分割する際、座標が小さい順から分割していくため）
        if(seg.q.x<seg.p.x || (seg.q.x==seg.p.x&&seg.q.y<seg.p.y)){
          Collections.reverse(Crossinglist);
          // System.out.println("reverse");
        }


        // 交差点を基準に分割してできる線分が既に存在していなかったら追加 (segmentExistで判定)
        if(segmentExist(new Segment(seg.p, Crossinglist.get(0)))==false){
          segment.add(new Segment(seg.p, Crossinglist.get(0)));

        }
        if(segmentExist(new Segment(Crossinglist.get(0), seg.p))==false){
          segment.add(new Segment(Crossinglist.get(0), seg.p));

        }
        if(segmentExist(new Segment(seg.q, Crossinglist.get(Crossinglist.size()-1)))==false){
          segment.add(new Segment(seg.q, Crossinglist.get(Crossinglist.size()-1)));

        }
        if(segmentExist(new Segment(Crossinglist.get(Crossinglist.size()-1), seg.q))==false){
          segment.add(new Segment(Crossinglist.get(Crossinglist.size()-1), seg.q));

        }
        for(int k = 0; k<Crossinglist.size()-1; k++){
          if(segmentExist(new Segment(Crossinglist.get(k), Crossinglist.get(k+1)))==false){
            segment.add(new Segment(Crossinglist.get(k), Crossinglist.get(k+1)));

          }
          if(segmentExist(new Segment(Crossinglist.get(k+1), Crossinglist.get(k)))==false){
            segment.add(new Segment(Crossinglist.get(k+1), Crossinglist.get(k)));

          }
        }

      }
    }

    // 交差点を基準に線分を分割すると、その線分は道では無くなるから線分のArrayListから削除する
    int i = 0;
    int j = 0;
    int size = segsize;
    while(i<size && j<size){
      if(flagc[j]==1){
        Segment seg = segment.get(i);
        // System.out.println("flagc["+j+"]="+flagc[j]+" i = "+i+" Removed p: x=" + seg.p.x+" y=" + seg.p.y+" q: x=" + seg.q.x+" y=" + seg.q.y);
        segment.remove(i);
      }
      else{
        i++;
        size--;
      }
      j++;
    }
  }
  // divide()終わり



  // axis(地点)がリストに存在するか判定するメソッド
  public static Boolean axisExist(Axis b, ArrayList<Axis> axis){
    int flag = 0;

    for(int i = 0; i<axis.size(); i++){
      double x = setDigit(axis.get(i).x, 5);
      double y = setDigit(axis.get(i).y, 5);
      b.x = setDigit(b.x, 5);
      b.y = setDigit(b.y, 5);

      if(x==b.x && y==b.y){
        flag = 1;
      }
    }

    if(flag == 1){
      return true;
    }else{
      return false;
    }
  }

  // segment(線分)がリストに存在するか判定するメソッド
  public static Boolean segmentExist(Segment t){
    int flag = 0;

    for(int i = 0; i<segment.size(); i++){

      Segment s = segment.get(i);

      if((setDigit(s.p.x,5)==setDigit(t.p.x,5))&&(setDigit(s.q.x,5)==setDigit(t.q.x,5))&&(setDigit(s.p.y,5)==setDigit(t.p.y,5))&&(setDigit(s.q.y,5)==setDigit(t.q.y,5))){
        flag = 1;
      }
    }

    if(flag == 1){
      return true;
    }else{
      return false;
    }
  }

  // 小数点以下n桁で変数を返す
  public static double setDigit(double x, int n){
    return (double)((int)Math.round((x*Math.pow(10, n))))/Math.pow(10, n);
  }

  // 始点、終点それぞれ0,最後の点としinput
  public static void InputPath(){

    int start, end;
    start = 0;
    end = axis.size()-1;
    if(start>=0 && end>=0 && axis.size()>=start && axis.size()>=end){
      Path.add(new Path(axis.get(start), axis.get(end)));
    }
    else{
      Path.add(new Path(new Axis(inf, inf), new Axis(inf, inf)));
    }
  }

  // Bridgeを見つける
  public static void OutBridge(Axis s, Axis d){


    int graph_size = axis.size();
    int start, end;
    //int count =0;

    // s.x=infだったら終了（inputPath()でaxisの範囲を超えていたらinfを代入している //ここ ）
    if(s.x == inf){
      System.out.println("NA");
      return;
    }
    else{
      start = axis.indexOf(s);
      end = axis.indexOf(d);
    }

    //重み付きグラフ 隣接リスト
    ArrayList<ArrayList<Pair>> adj = new ArrayList<ArrayList<Pair>>();
    for(int i = 0; i<graph_size; i++){
      adj.add(new ArrayList<Pair>());
    }

    //静的宣言してるものに代入
    V=graph_size;
    parent=new int[V];
    prenum=new int[V];
    lowest=new int[V];
    visited=new boolean[V];
    G=new boolean[V][V];

    for(int i = 0; i<graph_size; i++){
      for(int j = 0; j<graph_size; j++){
        if(NodeDistance(axis.get(i), axis.get(j))!=inf){
          adj.get(i).add(new Pair(j, NodeDistance(axis.get(i), axis.get(j))));
          //count++;
          //System.out.println(i+" "+j);
          G[i][j]=G[j][i]=true;
        }
      }
    }
    //System.out.println(graph_size+" "+count);
    art_point();
    
    /////////////////隣接リストprint test///////////////////
    // for(int i =0; i<graph_size; i++){
    //   System.out.print(i+": ");
    //   for(Pair pair : adj.get(i)){
    //     System.out.print("("+(int)setDigit(pair.v,5)+","+setDigit(pair.c,5)+") ");//実際は-1
    //   }
    //   System.out.println();
    // }
    ///////////////////////////////////////////////////////

  } // end of OutBridge()

  //深さ優先探索
  static void dfs(int current, int prev) {
    prenum[current]=lowest[current]=timer;
    timer++;
    visited[current]=true;
    int next=0;
    for(int i=0; i<V; i++) {
      if(G[current][i]) {
        next=i;
        if(!visited[next]) {
          parent[next]=current;
          dfs(next, current);
          lowest[current]=Math.min(lowest[current], lowest[next]);
        }
        else if(next != prev) {//currentからnextがbackedgeの時
          lowest[current]=Math.min(lowest[current], prenum[next]);
        }
      }
    }
  }

  static void art_point() {
    for(int i=0; i<V; i++) {
      visited[i]=false;
    }
    timer=1;
    dfs(0, -1);

    for(int i=1; i<V; i++) {
      int p=parent[i];
      if(prenum[p]<lowest[i]) {
        pq.add(new State(Math.min(p, i), Math.max(p, i)));
      }
    }

    System.out.println("幹線道路 :");
    while(! pq.isEmpty()) {
      State p=pq.remove();
      System.out.println(p.s+"-"+p.t);
    }

  }

  // 点aと点bまでの距離を返すメソッド
  public static Double NodeDistance(Axis a, Axis b){

    if(segmentExist(new Segment(a, b)) == true) { //繋がっている時には値を返して繋がっていなければinfを返す。

      return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }
    else{
      return inf;
    }
  }

  //現在の状況比較のためのクラス内クラス
  static class State implements Comparable<State>{
    int s, t;
    State(int s, int t){
      this.s=s;
      this.t=t;
    }
    public int compareTo(State p) {
      if(p.s==this.s) {
        return this.t-p.t;
      }
      return this.s-p.s;
    }
  }

} // end of Class Bridge


// 始点と終点と最短距離の数
class Path{
  public Axis s = new Axis(0,0);
  public Axis d = new Axis(0,0);

  public Path(Axis s, Axis d){
    this.s = s;
    this.d = d;
  }
}

class Pair implements Comparable<Pair> {
  int v;
  double c;
  int parent;
  public Pair(int v, double c) {
    super();
    this.v = v;
    this.c = c;
  }
  public Pair(int parent, int v, double c){
    super();
    this.parent = parent;
    this.v = v;
    this.c = c;
  }
  public int compareTo(Pair o) {
    return (int)this.c - (int)o.c;
  }
}
