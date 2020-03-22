package myLab.controller;

import org.jfree.ui.RefineryUtilities;

import java.util.Arrays;
import java.util.Random;

public class Lab4{

    private static int n = 8; //число гармоник
    private static int N = 1024; // колво точек
    private static int Wgr = 1200; // граничная частота
    private static double rangeMin = 0;
    private static double rangeMax = 100;
    //private static int[] arrayN = {500,1000,1500,2000,2500};
    private static double[] signal = new double[N];
    private static double[][] w_coeff = new double[N/2][N/2];
    private static double[] w_coeff_new = new double[N];
    private static double[] F_I = new double[N/2];
    private static double[] F_II = new double[N/2];
    private static double[] F = new double[N];

    private static Random random = new Random();

    public static void main(String[] args) {
        signal = makeSignal();
        makePlot("Signal",signal);


        for (int p = 0; p < w_coeff.length; p++) {
            for (int k = 0; k < w_coeff[p].length; k++) {
                w_coeff[p][k] = Math.cos((4*Math.PI/N)*p*k) + Math.sin((4*Math.PI/N)*p*k);
            }
        }

        for (int p = 0; p < w_coeff_new.length; p++) {
            w_coeff_new[p] = Math.cos((2*Math.PI/N)*p) + Math.sin((2*Math.PI/4)*p);
        }


        Arrays.fill(F, 0);
        Arrays.fill(F_I, 0);
        Arrays.fill(F_II, 0);

        for (int p = 0; p < N/2; p++) {
            for (int k = 0; k < N/2; k++) {
                F_II[p] += signal[2*k] *w_coeff[p][k];
                F_I[p] += signal[2*k+1]*w_coeff[p][k];
            }
        }

        for (int p = 0; p < N; p++) {
            if (p<N/2){
                F[p] += F_II[p]+w_coeff_new[p]*F_I[p];
            }else {
                F[p] += F_II[p-(N/2)] - w_coeff_new[p]*F_I[p-(N/2)];
            }
        }

        makePlot("FFT",F);

    }

    private static double[] makeSignal() {
        for (int i = 0; i < N; i++) {
            signal[i] = 0;
        }




        for (int i = 1; i < n+1; i++) {
            double ai = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            double fi = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            for (int j = 0; j < N; j++) {
                signal[j] += ai * Math.sin((((double) Wgr/i * (j + 1))) + fi);
            }
        }
        return signal;
    }

    private static void makePlot(String title,double[] arr) {
        final XYSeriesDemo demo = new XYSeriesDemo(title,arr);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }


}
