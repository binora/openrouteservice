package org.heigit.ors.matrix.core;

import org.heigit.ors.api.requests.common.APIEnums;
import org.heigit.ors.api.requests.matrix.MatrixRequest;
import org.heigit.ors.api.requests.matrix.MatrixRequestEnums;
import org.heigit.ors.api.requests.matrix.MatrixRequestHandler;
import org.heigit.ors.api.requests.matrix.MatrixRequestOptions;
import org.heigit.ors.exceptions.ParameterValueException;
import org.heigit.ors.exceptions.StatusCodeException;
import org.heigit.ors.matrix.MatrixMetricsType;
import org.heigit.ors.matrix.MatrixResult;
import org.heigit.ors.routing.RoutingProfileManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoreMatrixGojekTest {
    private static RoutingProfileManager routingProfileManager;

    @BeforeClass
    public static void setUp() throws IOException {
        System.setProperty("ors_config", "target/test-classes/ors-config-gojek.json");
        routingProfileManager = RoutingProfileManager.getInstance();
    }

//    @AfterClass
//    public static void tearDown() throws IOException {
//        FileUtils.deleteDirectory(new File("gojek-graphs"));
//    }


    @Test
    public void TestMatrixCH() throws StatusCodeException {
        MatrixRequest matrixRequest = buildMatrixRequest();
        MatrixRequestHandler handler = new MatrixRequestHandler();
        MatrixResult matrixResult = handler.generateMatrixFromRequest(matrixRequest);

        float[] distances = matrixResult.getTable(MatrixMetricsType.DISTANCE);
        float[] durations = matrixResult.getTable(MatrixMetricsType.DURATION);

        for (float d: distances) {
            Assert.assertTrue(d >= 0);
        }

        for (float d: durations) {
            Assert.assertTrue(d >= 0);
        }

    }

    @Test
    public void TestMatrixCore() throws StatusCodeException {
        MatrixRequest matrixRequest = buildMatrixRequest();

        MatrixRequestOptions matrixRequestOptions = new MatrixRequestOptions();
        matrixRequestOptions.setDynamicSpeeds(true);

        matrixRequest.setMatrixOptions(matrixRequestOptions);

        MatrixRequestHandler handler = new MatrixRequestHandler();
        MatrixResult matrixResult = handler.generateMatrixFromRequest(matrixRequest);

        float[] distances = matrixResult.getTable(MatrixMetricsType.DISTANCE);
        float[] durations = matrixResult.getTable(MatrixMetricsType.DURATION);


        for (float d: distances) {
            Assert.assertTrue(d >= 0);
        }

        for (float d: durations) {
            Assert.assertTrue(d >= 0);
        }
    }


    private MatrixRequest buildMatrixRequest() throws ParameterValueException {
        MatrixRequest matrixRequest = new MatrixRequest(generateLocations());

        matrixRequest.setProfile(APIEnums.Profile.DRIVING_CAR);
        matrixRequest.setSources(new String[]{"all"});
        matrixRequest.setDestinations(new String[]{"89"});
        matrixRequest.setMetrics(new MatrixRequestEnums.Metrics[]{
            MatrixRequestEnums.Metrics.DISTANCE,
            MatrixRequestEnums.Metrics.DURATION
        });
        return matrixRequest;
    }

    private List<List<Double>> generateLocations() {
        List<List<Double>> locations = new ArrayList<>();
        double[][] origins = {
                {110.3947846, -7.786464199999999},
                {110.3959302, -7.7816361},
                {110.3912655, -7.787737299999999},
                {110.3927167, -7.7958368},
                {110.4007126, -7.8038318},
                {110.4002132, -7.7790075},
                {110.3948011, -7.785419999999999},
                {110.4004478, -7.8021809},
                {110.3917467, -7.7883967},
                {110.401445, -7.793147599999999},
                {110.3905217, -7.7881633},
                {110.3922683, -7.7964533},
                {110.4024267, -7.79654},
                {110.4098739, -7.8002027},
                {110.3926133, -7.7982713},
                {110.4099911, -7.7943836},
                {110.4006097, -7.799685600000001},
                {110.4123845, -7.802734900000001},
                {110.3911068, -7.788302800000001},
                {110.4014267, -7.7846677},
                {110.4073567, -7.8106},
                {110.4083572, -7.7905015},
                {110.40838, -7.7948693},
                {110.3935344, -7.7889276999999995},
                {110.4107612, -7.773121},
                {110.4121183, -7.804015},
                {110.3918567, -7.792180900000001},
                {110.4020714, -7.8077369999999995},
                {110.3997879, -7.783913299999999},
                {110.4020418, -7.807735},
                {110.4099192, -7.794269299999999},
                {110.4152367, -7.805087999999999},
                {110.4013607, -7.782982399999999},
                {110.3996901, -7.7847357},
                {110.4087714, -7.772131000000001},
                {110.4111007, -7.7986111},
                {110.4013294, -7.777880499999999},
                {110.3995084, -7.8022108},
                {110.4022355, -7.7908015},
                {110.4181763, -7.810575499999999},
                {110.3992766, -7.801920899999999},
                {110.4022583, -7.804248299999999},
                {110.39835, -7.7911265},
                {110.4024968, -7.807454699999999},
                {110.4016589, -7.8043425},
                {110.3934642, -7.789139499999999},
                {110.402675, -7.802384999999999},
                {110.3960805, -7.7785464},
                {110.392206, -7.8000709},
                {110.4049063, -7.7733429},
                {110.4078663, -7.8116078},
                {110.4021701, -7.8067873},
                {110.4025467, -7.8074333},
                {110.4019967, -7.8077},
                {110.39205, -7.799533300000001},
                {110.4106267, -7.773135},
                {110.4054695, -7.7899468999999995},
                {110.3951464, -7.802157899999999},
                {110.4134722, -7.8083685},
                {110.4024597, -7.7983264000000005},
                {110.3935525, -7.7890242},
                {110.4005619, -7.792301799999999},
                {110.3998502, -7.7856905},
                {110.4161347, -7.805599400000001},
                {110.3961213, -7.7965314},
                {110.4017901, -7.779886500000001},
                {110.4087017, -7.8118729},
                {110.3972683, -7.787176700000001},
                {110.427624, -7.7834959},
                {110.4085533, -7.79652},
                {110.4162074, -7.8017878000000005},
                {110.3949702, -7.778829199999999},
                {110.3909599, -7.7878266},
                {110.4087361, -7.81165},
                {110.4092484, -7.7718301},
                {110.4020583, -7.807326700000001},
                {110.4087992, -7.7985391},
                {110.3926986, -7.7881252},
                {110.4010577, -7.773863399999999},
                {110.416035, -7.801371100000001},
                {110.3930666, -7.7930207},
                {110.4085756, -7.8088597},
                {110.4024546, -7.798345399999999},
                {110.3981123, -7.7945488},
                {110.4100415, -7.7911393},
                {110.3948535, -7.8034428},
                {110.4110412, -7.772930399999999},
                {110.4016969, -7.8042644},
                {110.4012666, -7.7938891},
                {110.3988849, -7.783108500000001},
                {110.4103838, -7.7919362}
        };

        for (double[] doubles : origins) {
            List<Double> origin = new ArrayList<>();
            origin.add(0, doubles[0]);
            origin.add(1, doubles[1]);
            locations.add(origin);
        }

        return locations;
    }
}
