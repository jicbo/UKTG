//cspell:disable
import java.io.*;
import java.util.*;

public class Graf {

  public final static int NoVertex = -1;

  private int N; // broj cvorova grafa
  private int M; // broj grana grafa
  private int[] D; // stepen cvora
  private int[][] A; // matrica susedstva

  // konstruktori
  
  public Graf(String fname) {
    try {
      SimpleIO.openFileInput(fname);
      N = SimpleIO.rdInt();
      D = new int[N];
      A = new int[N][N];
      int sum = 0;
      for(int i = 0; i < N; i++){
        int s = 0;
        for(int j = 0; j < N; j++){
          A[i][j] = SimpleIO.rdInt();
          if(A[i][j] != 0) s++;
        }
        D[i] = s;
        sum += s;
      }
      M = sum / 2;
    } catch (FileNotFoundException fn) {
      System.out.println("File not found: " + fn);
    } catch (Exception ex) {
      System.out.println(ex);
    }
    SimpleIO.closeInput();
  }

  public Graf(int n) {
    N = n;
    M = 0;
    D = new int[N];
    A = new int[N][N];
    for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
        A[i][j] = 0;
      }
      D[i] = 0;
    }
  }

  // osnovni parametri
  
  public int brCvorova() { return N; }

  public int brGrana() { return M; }

  public int deg(int v) { return D[v]; }

  public boolean susedni(int u, int v) { return A[u][v] != 0; }

  public int tezina(int u, int v) { return A[u][v]; }
  
  // dodavanje i oduzimanje grane
  
  public void dodajGranu(int u, int v, int w){
    D[u]++; D[v]++; M++;
    A[u][v] = w; A[v][u] = w;
  }

  public void dodajGranu(int u, int v){
    dodajGranu(u, v, 1);
  }

  public void ukloniGranu(int u, int v){
    D[u]--; D[v]--; M--;
    A[u][v] = 0; A[v][u] = 0;
  }

  // iterator po skupu suseda cvora

  private void nadjiSledeciCvor(SkupSuseda S) {
    while(true) {
      S.i++;
      if(S.i >= N) break;
      if(susedni(S.v, S.i)) break;
    }
  }

  public SkupSuseda susedi(int v) {
    SkupSuseda S = new SkupSuseda(v, NoVertex);
    nadjiSledeciCvor(S);
    return S;
  }
  
  public boolean imaJos(SkupSuseda S) { return S.i < N; }
  
  public int sledeci(SkupSuseda S) {
    int v = S.i;
    nadjiSledeciCvor(S);
    return v;
  }

  // osnovna implementacija DFS algoritma
  // povezanost, komponente, mostovi i artikulacioni cvorovi
  // provera da li je graf stablo
  
  public void DFS(int v, TimeStamps dft) {
    dft.stamp(v);
    SkupSuseda S = susedi(v);
    while(imaJos(S)){
      int w = sledeci(S);
      if(!dft.stamped(w)) DFS(w, dft);
    }
  }

  // instanca klase SkupSuseda ima javno polje v koje sadrzi cvor grafa
  // kroz cije susede prolazimo; zato na steku ne cuvamo cvor v, vec
  // susedi(v) koji sadrzi informaciju i o cvoruv i o njegovom skupu suseda;
  // tako dobijamo efikasniji algoritam
  public void iter_DFS(int v, TimeStamps dft) {
    Stack<SkupSuseda> K = new Stack<SkupSuseda>();
    dft.stamp(v);
    K.push(susedi(v));
    while(!K.isEmpty()){
      SkupSuseda S = K.peek();
      boolean nasao = false;
      while(imaJos(S)){
        int w = sledeci(S);
        if(!dft.stamped(w)) {
          nasao = true;
          dft.stamp(w);
          K.push(susedi(w));
          break;
        }
      }
      if(!nasao) K.pop();
    }
  }

  public boolean povezan() {
    TimeStamps dft = new TimeStamps(N);
    DFS(0, dft);
    for(int i = 0; i < N; i++){
      if(!dft.stamped(i)) return false;
    }
    return true;
  }

  public int brKompPovez() {
    int k = 0;
    TimeStamps dft = new TimeStamps(N);
    for(int i = 0; i < N; i++){
      if(!dft.stamped(i)) {
        k++;
        DFS(i, dft);
      }
    }
    return k;
  }

  public void DFS(int v, TimeStamps dft, int stamp) {
    dft.stamp(v, stamp);
    SkupSuseda S = susedi(v);
    while(imaJos(S)){
      int w = sledeci(S);
      if(!dft.stamped(w)) DFS(w, dft, stamp);
    }
  }

  public int[] komponentePovez() {
    int k = 0;
    TimeStamps dft = new TimeStamps(N);
    for(int i = 0; i < N; i++){
      if(!dft.stamped(i)) {
        k++;
        DFS(i, dft, k);
      }
    }
    return dft.stamps();
  }

  
  public boolean artCv(int v) {
    if(deg(v) == 0 || deg(v) == 1) return false;
    TimeStamps dft = new TimeStamps(N);
    dft.stamp(v, 0);
    SkupSuseda S = susedi(v);
    int x = sledeci(S);
    DFS(x, dft);
    do {
      x = sledeci(S);
      if(!dft.stamped(x)) return true;
    } while(imaJos(S));
    return false;
  }

  public boolean most(int u, int v) {
    ukloniGranu(u, v);
    TimeStamps dft = new TimeStamps(N);
    DFS(u, dft);
    dodajGranu(u, v);
    return !dft.stamped(v);
  }

  public boolean stablo() {
    return povezan() && M == N - 1;
  }

  // osnovna implementacija BFS algoritma
  // rastojanja od jednog cvora
  
  public void BFS(int v, TimeStamps bft) {
    Queue<Integer> Q = new LinkedList<Integer>();
    bft.stamp(v);
    Q.add(v);
    while(!Q.isEmpty()){
      v = Q.remove();
      SkupSuseda S = susedi(v);
      while(imaJos(S)){
        int w = sledeci(S);
        if(!bft.stamped(w)){
          bft.stamp(w);
          Q.add(w);
        }
      }
    }
  }

  public int[] rastojanjaOd(int v) {
    TimeStamps bft = new TimeStamps(N);
    Queue<Integer> Q = new LinkedList<Integer>();
    bft.stamp(v, 0);
    Q.add(v);
    while(!Q.isEmpty()){
      v = Q.remove();
      SkupSuseda S = susedi(v);
      while(imaJos(S)){
        int w = sledeci(S);
        if(!bft.stamped(w)){
          bft.stamp(w, bft.theStampOf(v) + 1);
          Q.add(w);
        }
      }
    }
    return bft.stamps();
  }

  public int[] najkraciPuteviDo(int v) {
    TimeStamps bft = new TimeStamps(N);
    int[] otac = new int[N];
    Queue<Integer> Q = new LinkedList<Integer>();
    bft.stamp(v, 0);
    otac[v] = NoVertex;
    Q.add(v);
    while(!Q.isEmpty()){
      v = Q.remove();
      SkupSuseda S = susedi(v);
      while(imaJos(S)){
        int w = sledeci(S);
        if(!bft.stamped(w)){
          bft.stamp(w, bft.theStampOf(v) + 1);
          otac[w] = v;
          Q.add(w);
        }
      }
    }
    return otac;
  }

  // komponenta je stablo
  // komponenta je bipartitan graf
  
  private boolean imaPovratnuGr(int v, int otac, TimeStamps dft) {
    dft.stamp(v);
    SkupSuseda S = susedi(v);
    while(imaJos(S)){
      int w = sledeci(S);
      if(!dft.stamped(w)){
        boolean ima = imaPovratnuGr(w, v, dft);
        if(ima) return true;
      }
      else if(w != otac) return true;
    }
    return false;
  }
  
  public boolean kompJeStablo(int v) {
    TimeStamps dft = new TimeStamps(N);
    return !imaPovratnuGr(v, NoVertex, dft);
  }
  
  private boolean imaNepKonturu(int v, int otac, TimeStamps dft) {
    boolean nepKontura = false;
    if(otac == NoVertex) dft.stamp(v, 0);
    else dft.stamp(v, 1 - dft.theStampOf(otac));

    SkupSuseda S = susedi(v);
    while(!nepKontura && imaJos(S)){
      int w = sledeci(S);
      if(!dft.stamped(w)){
        nepKontura = imaNepKonturu(w, v, dft);
      }
      else if(dft.theStampOf(v) == dft.theStampOf(otac)) {
        nepKontura = true;
      }
    }
    return nepKontura;
  }
  
  public boolean kompJeBipart(int v) {
    TimeStamps dft = new TimeStamps(N);
    return !imaNepKonturu(v, NoVertex, dft);
  }

  // algoritmi sa tezinskim grafovima
  
  public int[][] Floyd() {
    int[][] D = new int[N][N];
    int i, j, k;
    for(i = 0; i < N; i++)
      for(j = 0; j < N; j++)
        D[i][j] = tezina(i, j);

    for(k = 0; k < N; k++)
      for(i = 0; i < N; i++)
        for(j = 0; j < N; j++)
            if(
              i != j && i != k && j != k && D[i][k] != 0 && D[k][j] != 0
              &&(D[i][j] == 0 || D[i][k] + D[k][j] < D[i][j])
            ) {
              D[i][j] = D[i][k] + D[k][j];
            }
    return D;
  }
  
  public Graf Prim() {
    Graf T = new Graf(N);
    boolean[] uStablu = new boolean[N];
    uStablu[0] = true;
    for(int i = 1; i < N; i++) uStablu[i] = false;
    while(T.brGrana() < N - 1){
      // nadji najlaksu granu ciji jedan kraj je u stablu a drugi nije
      boolean nasao = false;
      int u = 0, v = 0, w = 0;
      for(int i = 0; i < N - 1; i++)
        for(int j = i + 1; j < N; j++)
          if(susedni(i, j) && (uStablu[i] && !uStablu[j] || uStablu[j] && !uStablu[i])) {
            if(nasao){
              if(tezina(i, j) < w) {
                u = i; v = j; w = tezina(i, j);
              }
            }
            else {
              u = i; v = j; w = tezina(i, j);
              nasao = true;
            }
          }
      // dodaj tu granu u stablo
      T.dodajGranu(u, v, w);
      uStablu[u] = true; uStablu[v] = true;
    }
    return T;
  }
}
