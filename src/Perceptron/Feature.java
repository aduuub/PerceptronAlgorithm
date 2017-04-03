package Perceptron;

/**
 * Created by Adam on 1/04/17.
 */
public class Feature {
    private int[] row;
    private int[] col;
    private boolean[] sign;

    public static final int IMG_SIZE = 10;

    /**
     * Create a new feature and generate random values for row, col and sign
     */
    public Feature(){
        row = new int[4];
        col = new int[4];
        sign = new boolean[4];

        // Generate random connections and values
        for(int i=0; i < 4; i++){
            row[i] = Perceptron.random.nextInt(IMG_SIZE);
            col[i] = Perceptron.random.nextInt(IMG_SIZE);
            sign[i] = Perceptron.random.nextBoolean();
        }
    }


    /**
     * Checks how well the image matches the feature
     * @param image
     * @return '1' if at least 3 pixels match, else return '0'.
     */
    public int match(Image image){
        int count = 0;
        for(int i=0; i < 4; i++){
            if(image.get(row[i], col[i]) == sign[i])
                count++;
        }

        return count >= 3 ? 1 : 0;
    }
}
