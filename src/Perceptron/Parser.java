package Perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Adam on 1/04/17.
 */
public class Parser {

    private String categoryName;

    public Parser(){

    }

    /**
     * Open a file chooser and load images from the file
     * @return
     */
    public List<Image> loadImages(String fileName) {
        // Create regex, scanner and image list
        java.util.regex.Pattern bit = java.util.regex.Pattern.compile("[01]");
        Scanner f = null;
        try {
            f = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            f.close();
            e.printStackTrace();
        }
        List<Image> images = new ArrayList<>();

        while (f.hasNext()) {
            // Check image type
            if (!f.next().equals("P1"))
                System.out.println("Not a P1 PBM file");

            // Read category
            String category = f.next().substring(1);
            if (!category.equals("other"))
                categoryName = category;

            // Read rows and cols
            int rows = f.nextInt();
            int cols = f.nextInt();

            // Read data
            boolean[][] data = new boolean[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    data[r][c] = (f.findWithinHorizon(bit, 0).equals("1"));
                }
            }

            Image image = new Image(data, category);
            images.add(image);
        }
        f.close();

        System.out.printf("Read %d images \n", images.size());
        return images;
    }


    public String getCategoryName() {
        return categoryName;
    }
}
