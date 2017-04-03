package Perceptron;

/**
 * Created by Adam on 1/04/17.
 */
public class Image {

    private boolean[][] data;
    private String category; // "Yes" "other"


    public Image(boolean[][] data, String category){
        this.data = data;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public boolean get(int row, int col) {
        return data[row][col];
    }
}
