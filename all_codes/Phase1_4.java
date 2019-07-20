import java.util.*;

public class Phase1_4 extends Phase1_3 {

  protected static int[] saveShotestPath;
  private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);
  // private static double [][] nodeDistance;   //Inter-node distance

  public static void main(String args[]){
    act(input());
    // 始点、終点、最短距離の数をshortestPathに入れる
    InputShortestPath();
    divide();

    for(int i = 0; i<shortestPath.size(); i++){
      OutputShortestPath(shortestPath.get(i).s, shortestPath.get(i).d, shortestPath.get(i).k);
    }
}
// sからdまでの最短距離を出力するメソッド、kは最短距離の数（短い順） ダイクストラ法
public static void OutputShortestPath(Axis s, Axis d, int k){

  int max = axis.size();
  saveShotestPath = new int[max];
  int u, v, start, end, flagp;
  // 各点の訪問状態 0:訪問していない 1:訪問済　2:訪問中
  int [] visited = new int[max];
  double min;
  // startから各点までの最短距離を保持
  double [] data = new double[max];
  // startから各点までの最短距離を小さい順からk個表示するためのリスト
  ArrayList<ArrayList<Double>> path = new ArrayList<ArrayList<Double>>();
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
      if(visited[v]!=2 && NodeDistance(axis.get(u), axis.get(v))!=inf) {
        // 最短距離を入れるリストに重複する値がなければ追加する
        flagp = 0; //判定用
        for(int i=0; i<path.get(v).size(); i++){
          if(path.get(v).get(i)==data[v]){
            flagp = 1;
          }
        }
        if(flagp == 0){
          path.get(v).add(data[v]);
          Collections.sort(path.get(v));
        }
        // 最短距離の更新
        if(data[v]>data[u]+NodeDistance(axis.get(u), axis.get(v))) {
          data[v] = data[u] + NodeDistance(axis.get(u), axis.get(v));
          // pn[v] = u;
          visited[v] = 1;
          saveShotestPath[v] = u;
        }
      }
    }
  }

  // 結果の出力
  if(path.get(end).size()>0) {
    for(int j = 0; j<k; j++){
      System.out.printf("%.5f\n",path.get(end).get(j));
    }
    ReverseSearchAndOutput(start, end, max);
    return;
  }
  else{
    System.out.println("NA");
    return;
  }
} // end of outputShortestPath()
public static void ReverseSearchAndOutput(int start, int end, int max){

  int[] route;
  route = new int[max];
  int route_element = 0;
  for(int i = end;i != start;i = saveShotestPath[i]){
    route[route_element++] = i+1;
  }
  route[route_element++] = start+1;
  for(int i = route_element - 1;i >= 0;i--){
    if(route[i] > n){
      OutputIntersection(route[i] - n);
    }
    else{
      System.out.print(route[i] + " ");
    }
  }
  System.out.println();
}

public static void OutputIntersection(int numberOfIntersection){

  System.out.print("C" + numberOfIntersection + " ");

}
}