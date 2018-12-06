import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
*	Author: 	 I.M Sandaruwan
*				 E/13/315
*	Description: Draw the mandelbrot set and julia set
*/

public abstract class Fractal extends JPanel {

    //set the JFrame size
    protected static final int WIDTH = 800;
    protected static final int HEIGHT = 800;

    protected static BufferedImage canvas = new BufferedImage(Fractal.WIDTH, Fractal.HEIGHT, BufferedImage.TYPE_INT_RGB);
    ;

    //fields for fractal set to map to the frame
    protected double region_x_min = -1;
    protected double region_x_max = 1;
    protected double region_y_min = -1;
    protected double region_y_max = 1;
    protected int iterations = 1000;
    protected double unit_real, unit_complex;

    protected Color colorPoints[][] = new Color[WIDTH][HEIGHT];


    //main function for test the fractal generation
    public static void main(String[] args) {
        Fractal fractalSet = null;

        //argument handle for generate the suitable fractal to the command line argument
        try {

            //handling arguments for Mandelbrot set
            if (args[0].equals("Mandelbrot")) {
                if (args.length == 1) {
                    fractalSet = new Mandelbrot();
                    fractalSet.generateFractalColors();
                } else if (args.length == 5) {
                    double x_ = Double.parseDouble(args[1]);
                    double x = Double.parseDouble(args[2]);
                    double y_ = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);

                    fractalSet = new Mandelbrot(x_, x, y_, y);
                    fractalSet.generateFractalColors();
                } else if (args.length == 6) {
                    double x_ = Double.parseDouble(args[1]);
                    double x = Double.parseDouble(args[2]);
                    double y_ = Double.parseDouble(args[3]);
                    double y = Double.parseDouble(args[4]);
                    int iterations = Integer.parseInt(args[5]);

                    fractalSet = new Mandelbrot(x_, x, y_, y, iterations);
                    fractalSet.generateFractalColors();
                } else
                    System.out.println("Invalid argument format for Mandelbrot set");

                //handling arguments for Julia set
            } else if (args[0].equals("Julia")) {
                if (args.length == 1) {
                    fractalSet = new Julia();
                    fractalSet.generateFractalColors();
                } else if (args.length == 3) {
                    double real_C = Double.parseDouble(args[1]);
                    double complex_C = Double.parseDouble(args[2]);

                    fractalSet = new Julia(real_C, complex_C);
                    fractalSet.generateFractalColors();
                } else if (args.length == 4) {
                    double real_C = Double.parseDouble(args[1]);
                    double complex_C = Double.parseDouble(args[2]);
                    int iterations = Integer.parseInt(args[3]);

                    fractalSet = new Julia(real_C, complex_C, iterations);
                    fractalSet.generateFractalColors();
                } else
                    System.out.println("Invalid argument format for Julia set");
            } else
                System.out.println("Invalid argument, define the fractal set as \"Julia\" or \"Mandelbrot\"");

            if (fractalSet != null) {
                JFrame frame = new JFrame("Fractal");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setContentPane(fractalSet);
                frame.setPreferredSize(new Dimension(WIDTH - 1, WIDTH - 1));
                frame.setSize(WIDTH - 1, WIDTH - 1);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        } catch (Exception e) {
            System.out.println("Invalid argument, define the type of fractal set");
        }
        
    }

    //set the units for map the interest region to the JFrame
    protected void unitCalculation() {
        this.unit_real = (region_x_max - region_x_min) / WIDTH;
        this.unit_complex = (region_y_max - region_y_min) / HEIGHT;
    }

    abstract protected Color generate_set(Complex point);

    protected void generateFractalColors() {
        int numOfThreads = 4;
        Thread threads[] = new ColorSetThread[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threads[i] = new ColorSetThread(i, WIDTH / numOfThreads);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numOfThreads; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            System.out.println("Error: joining threads!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(canvas, 0, 0, this);

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                canvas.setRGB(i, j, colorPoints[i][j].getRGB());
            }
        }

    }

    //inner class to find the colors of the frame using threads
    private class ColorSetThread extends Thread {
        private int threadID;
        private int threadRange;

        ColorSetThread(int numOfThread, int threadRange) {
            this.threadID = numOfThread;
            this.threadRange = threadRange;
        }

        @Override
        public void run() {
            Complex point;
            for (int i = threadID * threadRange; i < (threadID + 1) * threadRange; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    point = new Complex(i, j);
                    colorPoints[i][j] = generate_set(point);
                }
            }
        }
    }
}

