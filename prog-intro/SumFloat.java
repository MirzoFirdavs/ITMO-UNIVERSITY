public class SumFloat{
  public static void Main(String[] args){
    float ans = 0;
    for(int i = 0 ; i < args.length ; i++){
      int cur1 = -1 , cur2 = 0;
      for(int j = 0 ; j < args[i].length() ; j++){
        if(Character.isWhitespace(args[i].charAt(j))){
          if(cur2 > 0){
            ans += Float.parseFloat(args[i].substring(cur1, j));
            cur2 = 0;
            cur1 = -1;
          }
        } 
        else{
          if(cur1 == -1){
            cur1 = j;
          }
          cur2++;
        }
      }
      if(cur2 > 0){
        ans += Float.parseFloat(args[i].substring(args[i].length()-cur2, args[i].length()));
      }
    }  
    System.out.println(ans);
  }
}