import java.util.Scanner;

class Gray {
    static void gen(int n) {
        int max = (1 << n) - 1;
        int base = (0 << n);
        int res = (0 << n);
        while(base<max) {
            int idx = Integer.numberOfTrailingZeros(~base);
            res ^= (1 << idx);
            String bi = Integer.toBinaryString((1 << n) | res).substring(1); 
            System.out.println(bi);
            base++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Unesite n: ");
        int n = sc.nextInt();
        sc.close();
        gen(n);
    }
}
