/*
*   Author:      I.M Sandaruwan
*                E/13/315
*   Description: This class will model a complex number and do basic complex arithmetic
*/

public class Complex{
    private double real,imaginary;

    Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    //square of a complex number
    public Complex squared() {
        return new Complex((real * real - imaginary * imaginary), (2 * real * imaginary));
    }

    //addition of two complex numbers
    public Complex add(Complex number) {
        return new Complex((real + number.getReal()), (imaginary + number.getImaginary()));
    }

    //absolute square of a complex number
    public double absSquared() {
        return real * real + imaginary * imaginary;
    }

    public String toString() {
        return "[" + real + ", " + imaginary +"i"+ "]";
    }
}