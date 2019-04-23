import java.util.*;

public class DeistanceOfShortestPath extends ListCrossingPoint {

private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);
private static double [][] nodeDistance;   //Inter-node distance
private static int j, cross;

public static void main(String args[]){
  act(input());
}

public static void InputNodeDistance(Segment [] seg){

  Axis crossing = new Axis();

  for(i = 0; i<cross; i++) {
    for(int j = 0; j<cross; j++) {
nodeDistance[i][j] = inf;
    }
  }

  for(int i = 0; i<seg.length-1; i++) {
    for(int j =i+1; j<seg.length; j++) {

//交差点検知のメソッド実行, *継承クラスのメソッド
crossing = detect(seg[i], seg[j]);

//交差点があれば座標を表示なければNA
if(crossing.x != -1 && crossing.y != -1) {
  // 交点と各線分の頂点(4個)の距離を求める
  // nodeDistance[i][n] = Math.sqrt(Math.pow(seg[i].p.x-crossing.x,2)+Math.pow(seg[i].p.y-crossing.y,2));
  // nodeDistance[n][i] = Math.sqrt(Math.pow(seg[i].p.x-crossing.x,2)+Math.pow(seg[i].p.y-crossing.y,2));
  // nodeDistance[i][n++] = Math.sqrt(Math.pow(seg[i].q.x-crossing.x,2)+Math.pow(seg[i].q.y-crossing.y,2));
  // nodeDistance[n][i] = Math.sqrt(Math.pow(seg[i].q.x-crossing.x,2)+Math.pow(seg[i].q.y-crossing.y,2));
  // nodeDistance[i][n++] = Math.sqrt(Math.pow(seg[i].p.x-crossing.x,2)+Math.pow(seg[j].p.y-crossing.y,2));
  // nodeDistance[n][i] = Math.sqrt(Math.pow(seg[i].p.x-crossing.x,2)+Math.pow(seg[j].p.y-crossing.y,2));
  // nodeDistance[i][n++] = Math.sqrt(Math.pow(seg[j].q.x-crossing.x,2)+Math.pow(seg[j].q.y-crossing.y,2));
  // nodeDistance[n][i] = Math.sqrt(Math.pow(seg[j].q.x-crossing.x,2)+Math.pow(seg[j].q.y-crossing.y,2));
  for(i = 0; i<m; i++) {
    nodeDistance[b[i]-1][e[i]-1] = inf;
    nodeDistance[e[i]-1][b[i]-1] = inf;

    nodeDistance[b[i]-1][n] = Math.sqrt(Math.pow(axis[b[i]-1].x-x,2)+Math.pow(axis[b[i]-1].y-y,2));
    nodeDistance[n][b[i]-1] = Math.sqrt(Math.pow(axis[b[i]-1].x-x,2)+Math.pow(axis[b[i]-1].y-y,2));
    // System.out.println("i="+i);
    nodeDistance[e[i]-1][n] = Math.sqrt(Math.pow(axis[e[i]-1].x-x,2)+Math.pow(axis[b[i]-1].y-y,2));
    // System.out.println("i="+i);
    nodeDistance[n][e[i]-1] = Math.sqrt(Math.pow(axis[e[i]-1].x-x,2)+Math.pow(axis[b[i]-1].y-y,2));
  }
}
else{
  cross = n;
  nodeDistance = new double[n][n];
  for(i = 0; i<n; i++) {
    for(int j = 0; j<n; j++) {
nodeDistance[i][j] = inf;
    }
  }
  for(i = 0; i<m; i++) {
    nodeDistance[b[i]-1][e[i]-1] = Math.sqrt(Math.pow(axis[b[i]-1].x-axis[e[i]-1].x,2)+Math.pow(axis[b[i]-1].y-axis[e[i]-1].y,2));
    nodeDistance[e[i]-1][b[i]-1] = Math.sqrt(Math.pow(axis[b[i]-1].x-axis[e[i]-1].x,2)+Math.pow(axis[b[i]-1].y-axis[e[i]-1].y,2));
  }
}
    }
  }
}

public static String DeistanceOfShortestPath(int s, int e, int k, int n){
  if(s>n || e>n) {
    return "NA";
  }

  int i, j, u, v, max;
  double min;
  s -= 1;
  e -= 1;
  max = 1000000;

  int [] visited = new int[max]; //各地点の訪問状態の確認
  double [] d = new double[max];
  int [] pn = new int[max];

  for(i = 0; i<n; i++) {
    visited[i] = 0;
    d[i] = inf;
  }
  d[s] = 0;
  pn[s] = -1;
  visited[s] = 1;

  while(true) {
    min = inf;
    u = -1;
    for(i = 0; i<n; i++) {
if(visited[i] != 2 && d[i]<min) {
  System.out.println("d["+i+"]<min: "+d[i]+"<"+min+" visited"+visited[i]);
  min = d[i];
  u = i;
  System.out.println("min = "+min);
}
    }

    if(u == -1) {
break;
    }

    visited[u] = 2;
    System.out.println("visited["+u+"]="+visited[u]);

    for(v = 0; v<n; v++) {
if(visited[v]!=2 && nodeDistance[u][v]!=inf) {
  System.out.println("nodeDistance["+u+"]["+v+"]!=inf");
  if(d[v]>d[u]+nodeDistance[u][v]) {
    d[v] = d[u] + nodeDistance[u][v];
    pn[v] = u;
    System.out.println("d["+v+"]="+d[v]+"\n");
    visited[v] = 1;
  }
}
    }
  }

  if(d[e] != inf) {
    return String.valueOf(d[e]);
  }
  else{
    return "NA";
  }
}
}
