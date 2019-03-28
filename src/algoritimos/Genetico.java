package algoritimos;

import java.util.Random;
import util.Util;

public class Genetico {
    public final static int SIZE = 100;
    public final static int TAM = 11;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        int[] cargas = Util.geraCarga(SIZE);
        int[][] populacao = new int [TAM][SIZE+1];
        int[][] populacaoIntermediaria = new int [TAM][SIZE+1];

        inicializaPopulacao(r, populacao);
        for(int geracao = 0; geracao<=10000; geracao++) {
            System.out.println("Geracao:" + geracao);
            calculaFuncoesDeAptidao(populacao, cargas);
            printaMatriz(populacao);
            int melhor = pegaAltaTerra(populacao, populacaoIntermediaria); //highlander, Vulgo elitismo
            System.out.println("_________________");
            crossOver(populacaoIntermediaria, populacao);
            if(geracao%2==0) {
                mutacao(populacaoIntermediaria);
            }
            populacao = populacaoIntermediaria;
            if(populacao[0][SIZE] == 0) break;
            /* if(convergencia(populacao,melhor)){
                printaMatriz(populacao);
                System.out.println(">>>> Convergiu: ");
                break;
            } */
        }
        System.out.println("Solução:");
        printaVetor(populacao[0]);
        printaVetor(cargas);
        System.out.println(populacao[0][SIZE]);
        System.out.println(" ");
        Util.printSolucao(populacao[0], cargas);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration);
    }

    private static void printaMatriz(int[][] populacao) {
        for(int i = 0; i < populacao.length; i++) {
            for(int j = 0; j < populacao[i].length; j++) {
                System.out.print(populacao[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void printaVetor(int[] populacao) {

        for(int j = 0; j < populacao.length; j++) {
            System.out.print(populacao[j] + " ");
        }
        System.out.println();
    }

    private static void inicializaPopulacao(Random r, int[][] populacao) {
        for(int i = 0; i < populacao.length; i++) {
            for(int j = 0; j < populacao[i].length -1; j++) {
                populacao[i][j] = r.nextInt(2);
            }
        }
    }

    private static void calculaFuncoesDeAptidao(int[][] populacao, int[] cargas){
        for(int i = 0; i < populacao.length; i++) {
            funcaoDeAptidao(populacao[i], cargas);
        }
    }

    private static void funcaoDeAptidao(int[] linha, int[] cargas) {
        int somaZero = 0;
        int somaUm = 0;
        int i = 0;
        for(; i<linha.length-1; i++) {
            if(linha[i]==0) {
                somaZero+=cargas[i];
            } else {
                somaUm+=cargas[i];
            }
        }
        linha[i] = Math.abs(somaZero - somaUm);
    }

    private static int pegaAltaTerra(int[][] populacao, int[][] populacaoIntermediaria) {
        int highlander = 0;
        int menor = populacao[0][SIZE];

        for(int i=1; i<populacao.length; i++) {
            if(populacao[i][SIZE] < menor) {
                menor = populacao[i][SIZE];
                highlander = i;
            }
        }

        for(int i=0; i<SIZE+1; i++) {
            populacaoIntermediaria[0][i] = populacao[highlander][i];
        }

        return highlander;

    }

    private static int[] torneio(int[][] populacao) {
        Random r = new Random();
        int l1 = r.nextInt(populacao.length);
        int l2 = r.nextInt(populacao.length);

        if(populacao[l1][SIZE] < populacao[l2][SIZE]) {
            return populacao[l1];
        }else {
            return populacao[l2];
        }
    }

    private static void crossOver(int[][] intermediaria, int[][] populacao) {
        int[] pai;
        int[] pai2;
        int corte = 10;
        for(int i=1; i<TAM; i=i+2){
            do{
                pai = torneio(populacao);
                pai2 = torneio(populacao);
            }while(pai==pai2);
            for(int j=0;j<corte; j++){
                intermediaria[i][j]=pai[j];
                intermediaria[i+1][j]=pai2[j];
            }
            for(int j=corte;j<SIZE; j++){
                intermediaria[i][j]=pai2[j];
                intermediaria[i+1][j]=pai[j];
            }
        }
    }

    private static void mutacao(int[][] intermediaria){
        Random r = new Random();
        for(int cont = 1; cont<=2; cont++){
            int linha = r.nextInt(TAM);
            int coluna = r.nextInt(SIZE);
            if(intermediaria[linha][coluna]==0) intermediaria[linha][coluna] = 1;
            else intermediaria[linha][coluna] = 0;
        }
    }

    private static boolean convergencia(int[][] populacao, int melhor){
        int cont = 0;
        for(int i=0; i<TAM; i++) if(populacao[i][SIZE] == populacao[melhor][SIZE]) cont++;
        double perc = cont*100.0/TAM;
        return perc>98;
    }

}