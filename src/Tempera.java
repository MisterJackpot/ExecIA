import java.util.Random;

//Gabriel Löff e Gabriel Weich

public class Tempera {
    public final static int SIZE = 100;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        int[] cargas = Util.geraCarga(SIZE);
        int[] noAtual = new int [SIZE];
        int[] noProximo = new int [SIZE];
        double T = 200;
        double k = 0.6;
        int D;


        inicializaPopulacao(r, noAtual);
        for(int geracao = 1; geracao<=10000; geracao++) {
            System.out.println("Geracao:" + geracao);
            T = T * k;
            System.out.println("Temperatura: " + T);
            if(T == 0) break;
            noProximo = sucessor(noAtual,r);
            D = funcaoDeAptidao(noProximo, cargas) - funcaoDeAptidao(noAtual, cargas);
            System.out.println("D: " + D);
            System.out.println("noAtual:");
            printaVetor(noAtual);
            System.out.println("noProximo:");
            printaVetor(noProximo);
            System.out.println("Distribuição: " + funcaoDeAptidao(noAtual, cargas));
            System.out.println("Math.exp((-D)/T): " + Math.exp((-D)/T));
            if(D > 0) noAtual = noProximo.clone();
            else if(r.nextDouble() < Math.exp((-D)/T)) noAtual = noProximo.clone();
            System.out.println("__________________________________________");
            if(funcaoDeAptidao(noAtual,cargas) == 0) break;

        }

        System.out.println("Resultado: ");
        printaVetor(noAtual);
        printaVetor(cargas);
        System.out.println("Distribuição: " + funcaoDeAptidao(noAtual, cargas));
        System.out.println(" ");
        Util.printSolucao(noAtual,cargas);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration);
    }

    private static void printaVetor(int[] populacao) {

        for(int j = 0; j < populacao.length; j++) {
            System.out.print(populacao[j] + " ");
        }
        System.out.println();
    }

    private static void inicializaPopulacao(Random r, int[] populacao) {
        for(int j = 0; j < populacao.length; j++) {
            populacao[j] = r.nextInt(2);
        }
    }

    private static int funcaoDeAptidao(int[] linha, int[] cargas) {
        int somaZero = 0;
        int somaUm = 0;
        int i;
        for(i = 0; i<linha.length; i++) {
            if(linha[i]==0) {
                somaZero+=cargas[i];
            } else {
                somaUm+=cargas[i];
            }
        }
        return Math.abs(somaZero - somaUm);
    }

    private static int[] sucessor(int[] populacao, Random r){
        int pos = r.nextInt(SIZE);
        int[] aux = populacao.clone();
        if(populacao[pos] == 0) aux[pos] = 1;
        else aux[pos] = 0;
        return aux;
    }

}