import java.util.*;

public class DeistanceOfShortestPath extends ListCrossingPoint {

protected static ArrayList<ShortestPath> shortestPath = new ArrayList<ShortestPath>();
private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);
// private static double [][] nodeDistance;   //Inter-node distance

public static void main(String args[]){
  act(input());
  // 始点、終点、最短距離の数をshortestPathに入れる
  InputShortestPath();
  for(int i = 0; i<shortestPath.size(); i++){
    System.out.println("\n");
    System.out.println("s:"+shortestPath.get(i).s);
    System.out.println("d:"+shortestPath.get(i).d);
    System.out.println("k:"+shortestPath.get(i).k);
    OutputShortestPath(shortestPath.get(i).s, shortestPath.get(i).d, shortestPath.get(i).k);
  }

  // axisの出力
  for(int i = 0; i<axis.size(); i++){
    System.out.println("index="+(i+1)+" "+axis.get(i)+" X = "+axis.get(i).x+" y = "+axis.get(i).y);
  }

  // 線分の出力
  for(int i = 0; i<segment.size(); i++){
    System.out.println("index="+(i+1)+" "+segment.get(i).p+" "+segment.get(i).q);
  }
}

// 交点を出力しない
public static void act(ArrayList<Segment> segment){

  Axis crossing = new Axis();
  int segsize = m*(m-1)/2;

  for(int i = 0; i<segsize-1; i++){
    for(int j =i+1; j<segsize; j++){

      //交差点検知のメソッド実行, *継承クラスのメソッド
      Segment seg_i = segment.get(i);
      Segment seg_j = segment.get(j);
      crossing = detect(seg_i, seg_j);

      System.out.println("i: "+i+" j: "+j);
      //交差点があれば座標を表示なければNA
      if(crossing.x != -1 && crossing.y != -1){
        // System.out.printf("%.5f %.5f\n",crossing.x,crossing.y);

        // 交点がある線分の座標 p.x p.y q.x q.y
        System.out.println(" "+segment.get(i).p+"  "+segment.get(i).p.x+" "+segment.get(i).p.y);
        System.out.println(" "+segment.get(i).q+"  "+segment.get(i).q.x+" "+segment.get(i).q.y);
        System.out.println(" "+segment.get(j).p+"  "+segment.get(j).p.x+" "+segment.get(j).p.y);
        System.out.println(" "+segment.get(j).q+"  "+segment.get(j).q.x+" "+segment.get(j).q.y);
        System.out.println("x = "+crossing.x+" y =  "+crossing.y);

        Crossing_List.add(new Axis(crossing.x, crossing.y));

        // 交点と各点の道(線分)を追加
        axis.add(new Axis(crossing.x, crossing.y));

        // segment.remove(seg_i);
        // segment.remove(seg_j);
        // segment.add(new Segment(crossing, seg_i.p));
        // segment.add(new Segment(crossing, seg_i.q));
        // segment.add(new Segment(crossing, seg_j.p));
        // segment.add(new Segment(crossing, seg_j.q));

        count++;
      }else{
        //System.out.println("NA");
      }
    }
  }

  //Arrayを第一のソートキーにx座標,第二のソートキーにy座標を指定し、ソート後表示。
  Collections.sort(Crossing_List, new Compare());
  for(Axis c : Crossing_List) {
    // 交点を出力しない
    System.out.printf("%.5f %.5f\n",c.x,c.y);
  }
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

    if(start>=0 && end>=0 && axis.size()>=start && axis.size()>=end){
      shortestPath.add(new ShortestPath(axis.get(start), axis.get(end),  k[i]));
    }

  }
}

// 点aと点bまでの距離を返すメソッド
public static Double NodeDistance(Axis a, Axis b){
  if(segment.indexOf(new Segment(a, b)) != -1) {
    System.out.println("NodeDistance: "+Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2)));
    return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
  }
  else{
    return inf;
  }
}

// sからdまでの最短距離を出力するメソッド、kは最短距離の数（短い順）
public static void OutputShortestPath(Axis s, Axis d, int k){

  int max = axis.size();
  int u, v, start, end;
  int [] visited = new int[max];
  double min;
  // s -= 1;
  // d -= 1;
  start = axis.indexOf(s);
  end = axis.indexOf(d);

  double [] data = new double[max];
  // double [][] path = new double[max][k];
  ArrayList<ArrayList<Double>> path = new ArrayList<ArrayList<Double>>();
  for(int i = 0; i<max; i++){
    path.add(new ArrayList<Double>());
  }
  // int [] pn = new int[max];

  if(start>max || end>max) {
    System.out.println("NA");
    return;
  }

  for(int i = 0; i<max; i++) {
    visited[i]=0;
    data[i] = inf;
  }

  for(int j = 0; j<k; j++){
    data[start] = 0;
  }
  // pn[s] = -1;
  visited[start] = 1;

  while(true) {
    min = inf;
    u = -1;
    for(int i = 0; i<n; i++) {
      if(visited[i] != 2 && data[i]<min) {
        min = data[i];
        u = i;
      }
    }

    if(u == -1) {
      break;
    }

    visited[u] = 2;

    for(v = 0; v<n; v++) {
      if(visited[v]!=2 && NodeDistance(axis.get(u), axis.get(v))!=inf) {
        if(data[v]>data[u]+NodeDistance(axis.get(u), axis.get(v))) {
          data[v] = data[u] + NodeDistance(axis.get(u), axis.get(v));
          path.get(v).add(data[v]);
          Collections.sort(path.get(v));
          // pn[v] = u;
          visited[v] = 1;
        }
      }
    }
  }

  if(path.get(end).size()>0) {
    for(int j = 0; j<path.get(end).size(); j++){
      System.out.println(path.get(end).get(j));
    }
  }
  else{
    System.out.println("NA");
    return;
  }
}
}

// class Node extends Axis{
//
//   private int visited; //各地点の訪問状態の確認
//
//   public Node(){
//     super(x, y);
//     visited = 0;
//   }
//
//   public int getV(){
//     return visited;
//   }
// }

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
