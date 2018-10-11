import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Integral {


    public static double result = 0;

    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore barreiraMutex = new Semaphore(1);

    public static Semaphore barreira = new Semaphore(0);

    public static int contador = 0;

    public static int p;

    public static long startTime;
    public static long endTime;

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

//        System.out.print("Digite a:");
//        double a = scanner.nextDouble();
//
//        System.out.print("\nDigite b:");
//        double b = scanner.nextDouble();
//
//        System.out.print("\nDigite n:");
//        int n = scanner.nextInt();

        double a = 1;
        double b = 2;
        double n = 100000000;

        System.out.print("\nDigite o número de threads a serem usadas:");
        p = scanner.nextInt();

        Thread[] threads = new Thread[p];

        double pR = n/p;

        double h = (b-a) / n;

        double aux = a;

        for(int i =0; i < p; ++i){

            threads[i] = new Thread( new Calculator(
                    pR,
                    h,
                    aux,
                    i
            ));

            aux += (pR * h);
        }

        startTime = System.currentTimeMillis();

        for ( Thread thread : threads)
        {
            thread.start();
        }

    }

}
