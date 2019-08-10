import java.awt.*;

/*
*   Author:      I.M Sandaruwan
*                E/13/315
*   Description: Draw the mandelbrot set.
*/

public class Mandelbrot extends Fractal {

    private Complex c_complex = new Complex(0, 0);


    //constructors for different command line arguments
    public Mandelbrot() {
        unitCalculation();
    }

    public Mandelbrot(double region_x_min, double region_x_max, double region_y_min, double region_y_max) {
        this.region_x_min = region_x_min;
        this.region_x_max = region_x_max;
        this.region_y_min = region_y_min;
        this.region_y_max = region_y_max;
        unitCalculation();
    }

    public Mandelbrot(double region_x_min, double region_x_max, double region_y_min, double region_y_max, int iterations) {
        this.region_x_min = region_x_min;
        this.region_x_max = region_x_max;
        this.region_y_min = region_y_min;
        this.region_y_max = region_y_max;
        this.iterations = iterations;
        unitCalculation();
    }

    @Override
    public Color generate_set(Complex point) {
        Color color = Color.black;

        c_complex.setReal(region_x_min + point.getReal() * this.unit_real);
        c_complex.setImaginary(region_y_max - point.getImaginary() * this.unit_complex);

        point.setReal(0);
        point.setImaginary(0);

        for (int count = 0; count < this.iterations; count++) {
            double abs_Z = point.absSquared();

            if (abs_Z > 4) {
                double colorRange = ((double) count)/(this.iterations);
                color = new Color((int)(60.0*colorRange),(int)(150.0*colorRange),(int)(255.0*colorRange));
                return color;
            }
            point = (point.squared()).add(c_complex);
        }

        return color;
    }
}
