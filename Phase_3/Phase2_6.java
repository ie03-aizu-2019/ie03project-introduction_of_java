import java.util.*;
import java.math.*;
import java.io.*;
public class Phase2_6 extends Phase1_2 {
  public static StringBuilder bl = new StringBuilder();
  public static final int INFTY = (int)Double.POSITIVE_INFINITY;
  public static final double  inf = Double.POSITIVE_INFINITY;
  protected static int[] saveShotestPath;

  protected static ArrayList<ShortestPath> shortestPath = new ArrayList<ShortestPath>();
  // private static double [][] nodeDistance;   //Inter-node distance

  public static void run(boolean defo){
    act(input(defo, 6));
    // 始点、終点、最短距離の数をshortestPathに入れる
    InputShortestPath();
    divide();

    for(int i = 0; i<shortestPath.size(); i++){

      Output(shortestPath.get(i).s, shortestPath.get(i).d, shortestPath.get(i).k);
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


      if(start>=0 && end>=0 && axis.size()>=start && axis.size()>=end){
        shortestPath.add(new ShortestPath(axis.get(start), axis.get(end),  k[i]));
      }
      else{
        shortestPath.add(new ShortestPath(new Axis(inf, inf), new Axis(inf, inf),  k[i]));
      }
    }
  }

  // sからdまでの最短距離を出力するメソッド、kは最短距離の数（短い順） ダイクストラ法
  public static void Output(Axis s, Axis d, long k){
    int start;
    int end;
    int max = axis.size();
    double matrix [][] = new double [max][max];//隣接行列
    //初期化
    for(int i =0; i<max; i++){
      for(int j =0; j<max; j++){
        matrix[i][j] = inf;
      }
    }
    // s.x=infだったら終了（inputShortestPath()でaxisの範囲を超えていたらinfを代入している //ここ ）
    if(s.x == inf){
      System.out.println("NA");
      return;
    }
    else{
      start = axis.indexOf(s);
      end = axis.indexOf(d);
    }

    //隣接行列に代入
    for(int i = 0; i<max; i++){
      for(int j = 0; j<max; j++){
        if(NodeDistance(axis.get(i), axis.get(j))!=inf){
          matrix[i][j] = NodeDistance(axis.get(i), axis.get(j));
        }
      }
    }
    //yensalgorithm
    yens(start, end, k, matrix);
  } // end of outputShortestPath()

  // 点aと点bまでの距離を返すメソッド
  public static Double NodeDistance(Axis a, Axis b){

    if(segmentExist(new Segment(a, b)) == true) { //繋がっている時には値を返して繋がっていなければinfを返す。

      return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }
    else{
      return inf;
    }
  }


  ///yens algorithm
  public static void yens(int start, int end, long k, double matrix[][]){
    ArrayList<KPath> A = new ArrayList<KPath>();//Aは優先度付きキューではなくていい
    PriorityQueue B = new PriorityQueue (new MyComparator ());
    ArrayList<Double> distance_list = new ArrayList<Double>();//重複ルートを消すための距離のリスト

    int num_node = 6; //交差点以外の数
    int spurnode;
    //まず第一経路を探索する
    B.add(OutputShortestPath(start, end, matrix));
    A.add((KPath)B.poll());

    for(int outer = 0; outer<k-1; outer++){

      KPath base = (KPath)A.get(A.size()-1); //たどるルートの基準 Aに最後に追加されたもの
      ArrayList<Double> rootweight = new ArrayList<Double>(); //重み保存 次々と合計されていく右端に今までの累積
      ArrayList<Integer> spurroot = new ArrayList<Integer>(); //spurroot保存用 rootの経路をリストに一つずつ保存

      loop: for(int i = 0; i < base.path.size()-1; i++){//base.path.size()
        ArrayList<Integer> nextpath = new ArrayList<Integer>(); //spurnodeから次のノードで経路を無限にするものを入れるリスト
        spurnode = base.path.get(i);

        //2回目のループ以降rootを足していく。
        if(i>0){
          int root = base.path.get(i-1);
          spurroot.add(root);// ok

          //3回目のループ以降weightを足していく
          if(i>1){
            rootweight.add(rootweight.get(i-2)+matrix[spurroot.get(spurroot.size()-1)-1][spurnode-1]);
          }
          else{//i==1
            rootweight.add(matrix[spurroot.get(spurroot.size()-1)-1][spurnode-1]);
          }
        }

        //Aに格納されているすべてのルートのsupurnodeからのnextpathをリストに追加。
        for(KPath npath: A){
          loop2: for(int p=0; p<npath.path.size(); p++){
            if(npath.path.get(p) == spurnode){
              for(int m = 0; m<nextpath.size(); m++){
                if(nextpath.get(m) == npath.path.get(p+1)){ //nextpathに重複したものを入れないようにする
                  continue loop2;
                }
              }
              for(int q = 0;q<spurroot.size();q++){
                if(spurroot.get(q)!=npath.path.get(q))continue loop2; //Aにある経路でspurnodeまでの経路が全て一致している訳ではないものは追加しない TODO
              }
              //System.out.println(spurnode+" "+npath.path.get(p+1));
              nextpath.add(npath.path.get(p+1));
            }
          }
        }

        //経路を通れなくする
        ArrayList<Double> tmp = new ArrayList<Double>();
        for(int p=0; p<nextpath.size(); p++){
          tmp.add(matrix[spurnode-1][nextpath.get(p)-1]);
          matrix[spurnode-1][nextpath.get(p)-1] = inf;
        }

        KPath box = OutputShortestPath(spurnode-1, end, matrix);

        //経路を復元 DONE
        for(int p=0; p<nextpath.size(); p++){
          matrix[spurnode-1][nextpath.get(p)-1] = tmp.get(p);
        }

        if(box == null)continue; //経路が無限で会ったとき、次の処理

        //経路を修正, spurrootと距離(重み)を足している
        if(i>0){
          box.distance += round(rootweight.get(rootweight.size()-1));
          for(int j = spurroot.size()-1; j >=0; j--){
            for(int m = 0; m<box.path.size(); m++){
              if(box.path.get(m) == spurroot.get(j))continue loop; //重複があった場合はBに追加しない
            }
            box.path.add(0, spurroot.get(j));
          }
        }
        //System.out.println(box.path);
        for(double dis: distance_list){//同じ経路をキューに入れないため
          if(dis == box.distance)continue loop;
        }

        distance_list.add(box.distance);
        B.add(box);
      }
      KPath shortest = (KPath)B.poll(); // Aに短いものを追加
      if(shortest!=null)A.add(shortest);
    }

    for(int i = 0; i< A.size(); i++){
      //System.out.println((i+1)+"path");
      System.out.println(A.get(i).distance);
      ArrayList<Integer> path_list = A.get(i).path;
      for(int p =0; p<path_list.size(); p++){
        if(path_list.get(p)>num_node)System.out.print(OutputIntersection(path_list.get(p)-num_node));
        else System.out.print(path_list.get(p)+" ");
      }
      System.out.println();

      //System.out.println();
    }

    //Bに残っている経路の確認
    // while(B.size()>0){
    //   KPath p = (KPath)B.poll();
    //   System.out.println();
    //   System.out.println(p.distance);
    //   System.out.println(p.path);
    // }
  }

