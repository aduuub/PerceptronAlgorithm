package Perceptron;

import java.util.List;

/**
 * Created by Adam on 1/04/17.
 */
public class Evaluation {

    /**
     * Evaluate the features for the image specified.
     *
     * @param features
     * @param image
     * @return - feature values for the particular image
     */
    public static double[] evaluateFeatures(List<Feature> features, Image image) {
        double[] vals = new double[features.size() + 1]; // Add one for the dummy value
        vals[0] = 1; // dummy value

        for (int i = 1; i < features.size(); i++) {
            Feature feature = features.get(i);
            vals[i] = feature.match(image);
        }
        return vals;
    }


    /**
     * Evalute the perception by summing the (weight by the feature value) for each index
     * @param weights
     * @param featureValues
     * @return
     */
    public static double evaluatePerceptron(double[] weights, double[] featureValues) {
        assert weights.length == featureValues.length;
        double val = 0;

        for (int i = 0; i < weights.length; i++) {
            // Weight * feature
            val += weights[i] * featureValues[i];
        }
        return val;
    }


    /**
     *
     * @param featureValues
     * @param weights
     * @return
     */
    public static int testCorrectness(List<double[]> featureValues, double[] weights, List<Image> images, String categoryName){
        int numCorrect = 0;
        for(int i=0; i < featureValues.size(); i++){
            double[] featureVal = featureValues.get(i);
            double p =  evaluatePerceptron(weights, featureVal);
            boolean isPositive = images.get(i).getCategory().equals(categoryName);
            if ((p > 0 && isPositive) || (p <= 0 && !isPositive))
                numCorrect++;
        }
        return numCorrect;
    }
}
