import java.util.Scanner;

class Gray {
    static String bin(int val, int len) {
        return Integer.toBinaryString((1 << len) | val).substring(1);
    }

    static void gen(int n) {
        int max = (1 << n) - 1;
        int base = (0 << n);
        int res = (0 << n);
        System.out.println(bin(res, n));
        while(base<max) {
            int idx = Integer.numberOfTrailingZeros(~base);
            res ^= (1 << idx);
            System.out.println(bin(res, n));
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
