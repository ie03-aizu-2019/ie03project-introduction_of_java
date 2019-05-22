import java.util.*;
import java.math.*;

public class DistanceOfShortestPath extends ListCrossingPoint {

protected static ArrayList<ShortestPath> shortestPath = new ArrayList<ShortestPath>();
private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);
// private static double [][] nodeDistance;   //Inter-node distance

public static void main(String args[]){
  act(input());
  // 始点、終点、最短距離の数をshortestPathに入れる
  InputShortestPath();
  divide();

  for(int i = 0; i<shortestPath.size(); i++){
    System.out.println("s: "+axis.indexOf(shortestPath.get(i).s)+" "+shortestPath.get(i).s+" x=" + shortestPath.get(i).s.x+" y=" + shortestPath.get(i).s.y);
    System.out.println("d: "+axis.indexOf(shortestPath.get(i).d)+" "+shortestPath.get(i).d+" x=" + shortestPath.get(i).d.x+" y=" + shortestPath.get(i).d.y);
    System.out.println("k: "+shortestPath.get(i).k);
    OutputShortestPath(shortestPath.get(i).s, shortestPath.get(i).d, shortestPath.get(i).k);
    System.out.println("\n");
  }
  // axisの出力
  //   System.out.println("\naxis");
  // for(int i = 0; i<axis.size(); i++){
  //   System.out.println("index="+(i+1)+" "+axis.get(i)+" x = "+axis.get(i).x+" y = "+axis.get(i).y);
  // }

  // 線分の出力
  // for(int i = 0; i<segment.size(); i++){
  //   System.out.println("\nindex="+(i+1));
  //   System.out.println(segment.get(i).p+" x="+segment.get(i).p.x+" y="+segment.get(i).p.y);
  //   System.out.println(segment.get(i).q+" x="+segment.get(i).q.x+" y="+segment.get(i).q.y);
  // }
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

  // 線分上の交差点を入れる為のArrayList c.get(i)にi番目の線分の交差点のArrayListが入る
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
            // System.out.println("-----find crossing-----");
            // System.out.println("Crossinglist: x=" + crossing.x+" y="+crossing.y);
          }
        }

        fx = setDigit((seg.p.y-seg.q.y)*(crossing.x-seg.q.x)/(seg.p.x-seg.q.x)+seg.q.y,2);
        if((setDigit(crossing.y,2) == fx)&&(setDigit(seg.p.y,2)!=fx)&&(setDigit(seg.q.y,2)!=fx)){
          if(axisExist(crossing, c.get(i)) == false){
            // 交差点があったらflagcを1にして交差点情報を保存
            flagc[i] = 1;
            c.get(i).add(crossing);
            // System.out.println("-----find crossing-----");
            // System.out.println("Crossinglist: x=" + crossing.x+" y="+crossing.y);
          }
        }
        // System.out.println("\n");
    }
    // System.out.println("\n");
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

      // System.out.println("p: x=" + seg.p.x+" y=" + seg.p.y+" q: x=" + seg.q.x+" y=" + seg.q.y);
      // for(Axis d : Crossinglist) {
      //   System.out.printf("%f %f\n",d.x,d.y);
      // }

      // 交差点を基準に分割してできる線分が既に存在していなかったら追加 (segmentExistで判定)
      if(segmentExist(new Segment(seg.p, Crossinglist.get(0)))==false){
        segment.add(new Segment(seg.p, Crossinglist.get(0)));
        // System.out.println("Add: "+"p: x=" + seg.p.x+" y=" + seg.p.y+" start: x=" + Crossinglist.get(0).x+" y=" + Crossinglist.get(0).y);
      }
      if(segmentExist(new Segment(Crossinglist.get(0), seg.p))==false){
        segment.add(new Segment(Crossinglist.get(0), seg.p));
        // System.out.println("Add: "+"start: x=" + Crossinglist.get(0).x+" y=" + Crossinglist.get(0).y+" p: x=" + seg.p.x+" y=" + seg.p.y);
      }
      if(segmentExist(new Segment(seg.q, Crossinglist.get(Crossinglist.size()-1)))==false){
        segment.add(new Segment(seg.q, Crossinglist.get(Crossinglist.size()-1)));
        // System.out.println("Add: "+"q: x=" + seg.q.x+" y=" + seg.q.y+" end: x=" + Crossinglist.get(Crossinglist.size()-1).x+" y=" + Crossinglist.get(Crossinglist.size()-1).y);
      }
      if(segmentExist(new Segment(Crossinglist.get(Crossinglist.size()-1), seg.q))==false){
        segment.add(new Segment(Crossinglist.get(Crossinglist.size()-1), seg.q));
        // System.out.println("Add: "+"end: x=" + Crossinglist.get(Crossinglist.size()-1).x+" y=" + seg.q.y+" q: x=" + seg.p.x+" y=" + seg.q.y);
      }
      for(int k = 0; k<Crossinglist.size()-1; k++){
        if(segmentExist(new Segment(Crossinglist.get(k), Crossinglist.get(k+1)))==false){
          segment.add(new Segment(Crossinglist.get(k), Crossinglist.get(k+1)));
          // System.out.println("Add: "+"k: x=" + Crossinglist.get(k).x+" y=" + Crossinglist.get(k).y+" k+1: x=" + Crossinglist.get(k+1).x+" y=" + Crossinglist.get(k+1).y);
        }
        if(segmentExist(new Segment(Crossinglist.get(k+1), Crossinglist.get(k)))==false){
          segment.add(new Segment(Crossinglist.get(k+1), Crossinglist.get(k)));
          // System.out.println("Add: "+"k+1: x=" + Crossinglist.get(k+1).x+" y=" + Crossinglist.get(k+1).y+" k: x=" + Crossinglist.get(k).x+" y=" + Crossinglist.get(k).y);
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
  return (double)((int)(x*Math.pow(10, n)))/Math.pow(10, n);
}

// 始点、終点、最短距離の数をshortestPathに入れる
public static void InputShortestPath(){

  for(int i = 0; i<q; i++){

    int start, end;

    if(s[i].charAt(0)=='C'){
      s[i] = s[i].substring(1);
      start = Integer.valueOf(s[i])-1+n;
    }
    else{
      start = Integer.valueOf(s[i])-1;
    }

    if(d[i].charAt(0)=='C'){
      d[i] = d[i].substring(1);
      end = Integer.valueOf(d[i])-1+n;
    }
    else{
      end = Integer.valueOf(d[i])-1;
    }

    // System.out.println("axis.size()"+axis.size());
    // System.out.println("start"+start);
    // System.out.println("end"+end);
    // System.out.println((start>=0 && end>=0 && axis.size()>=start && axis.size()>=end)+"\n");

    //ここ
    if(start>=0 && end>=0 && axis.size()>=start && axis.size()>=end){
      shortestPath.add(new ShortestPath(axis.get(start), axis.get(end),  k[i]));
    }
    else{
      shortestPath.add(new ShortestPath(new Axis(inf, inf), new Axis(inf, inf),  k[i]));
    }
  }
}

private static int max = axis.size();
private static int u, v, start, end, flagp;
private static double min;

// 各点の訪問状態 0:訪問していない 1:訪問済　2:訪問中
private static int [] visited;
// startから各点までの最短距離を保持
private static double [] data;
// startから各点までの最短距離を小さい順からk個表示するためのリスト
private static ArrayList<ArrayList<Double>> path = new ArrayList<ArrayList<Double>>();

// sからdまでの最短距離を出力するメソッド、kは最短距離の数（短い順） ダイクストラ法
public static void OutputShortestPath(Axis s, Axis d, int k){
  max = axis.size();
  visited = new int[max];
  data = new double[max];

  for(int i = 0; i<max; i++){
    path.add(new ArrayList<Double>());
  }
  // int [] pn = new int[max];

  // s.x=infだったら終了（inputShortestPath()でaxisの範囲を超えていたらinfを代入している //ここ ）
  if(s.x == inf){
    System.out.println("NA");
    return;
  }
  else{
    start = axis.indexOf(s);
    end = axis.indexOf(d);
  }

  // 各点の訪問状態を0で初期化、各点までの最短距離をinfで初期化
  for(int i = 0; i<max; i++) {
    visited[i] = 0;
    data[i] = inf;
  }
  // startからstartまでの距離は0、startの訪問状態を訪問済にする
  data[start] = 0;
  visited[start] = 1;

  flagp = 0;
  System.out.print((start+1));
  depthFirstSearch(start, end);

  // pn[s] = -1;
  // while(true) {
  //   // min = inf;
  //   u = -1;
  //   for(int i = 0; i<max; i++) {
  //     // if(visited[i] != 2 && data[i]<min) {
  //     if(visited[i] == 1) {
  //       // min = data[i];
  //       u = i;
  //       System.out.println("u="+(u+1));
  //       break;
  //     }
  //   }
  //
  //   if(u == -1) {
  //     break;
  //   }
  //
  //   visited[u] = 2;
  //
  //   if(u == start){
  //     for(v = 0; v<max; v++) {
  //       if(NodeDistance(axis.get(u), axis.get(v))!=inf){
  //         path.get(v).add(NodeDistance(axis.get(u), axis.get(v)));
  //         System.out.println("d="+(v+1)+" "+NodeDistance(axis.get(u), axis.get(v)));
  //         visited[v]=1;
  //         // System.out.println("visited["+(v+1)+"] = 1");
  //       }
  //     }
  //   }
  //   else{
  //     for(v = 0; v<max; v++) {
  //       if(NodeDistance(axis.get(u), axis.get(v))!=inf){
  //         for(int i=0; i<path.get(u).size(); i++){
  //           double s_to_v = path.get(u).get(i) + NodeDistance(axis.get(u),   axis.get(v));
  //
  //           flagp = 0;
  //           // 最短距離を入れるリストに重複する値があったら flagp=1
  //           for(int j=0; j<path.get(v).size(); j++){
  //             if(path.get(v).get(j)==s_to_v){
  //               flagp = 1;
  //             }
  //           }
  //           if(flagp==0){
  //             path.get(v).add(s_to_v);
  //             System.out.println("d="+(v+1)+" "+s_to_v);
  //             visited[v]=1;
  //           }
  //         }
  //       }
  //     }
  //   }
  // }
  // 結果の出力
  // if(path.get(end).size()<k){
  //   k = path.get(end).size();
  // }
  // if(path.get(end).size()>0) {
  //   Collections.sort(path.get(end));
  //   for(int j = 0; j<k; j++){
  //       if(path.get(end).get(j)!=inf){
  //         System.out.printf("%.5f\n",path.get(end).get(j));
  //       }
  //   }
  //   return;
  // }
  // else{
  //   System.out.println("NA");
  //   return;
  // }
} // end of outputShortestPath()

private static void depthFirstSearch(int u, int end){
  ArrayList<Integer> next = new ArrayList<Integer>();
  visited[u]=1;
  flagp++;
  // System.out.print("[分岐"+flagp+"]");
  for(v = 0; v<max; v++) {
    if((NodeDistance(axis.get(u), axis.get(v))!=inf) && (v == end)){
      System.out.print(" -> "+(end+1)+"\n");
      break;
    }
    if((NodeDistance(axis.get(u), axis.get(v))!=inf) && (visited[v]!=1)){
      next.add(v);
    }
  }
  if(v != end){
    // System.out.println(next)
    for(int i = 0; i<next.size(); i++) {
      System.out.print(" -> "+(next.get(i)+1));
      depthFirstSearch(next.get(i), end);
    }
  }
  visited[u]=0;
}

// 点aと点bまでの距離を返すメソッド
public static Double NodeDistance(Axis a, Axis b){
  // System.out.println(segment.indexOf(new Segment(a, b)));
  // System.out.println("a: "+a+"  b: "+b+"\n");
  // for(int i = 0; i<segment.size(); i++){
  //   System.out.println(segment.get(i).p+" "+segment.get(i).q);
  // }
  if(segmentExist(new Segment(a, b)) == true) {
    // System.out.println("NodeDistance: "+Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2)));
    return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
  }
  else{
    return inf;
  }
}
} // end of Class DistanceOfShortestPath

// 始点と終点と最短距離の数
class ShortestPath{
  public Axis s = new Axis(0,0);
  public Axis d = new Axis(0,0);
  public int k;

  public ShortestPath(Axis s, Axis d, int k){
    this.s = s;
    this.d = d;
    this.k = k;
  }
}

  //segmentからツリー構造を作るためのクラス
  class BinaryTree{
    BinaryTree left; //２分木の左の子ノード
    BinaryTree right; //右の子ノード
    int data; //このノードのデータ

    //新たなノードを生成する
    public BinaryTree(int data, BinaryTree left, BinaryTree right) {
      this.data = data;
      this.left = left;
      this.right = right;
    }
  }
