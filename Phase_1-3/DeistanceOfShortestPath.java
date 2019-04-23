import java.util.*;

public class DeistanceOfShortestPath extends ListCrossingPoint {

private static double inf = Double.longBitsToDouble(0x7ff0000000000000L);
// private static double [][] nodeDistance;   //Inter-node distance

public static void main(String args[]){
  act(input());
  InputShortestPath();
}

public static Double NodeDistance(Axis a, Axis b){
  if(segment.indexOf(new Segment(a, b)) != -1) {
    return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
  }
  else{
    return inf;
  }
}

public static String DeistanceOfShortestPath(int s, int e, int k){
  if(s>n || e>n) {
    return "NA";
  }

  int u, v, max;
  double min;
  s -= 1;
  e -= 1;
  max = 1000000;

  int [] visited = new int[max]; //各地点の訪問状態の確認
  double [] d = new double[max];
  int [] pn = new int[max];

  for(int i = 0; i<n; i++) {
    visited[i] = 0;
    d[i] = inf;
  }
  d[s] = 0;
  pn[s] = -1;
  visited[s] = 1;

  while(true) {
    min = inf;
    u = -1;
    for(int i = 0; i<n; i++) {
      if(visited[i] != 2 && d[i]<min) {
        // System.out.println("d["+i+"]<min: "+d[i]+"<"+min+" visited"+visited[i]);
        min = d[i];
        u = i;
        // System.out.println("min = "+min);
      }
    }

    if(u == -1) {
      break;
    }

    visited[u] = 2;
    // System.out.println("visited["+u+"]="+visited[u]);

    for(v = 0; v<n; v++) {
      if(visited[v]!=2 && NodeDistance(axis.get(u), axis.get(v))!=inf) {
        // System.out.println("nodeDistance["+u+"]["+v+"]!=inf");
        if(d[v]>d[u]+NodeDistance(axis.get(u), axis.get(v))) {
          d[v] = d[u] + NodeDistance(axis.get(u), axis.get(v));
          pn[v] = u;
          // System.out.println("d["+v+"]="+d[v]+"\n");
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
