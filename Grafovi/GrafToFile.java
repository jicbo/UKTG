import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class GrafToFile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Unesite broj cvorova (N): ");
        int n = sc.nextInt();
        
        System.out.print("Unesite broj grana (M): ");
        int m = sc.nextInt();

        System.out.print("Da li je graf usmeren? (y/n): ");
        boolean isDirected = sc.next().equalsIgnoreCase("y");
        
        int[][] arr = new int[n][n];

        String output = n + "\n";
        
        System.out.println("Unesite " + m + " grana (format: od do):");
        for (int k = 0; k < m; k++) {
            System.out.print("Grana " + (k + 1) + ": ");
            int u = sc.nextInt();
            int v = sc.nextInt();
            
            if (u >= 0 && u < n && v >= 0 && v < n) {
                arr[u][v] = 1;
                if (!isDirected) {
                    arr[v][u] = 1;
                }
            } else {
                System.out.println("Greska: Cvorovi moraju biti u opsegu 0 do " + (n - 1));
                k--; 
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                output += arr[i][j] + " ";
            }
            output += "\n";
        }
        
        System.out.print("Unesite ime fajla: ");
        String filename = sc.next();
        
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            writer.print(output);
            System.out.println("\nGraf (" + (isDirected ? "usmeren" : "neusmeren") + ") je sacuvan u: " + filename);
        } catch (Exception e) {
            System.err.println("Greska: " + e.getMessage());
        }
        
        sc.close();
    }
}