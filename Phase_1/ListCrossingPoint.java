public class ListCrossingPoint extends DetectCrossingPoint{
  public static void main(String args[]){
    System.out.println("this is List Program");
    act(input());
  }

  public static void act(Segment [] seg){
    Axis crossing = new Axis();
    for(int i = 0; i<seg.length-1; i++){
      for(int j =i+1; j<seg.length; j++){

        //交差点検知のメソッド実行
        crossing = detect(seg[i], seg[j]);

        //交差点があれば座標を表示なければNA
        if(crossing.x != -1 && crossing.y != -1){
          System.out.printf("%.5f %.5f\n",crossing.x,crossing.y);
        }else{
          //System.out.println("NA");
        }

      }
    }
  }
}