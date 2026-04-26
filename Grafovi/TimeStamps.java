public class TimeStamps {

  int T;
  int[] time;

  public TimeStamps(int k) {
    T = -1; time = new int[k];
    for(int i = 0; i < k; i++) time[i] = -1;
  }
  
  public void stamp(int v) { T++; time[v] = T; }
  public void stamp(int v, int s) { time[v] = s; }
  public int theStampOf(int v) { return time[v]; }
  public boolean stamped(int v) { return time[v] != -1; }
  public int[] stamps() { return time; }
}
