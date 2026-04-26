public class Graf_LS extends AbstractGraf {
    public Graf_LS(String fname) {
        ucitajFile(fname, false);
    }

    public Graf_LS(int n) {
        super(n);
    }

    public void dodajGranu(int u, int v) {
        if (L.dodajGranu(u, v)) {
            L.dodajGranu(v, u);
            M++;
        }
    }

    public void ukloniGranu(int u, int v) {
        if (L.ukloniGranu(u, v)) {
            L.ukloniGranu(v, u);
            M--;
        }
    }
}