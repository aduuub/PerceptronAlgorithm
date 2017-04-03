package Perceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Adam on 1/04/17.
 */
public class Perceptron {
    // Constants
    public static int MAX_ITERATION = 100;
    public static int NUM_OF_FEATURES = 100;
    public static final Random random = new Random(); // keep consistent seed for testing

    // Fields
    private String categoryName; // E.g. "Yes"

    private List<Image> images;
    private List<Feature> features;
    private List<double[]> featureValues;
    private double[] weights;


    public Perceptron(List<Image> images, String categoryName) {
        this.images = images;
        this.categoryName = categoryName;
        initialise();
        learn();
    }


    /**
     * Initialise the perceptron algorithm.
     */
    public void initialise() {
        // Init features
        features = new ArrayList<>();
        for (int i = 0; i < NUM_OF_FEATURES; i++)
            features.add(new Feature());

        // Init weights to small random values
        weights = new double[NUM_OF_FEATURES + 1]; // One extra for threshold dummy value
        for (int i = 0; i < weights.length; i++)
            weights[i] = random.nextDouble() - 0.5; // Random value between -0.5 to 0.49

        // Init feature values for each image
        featureValues = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            featureValues.add(Evaluation.evaluateFeatures(features, images.get(i)));
        }
    }


    /**
     * Learn the images
     */
    public void learn() {
        int correct = 0;
        int epoch = 0;
        for (; epoch < MAX_ITERATION; epoch++) {

            // Check to see if they are all correct
            if (correct == images.size())
                break;

            // Loop over images
            for (int imgIndex = 0; imgIndex < images.size(); imgIndex++) {
                Image image = images.get(imgIndex);
                double[] imageFeature = featureValues.get(imgIndex);

                // Evaluate
                double perceptronValue = Evaluation.evaluatePerceptron(weights, imageFeature);

                // Check class
                boolean positive = image.getCategory().equals(categoryName);

                if ((perceptronValue > 0 && positive) || (perceptronValue <= 0 && !positive)) {
                    // Correct. Do nothing

                } else if (!positive) {
                    // -ve and wrong (weights on features are too high)
                    // Subtract feature vector from weight vector
                    for (int i = 0; i < weights.length; i++)
                        weights[i] -= imageFeature[i];

                } else {
                    // +ve and wrong (weights on feature are too low)
                    // Add feature vector to weight vector
                    for (int i = 0; i < weights.length; i++)
                        weights[i] += imageFeature[i];

                }
                // Calculate correctness
                correct = Evaluation.testCorrectness(featureValues, weights, images, categoryName);
            }
            // Display correctness every 5 iterations
            if (epoch % 5 == 0)
                printCorrectness(correct, epoch);
        }
        // Display correctness once finished predicting images
        printCorrectness(correct, epoch);
    }

    /**
     * Print the percentage correct the algorithm is on the the specified epoch.
     * @param correct
     * @param epoch
     */
    private void printCorrectness(double correct, int epoch){
        System.out.printf("Correct %.2f%% after %d epochs\n", 100 * correct / (double) images.size(), epoch);
    }


    /**
     * Ruuuun the bish
     *
     * @param args
     */

    public static void main(String args[]) {
        if(args.length != 1){
            System.out.println("Run program with arg: \"filename\"");
            return;
        }
        Parser parser = new Parser();
        List<Image> images = parser.loadImages(args[0]);
        String categoryName = parser.getCategoryName();
        new Perceptron(images, categoryName);
    }

}
