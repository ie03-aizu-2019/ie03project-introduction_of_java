import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListCrossingPoint extends DetectCrossingPoint{
  protected static int count = 0;
  //順番割り当てのためのArray
  protected static ArrayList<Axis> Crossing_List = new ArrayList<Axis>();

  //コンストラクタ
  public ListCrossingPoint(){
  }

  public static void main(String args[]){
    //System.out.println("this is List Program");
    act(input());
    InputShortestPath();
  }

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
          //System.out.printf("%.5f %.5f\n",crossing.x,crossing.y);
          Crossing_List.add(new Axis(crossing.x, crossing.y));
          // 交点と各点の道(線分)を追加
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
      axis.add(new Axis(c.x, c.y));
      System.out.printf("%.5f %.5f\n",c.x,c.y);
    }
  }

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

      System.out.println("axis.size()"+axis.size());
      System.out.println("start"+start);
      System.out.println("end"+end);
      System.out.println((start>=0 && end>=0 && axis.size()>=start && axis.size()>=end)+"\n");

      if(start>=0 && end>=0 && axis.size()>=start && axis.size()>=end){
        shortestPath.add(new ShortestPath(axis.get(start), axis.get(end),  k[i]));
      }

    }
  }
}

//複数キーで比較しソートするためのクラス
class Compare implements Comparator<Axis> {
  public int compare(Axis c1, Axis c2) {
    if(c1.getX() < c2.getX()) {
      return -1;
    } else if(c1.getX() > c2.getX()) {
      return 1;
    } else if(c1.getY() < c2.getY()) {
      return -1;
    } else {
      return 1;
    }
  }
}
