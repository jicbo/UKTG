
public class GrTest1 {
  public static void main(String args[]) {
    Graf G = new Graf("test4.txt");
    System.out.println("Broj cvorova = " + G.brCvorova());
    System.out.println("Broj grana = " + G.brGrana());
    for(int i = 0; i < G.brCvorova(); i++){
      System.out.println("deg(" + i + ") = " + G.deg(i));
      System.out.print("susedi: ");
      SkupSuseda S = G.susedi(i);
      while(G.imaJos(S)){
        int x = G.sledeci(S);
        System.out.print(x + " ");
      }
      System.out.println();
    }
  }
}
