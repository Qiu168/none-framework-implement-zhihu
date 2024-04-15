import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


//todo:实现图片转像素画
public class Photo2PixelModelJava {
    @Test
    public void testReflectionPad2d(){
        // Example usage
        double[][] matrixData = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RealMatrix matrix =new Array2DRowRealMatrix(matrixData);

        int[] padding = {1, 1, 1, 1};
        RealMatrix paddedMatrix = Matrix.reflectionPad2d(matrix, padding);

        System.out.println(paddedMatrix);
    }
}
interface Matrix{
    static double[][] convolution(double[][] source, double[][] operator){
        int sourceRows = source.length;
        int sourceCols = source[0].length;
        int operatorRows = operator.length;
        int operatorCols = operator[0].length;

        // 计算卷积结果的矩阵大小
        int resultRows = sourceRows - operatorRows + 1;
        int resultCols = sourceCols - operatorCols + 1;

        // 创建卷积结果矩阵
        double[][] result = new double[resultRows][resultCols];

        // 执行卷积操作
        for (int i = 0; i < resultRows; i++) {
            for (int j = 0; j < resultCols; j++) {
                for (int m = 0; m < operatorRows; m++) {
                    for (int n = 0; n < operatorCols; n++) {
                        result[i][j] += source[i + m][j + n] * operator[m][n];
                    }
                }
            }
        }
        return result;
    }
    static RealMatrix reflectionPad2d(RealMatrix source, int[] padding) {
        int numRows = source.getRowDimension();
        int numCols = source.getColumnDimension();

        int top = padding[0];
        int bottom = padding[1];
        int left = padding[2];
        int right = padding[3];

        int newRows = numRows + top + bottom;
        int newCols = numCols + left + right;

        RealMatrix paddedMatrix = new Array2DRowRealMatrix(newRows, newCols);

        // Copy the original matrix to the center of the padded matrix
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                paddedMatrix.setEntry(i + top, j + left, source.getEntry(i, j));
            }
        }

        // Reflect and fill the top and bottom
        for (int i = 0; i < top; i++) {
            for (int j = 0; j < newCols; j++) {
                paddedMatrix.setEntry(i, j, paddedMatrix.getEntry(2 * top - i, j));
                paddedMatrix.setEntry(newRows - 1 - i, j, paddedMatrix.getEntry(newRows - 1 - 2 * top + i, j));
            }
        }

        // Reflect and fill the left and right
        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < left; j++) {
                paddedMatrix.setEntry(i, j, paddedMatrix.getEntry(i, 2 * left - j));
                paddedMatrix.setEntry(i, newCols - 1 - j, paddedMatrix.getEntry(i, newCols - 1 - 2 * left + j));
            }
        }

        return paddedMatrix;
    }

    static RealMatrix abs(RealMatrix source){
        int rows = source.getRowDimension();
        int columns = source.getColumnDimension();

        // 创建一个新的矩阵，用于存储绝对值
        RealMatrix result = source.createMatrix(rows, columns);

        // 遍历源矩阵的每个元素，计算绝对值并存储到结果矩阵中
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value = Math.abs(source.getEntry(i, j));
                result.setEntry(i, j, value);
            }
        }

        return result;
    }

    static RealMatrix getMax(RealMatrix edgeH, RealMatrix edgeW) {
        int rows = edgeH.getRowDimension();
        int columns = edgeH.getColumnDimension();

        // 创建一个新的矩阵，用于存储绝对值
        RealMatrix result = edgeH.createMatrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double value = Math.max(edgeH.getEntry(i, j),edgeW.getEntry(i,j));
                result.setEntry(i, j, value);
            }
        }
        return result;
    }

    static void gt(RealMatrix matrix, double threshold, double big, double small) {
        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double r = matrix.getEntry(i, j);
                if (r > threshold) {
                    matrix.setEntry(i, j, big);
                } else {
                    matrix.setEntry(i, j, small);
                }
            }
        }

    }
    static RealMatrix mean(RealMatrix[] matrix) {
        int rows = matrix[0].getRowDimension();
        int cols = matrix[0].getColumnDimension();
        RealMatrix ans=new Array2DRowRealMatrix(rows,cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double mid=0;
                for (RealMatrix realMatrix : matrix) {
                    double r = realMatrix.getEntry(i, j);
                    mid+=r;
                }
                ans.setEntry(i, j, mid/3);
            }
        }
        return ans;
    }
    static RealMatrix maxPool2d(RealMatrix matrix, int kernelSize, int stride, int padding) {
        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();

        int pooledRows = (rows + 2 * padding - kernelSize) / stride + 1;
        int pooledCols = (cols + 2 * padding - kernelSize) / stride + 1;

        RealMatrix pooledMatrix = new Array2DRowRealMatrix(pooledRows, pooledCols);

        for (int i = 0; i < pooledRows; i++) {
            for (int j = 0; j < pooledCols; j++) {
                int startRow = i * stride - padding;
                int startCol = j * stride - padding;

                double maxVal = Double.MIN_VALUE;
                for (int m = 0; m < kernelSize; m++) {
                    for (int n = 0; n < kernelSize; n++) {
                        int currentRow = startRow + m;
                        int currentCol = startCol + n;

                        if (currentRow >= 0 && currentRow < rows && currentCol >= 0 && currentCol < cols) {
                            maxVal = Math.max(maxVal, matrix.getEntry(currentRow, currentCol));
                        }
                    }
                }

                pooledMatrix.setEntry(i, j, maxVal);
            }
        }

        return pooledMatrix;
    }
}

