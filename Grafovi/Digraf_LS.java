public class Digraf_LS extends AbstractGraf {
    public Digraf_LS(String fname) {
        ucitajFile(fname, true);
    }

    public Digraf_LS(int n) {
        super(n);
        Din = new int[n];
    }

    public void dodajGranu(int u, int v) {
        if (L.dodajGranu(u, v)) {
            Din[v]++;
            M++;
        }
    }

    public void ukloniGranu(int u, int v) {
        if (L.ukloniGranu(u, v)) {
            Din[v]--;
            M--;
        }
    }

    public int degOut(int v) { return L.susedi(v).size(); }
    public int degIn(int v) { return Din[v]; }
}