import java.util.Vector;

public class ListaSuseda {
    Vector<Vector<Integer>> LS;

    public ListaSuseda(int n) {
        LS = new Vector<Vector<Integer>>(n);
        for (int i = 0; i < n; i++) {
            LS.add(new Vector<Integer>());
        }
    }

    public boolean dodajGranu(int i, int j) {
        if (!susedni(i, j)) {
            susedi(i).add(j);
            return true;
        }
        return false;
    }

    public boolean ukloniGranu(int i, int j) {
        if (susedni(i, j)) {
            susedi(i).remove((Integer)j);
            return true;
        }
        return false;
    }

    public void dodajCvor() {
        LS.add(new Vector<Integer>());
    }

    public Vector<Integer> susedi(int i) {
        return LS.get(i);
    }

    public boolean susedni(int u, int v) {
        return susedi(u).contains(v);
    }

    public int get(int i, int j) {
        return LS.get(i).get(j);
    }
}
