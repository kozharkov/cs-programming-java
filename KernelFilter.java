import java.awt.Color;

public class KernelFilter {

    // Returns a new picture that applies a Gaussian blur filter to the given picture.
    public static Picture gaussian(Picture picture) {
        double a = 1.0 / 16;
        double[][] gaussian = {{a, 2 * a, a}, {2 * a, 4 * a, 2 * a}, {a, 2 * a, a}};
        return kernel(picture, gaussian);
    }

    // Returns a new picture that applies a sharpen filter to the given picture.
    public static Picture sharpen(Picture picture) {
        double[][] sharp = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        return kernel(picture, sharp);
    }

    // Returns a new picture that applies an Laplacian filter to the given picture.
    public static Picture laplacian(Picture picture) {
        double[][] lap = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
        return kernel(picture, lap);
    }

    // Returns a new picture that applies an emboss filter to the given picture.
    public static Picture emboss(Picture picture) {
        double[][] emb = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};
        return kernel(picture, emb);
    }

    // Returns a new picture that applies a motion blur filter to the given picture.
    public static Picture motionBlur(Picture picture) {
        double a = 1.0 / 9;
        double[][] mot = {{a, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, a, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, a, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, a, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, a, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, a, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, a, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, a, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, a}
        };
        return kernel(picture, mot);
    }

    private static Picture kernel(Picture picture, double[][] kernel) {
        int w = picture.width();
        int h = picture.height();
        Picture target = new Picture(w, h);
        for (int tcol = 0; tcol < w; tcol++)
            for (int trow = 0; trow < h; trow++) {
                // Color color = picture.get(tcol, trow);
                target.set(tcol, trow, applyKernel(selectArea(picture, kernel.length, tcol, trow), kernel));
            }
        return target;
    }

    private static Color applyKernel(Picture p, double[][] kernel) {
        /* if ((kernel.length != kernel[0].length) || (p.width() != kernel.length) || (p.height() != kernel[0].length)) {
            // StdOut.println("cannot apply kernel, mismatch of size...");
            return Color.BLACK;
        } */
        int outRed = 0;
        int outGreen = 0;
        int outBlue = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                outRed += Math.round(p.get(i, j).getRed() * kernel[i][j]);
                outGreen += Math.round(p.get(i, j).getGreen() * kernel[i][j]);
                outBlue += Math.round(p.get(i, j).getBlue() * kernel[i][j]);
            }
        }
        if (outRed < 0) outRed = 0;
        if (outRed > 255) outRed = 255;
        if (outGreen < 0) outGreen = 0;
        if (outGreen > 255) outGreen = 255;
        if (outBlue < 0) outBlue = 0;
        if (outBlue > 255) outBlue = 255;
        // StdOut.println("red: " + outRed + ", green: " + outGreen + ", blue: " + outBlue);
        return new Color(outRed, outGreen, outBlue);
    }

    private static Picture selectArea(Picture picture, int size, int pcol, int prow) {
        // StdOut.println("selecting area of size " + size + "x" + size + " from positiion pcol=" + pcol + ", prow = " + prow + " out of picture " + picture.width() + "x" + picture.height());
        Picture pic = new Picture(size, size);
        for (int col = 0; col < pic.width(); col++)
            for (int row = 0; row < pic.height(); row++)
            {
                int scol = col - ((size / 2) - pcol);
                int srow = row - ((size / 2) - prow);
                // StdOut.println("initial: scol = " + scol + ", srow = " + srow);
                if (scol < 0) {
                    scol = picture.width() - 2 - scol;
                    // StdOut.println("scol is negative, adjusted scol = " + scol);
                }
                if (scol > picture.width() - 1) {
                    scol = scol - picture.width();
                    // StdOut.println("scol is too big, adjusted scol = " + scol);
                }
                if (srow < 0) {
                    srow = picture.height() - 2 - srow;
                    // StdOut.println("srow is negative, adjusted srow = " + scol);
                }
                if (srow > picture.height() - 1) {
                    srow = srow - picture.height();
                    // StdOut.println("srow is too big, adjusted srow = " + scol);
                }
                // StdOut.println("for target col = " + col + ", row = " + row + " selecting source scol = " + scol + ", srow = " + srow);
                Color c = picture.get(scol, srow);
                pic.set(col, row, c);
            }
        return pic;
    }

    // Test client (ungraded).
    public static void main(String[] args)
    {
        Picture pic = new Picture("baboon.png");
        double[][] ident = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        Picture picId = kernel(pic, ident);
        picId.show();
        Picture pic2 = gaussian(pic);
        pic2.show();
        Picture pic4 = sharpen(pic);
        pic4.show();
        Picture pic3 = laplacian(pic);
        pic3.show();
        Picture pic5 = emboss(pic);
        pic5.show();
        Picture pic1 = motionBlur(pic);
        pic1.show();
    }

}
