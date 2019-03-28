package algoritimos;

import util.Util;

import java.util.Random;

public class HillClimbing {

    public final static int SIZE = 100;

    public static void main(String args[]){
        long startTime = System.currentTimeMillis();
        Random r = new Random();
        int[] cargas = Util.geraCarga(SIZE);
        int[] noAtual = new int [SIZE];
        int[] noProximo = new int [SIZE];

        inicializaPopulacao(r,noAtual);
        for (int i = 0; i <= 1000; i++){
            System.out.println("Geracao: " + i);
            if(Util.funcaoDeAptidao(noAtual, cargas) == 0) break;
            noProximo = sucessor(noAtual,r);

            System.out.println("noAtual:");
            Util.printaVetor(noAtual);
            System.out.println("noProximo:");
            Util.printaVetor(noProximo);
            System.out.println("Distribuição: " + Util.funcaoDeAptidao(noAtual, cargas));

            if(Util.funcaoDeAptidao(noProximo,cargas) < Util.funcaoDeAptidao(noAtual,cargas)) noAtual = noProximo.clone();
            System.out.println("__________________________________________");
        }

        System.out.println("Distribuição: " + Util.funcaoDeAptidao(noAtual, cargas));
        System.out.println(" ");
        Util.printSolucao(noAtual,cargas);

        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Duration: " + duration);
    }

    private static void inicializaPopulacao(Random r, int[] populacao) {
        for(int j = 0; j < populacao.length; j++) {
            populacao[j] = r.nextInt(2);
        }
    }


    private static int[] sucessor(int[] populacao, Random r){
        int pos = r.nextInt(SIZE);
        int[] aux = populacao.clone();
        if(populacao[pos] == 0) aux[pos] = 1;
        else aux[pos] = 0;
        return aux;
    }
}
