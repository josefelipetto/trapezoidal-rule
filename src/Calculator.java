class Calculator implements Runnable{

    private double pR;

    private double h;

    private double a;

    private double area;

    private int threadNumber;

    public Calculator(double pR, double h, double a, int threadNumber) {
        this.pR = pR;
        this.h = h;
        this.a = a;
        this.area = 0;
        this.threadNumber = threadNumber;


    }

    @Override
    public void run() {
        System.out.println("Thread " + this.threadNumber + " iniciada ");
        double a;

        double b;

        for( int i = 1; i <= this.pR; ++i){

            a = this.a + ( (i-1) * this.h );

            b = this.a + ( i * this.h );

            this.area += ( ( this.f(a) + this.f(b) ) / 2 ) * (b-a);
        }

        try
        {

            Integral.mutex.acquire();
            Integral.result += this.area;
            Integral.mutex.release();

            Integral.barreiraMutex.acquire();
            Integral.contador += 1;
            Integral.barreiraMutex.release();

            if(Integral.contador == Integral.p ) Integral.barreira.release();

            Integral.barreira.acquire();

            Integral.barreira.release();

            if(this.threadNumber == 0) {
                System.out.println("Resultado : " + Integral.result);

                Integral.endTime = System.currentTimeMillis();

                System.out.println("Tempo decorrido : " + (Integral.endTime - Integral.startTime));
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    private double f(double x){
        return 2 * Math.sin(x) + (Math.cos(x)/3);
    }

    private void debug(){

        System.out.println(" =========== Thread " + this.threadNumber + " ===================");
        System.out.println(" pR: " + this.pR + " , h = " + this.h + " , a = " + this.a + " , area = " + this.area);
        System.out.println(" ================================================================");
    }
}