  public static KPath OutputShortestPath(int s, int d, double matrix[][]){

    int max = matrix.length;//辺の数
    saveShotestPath = new int[max];
    int u, v, start, end, flagp;
    // 各点の訪問状態 0:訪問していない 1:訪問済　2:訪問中
    int [] visited = new int[max];
    double min;
    // startから各点までの最短距離を保持
    double [] data = new double[max];
    // int [] pn = new int[max];

    start = s;
    end = d;

    // 各点の訪問状態を0で初期化、各点までの最短距離をinfで初期化
    for(int i = 0; i<max; i++) {
      visited[i]=0;
      data[i] = inf;
      saveShotestPath[i] = -1;
    }

    // startからstartまでの距離は0、startの訪問状態を訪問済にする
    data[start] = 0;
    visited[start] = 1;

    // pn[s] = -1;

    while(true) {
      min = inf;
      u = -1;
      for(int i = 0; i<max; i++) {
        if(visited[i] != 2 && data[i]<min) {
          min = data[i];
          u = i;
        }
      }

      if(u == -1) {
        break;
      }

      visited[u] = 2;
      for(v = 0; v<max; v++) {
        if(visited[v]!=2 && matrix[u][v]!=inf) {
          // 最短距離の更新
          if(data[v]>data[u]+matrix[u][v]) {
            data[v] = data[u] + matrix[u][v];
            // pn[v] = u;
            visited[v] = 1;
            saveShotestPath[v] = u;
          }
        }
      }
    }

    double distance = data[end];
    if(distance == inf){
      return null;
    }
    //System.out.printf("%.5f\n",distance);
    KPath k_path = ReverseSearchAndOutput(start, end, max, round(distance));
    return k_path;
  } // end of outputShortestPath()
  public static KPath ReverseSearchAndOutput(int start, int end, int max, double distance){
    //System.out.println(distance);
    KPath k_path = new KPath(distance);
    int[] route;
    route = new int[max];
    int route_element = 0;

    for(int i = end;i != start;i = saveShotestPath[i]){
      route[route_element++] = i+1;
    }

    route[route_element++] = start+1;

    for(int i = route_element - 1;i >= 0;i--){
      //System.out.print(route[i] + " ");
      k_path.path.add(route[i]);
    }

    //System.out.println();
    return k_path;
  }

  public static String OutputIntersection(int numberOfIntersection){

    return "C" + numberOfIntersection + " ";

  }

  private static double round(double num){
    BigDecimal numDecBefore = new BigDecimal(num);
    BigDecimal numDecAfter = numDecBefore.setScale(6, BigDecimal.ROUND_HALF_UP);
    double numAfter = numDecAfter.doubleValue();
    return numAfter;
  }

} // end of Class DistanceOfShortestPath




// 始点と終点と最短距離の数
class ShortestPath{
  public Axis s = new Axis(0,0);
  public Axis d = new Axis(0,0);
  public long k;

  public ShortestPath(Axis s, Axis d, long k){
    this.s = s;
    this.d = d;
    this.k = k;
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

class KPath{
  double distance = 0;
  ArrayList<Integer> path = new ArrayList<Integer>();
  public KPath(double distance){
    this.distance = distance;
    this.path = path;
  }
  public KPath(double distance, ArrayList<Integer> path){
    this.distance = distance;
    this.path = path;
  }
}

class MyComparator implements Comparator {
  @Override
  public int compare (Object arg0, Object arg1) {
    KPath path0 = (KPath)arg0;
    KPath path1 = (KPath)arg1;
    double x = path0.distance;
    double y = path1.distance;

    if (x > y) {
      return 1;
    } else if (x < y) {
      return -1;
    } else{
      return 0;
    }
  }
}
