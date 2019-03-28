import java.util.ArrayList;
import java.util.Random;

public class Util {

    public static void printSolucao(int[] s, int[] cargas){
        ArrayList<Integer> zero = new ArrayList();
        ArrayList<Integer> um = new ArrayList();
        int somaZero = 0;
        int somaUm = 0;

        for(int i = 0; i<cargas.length; i++){
            if(s[i] == 0) zero.add(cargas[i]);
            else um.add(cargas[i]);
        }

        System.out.println("Zeros:");
        for (Integer i:
             zero) {
            System.out.print(i + " ");
            somaZero += i;
        }
        System.out.println("Soma: " + somaZero);

        System.out.println("Uns:");
        for (Integer i:
                um) {
            System.out.print(i + " ");
            somaUm += i;
        }
        System.out.println("Soma: " + somaUm);
    }

    public static int[] geraCarga(int n){
        int[] aux = new int[n];
        Random r = new Random();
        for(int i = 0; i<n; i++){
            aux[i] = r.nextInt(200);
        }
        return aux;
    }

}
