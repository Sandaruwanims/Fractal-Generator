import java.awt.*;

/*
*	Author: 	 I.M Sandaruwan
*				 E/13/315
*	Description: Draw the julia set
*/

public class Julia extends Fractal {

    private Complex c_complex = new Complex(-0.4, 0.6);

    //constructors for different command line arguments
    public Julia() {
        unitCalculation();
    }

    public Julia(double real_C, double complex_C) {
        this.c_complex.setReal(real_C);
        this.c_complex.setImaginary(complex_C);
        unitCalculation();
    }

    public Julia(double real_C, double complex_C, int iterations) {
        this.c_complex.setReal(real_C);
        this.c_complex.setImaginary(complex_C);
        this.iterations = iterations;
        unitCalculation();
    }

    @Override
    public Color generate_set(Complex point) {
        Color color = Color.black;

        point.setReal(region_x_min + point.getReal() * this.unit_real);
        point.setImaginary(region_y_max - point.getImaginary() * this.unit_complex);

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