class ImageConversion {

    public static RealMatrix[] convertImageToMatrices(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        RealMatrix[] channelMatrices = new RealMatrix[3];

        for (int channel = 0; channel < 3; channel++) {
            double[][] matrixData = new double[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = img.getRGB(x, y);
                    double channelValue = ((rgb >> (16 - channel * 8)) & 0xFF) / 255.0;
                    matrixData[y][x] = channelValue;
                }
            }

            channelMatrices[channel]=new Array2DRowRealMatrix(matrixData);
        }

        return channelMatrices;
    }

    public static BufferedImage convertMatrixToImage(RealMatrix matrix, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = (int) (matrix.getEntry(y, x) * 255.0);
                int g = (int) (matrix.getEntry(y, x) * 255.0);
                int b = (int) (matrix.getEntry(y, x) * 255.0);

                int rgb = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgb);
            }
        }

        return img;
    }

    public static void main(String[] args) throws IOException {
        // 示例用法
        BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\_qqiu\\Desktop\\微信图片\\IMG_3075(20231123-225957).JPG"));

        // 将 BufferedImage 转换为 RealMatrix
        RealMatrix[] matrixArray = convertImageToMatrices(originalImage);

        // 对矩阵进行一些处理（例如，应用过滤器或修改像素值）
        //matrix=Matrix.reflectionPad2d(matrix,new int[]{1,1,1,1});
        for (int i = 0; i < matrixArray.length; i++) {
            RealMatrix matrix;
            matrix=Matrix.reflectionPad2d(matrixArray[i],new int[]{1,1,1,1});
            double[][] data = matrix.getData();
            double[][] convolutionH = Matrix.convolution(data, new double[][]{
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}});
            double[][] convolutionW=Matrix.convolution(data,new double[][]{
                    {-1,-2,-1},
                    {0,0,0},
                    {1,2,1}});
            RealMatrix edgeH=new Array2DRowRealMatrix(convolutionH);
            RealMatrix edgeW=new Array2DRowRealMatrix(convolutionW);
            edgeH=Matrix.abs(edgeH);
            edgeW=Matrix.abs(edgeW);
            matrix = Matrix.getMax(edgeH, edgeW);
            matrixArray[i]=matrix;
        }
        RealMatrix matrix=Matrix.mean(matrixArray);
        Matrix.gt(matrix,  0.5,1.0,0.0);
        int param_edge_dilate=3;
        matrix = Matrix.maxPool2d(matrix, param_edge_dilate, 1, param_edge_dilate / 2);
        // 将 RealMatrix 转换回 BufferedImage
        BufferedImage resultImage = convertMatrixToImage(matrix, originalImage.getWidth(), originalImage.getHeight());

        // Save the result image
        ImageIO.write(resultImage, "png", new File("abc.png"));
    }
}