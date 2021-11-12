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

        for (float d : distances) {
            Assert.assertTrue(d >= 0);
        }

        for (float d : durations) {
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


        List<Integer> negativeDistances = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            float d = distances[i];
            if (d < 0) {
                negativeDistances.add(i);
            }
        }


        System.out.println("no. of negative distances: " + negativeDistances.size());

        System.out.println("Faulty OD pair:   origin: " + matrixRequest.getLocations().get(0) + ", destination: " + matrixRequest.getLocations().get(89));

        List<Integer> negativeDurations = new ArrayList<>();
        for (int i = 0; i < durations.length; i++) {
            float d = distances[i];
            if (d < 0) {
                negativeDurations.add(i);
            }
        }

        System.out.println(negativeDurations);

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

    // This OD pair is derived from the failing TestMatrixCore test
    @Test
    public void TestMatrixCore_FaultyODPair() throws StatusCodeException {
        Double[][] locations = new Double[2][];
        locations[0] = new Double[]{110.3567555, -7.8012379};
        locations[1] = new Double[]{110.3624255, -7.8139259};

        MatrixRequest matrixRequest = new MatrixRequest(locations);
        matrixRequest.setProfile(APIEnums.Profile.DRIVING_CAR);
        matrixRequest.setMetrics(new MatrixRequestEnums.Metrics[]{
                MatrixRequestEnums.Metrics.DISTANCE,
                MatrixRequestEnums.Metrics.DURATION
        });

        MatrixRequestOptions matrixRequestOptions = new MatrixRequestOptions();
        matrixRequestOptions.setDynamicSpeeds(true);

        matrixRequest.setMatrixOptions(matrixRequestOptions);

        MatrixRequestHandler handler = new MatrixRequestHandler();
        MatrixResult matrixResult = handler.generateMatrixFromRequest(matrixRequest);

        float[] distances = matrixResult.getTable(MatrixMetricsType.DISTANCE);
        float[] durations = matrixResult.getTable(MatrixMetricsType.DURATION);


        List<Integer> negativeDistances = new ArrayList<>();
        for (int i = 0; i < distances.length; i++) {
            float d = distances[i];
            if (d < 0) {
                negativeDistances.add(i);
            }
        }

        List<Integer> negativeDurations = new ArrayList<>();
        for (int i = 0; i < durations.length; i++) {
            float d = distances[i];
            if (d < 0) {
                negativeDurations.add(i);
            }
        }

        for (float d: distances) {
            Assert.assertTrue(d >= 0);
        }

        for (float d: durations) {
            Assert.assertTrue(d >= 0);
        }

    }

    private List<List<Double>> generateLocations() {
        List<List<Double>> locations = new ArrayList<>();
        double[][] origins = {
                {110.3567555, -7.8012379},
                {110.3690278, -7.8085344},
                {110.3543654, -7.802529100000001},
                {110.3778336, -7.806080699999999},
                {110.368396, -7.8153317},
                {110.3773617, -7.808645},
                {110.3571958, -7.802797399999999},
                {110.3665719, -7.801498799999999},
                {110.3689388, -7.810130899999999},
                {110.3712367, -7.80917},
                {110.3658857, -7.8033284},
                {110.3520283, -7.7996867},
                {110.3583375, -7.8010649},
                {110.3718726, -7.800517999999999},
                {110.3626488, -7.8138116},
                {110.3641228, -7.797279799999999},
                {110.3696669, -7.814827700000001},
                {110.3570096, -7.801130199999999},
                {110.3641201, -7.797197499999999},
                {110.3772798, -7.8090114},
                {110.3612448, -7.812229100000001},
                {110.3609448, -7.8061064},
                {110.3767186, -7.8044269},
                {110.3589963, -7.801154},
                {110.3669557, -7.8126427},
                {110.362308, -7.8013126},
                {110.3630345, -7.804655399999999},
                {110.3663961, -7.8103725},
                {110.3607062, -7.8030702000000005},
                {110.3545255, -7.8004265},
                {110.3578828, -7.8089664},
                {110.3734861, -7.803785000000001},
                {110.3519584, -7.7999381},
                {110.3617502, -7.8087336999999994},
                {110.3747449, -7.8011517},
                {110.3693126, -7.8154471999999995},
                {110.3697648, -7.789458899999999},
                {110.3607479, -7.8012782},
                {110.3744816, -7.8014904000000005},
                {110.3585752, -7.8012539},
                {110.3753765, -7.8060578},
                {110.362075, -7.7983154},
                {110.3551653, -7.7996233},
                {110.3615536, -7.797792899999999},
                {110.3683642, -7.8012352},
                {110.3532403, -7.8066438},
                {110.3684432, -7.800438},
                {110.3551433, -7.7999},
                {110.3780664, -7.8071156},
                {110.352021, -7.800002899999999},
                {110.3731618, -7.798885000000001},
                {110.3774811, -7.8012884},
                {110.3623581, -7.8059699},
                {110.3676259, -7.8037952},
                {110.359017, -7.8070439},
                {110.3686217, -7.7951730999999995},
                {110.3558443, -7.7993505},
                {110.3521097, -7.7996194999999995},
                {110.3664394, -7.803005200000001},
                {110.3611, -7.80798},
                {110.3688129, -7.812864799999999},
                {110.3783712, -7.8077418},
                {110.3748911, -7.7996341},
                {110.3744583, -7.812919999999999},
                {110.3600508, -7.800902899999999},
                {110.3597433, -7.808312600000001},
                {110.3697987, -7.814767099999999},
                {110.3629133, -7.80228},
                {110.3610009, -7.8057426},
                {110.3688783, -7.813009800000001},
                {110.3736308, -7.8017366},
                {110.3556977, -7.7976999},
                {110.3518581, -7.8038354},
                {110.3586534, -7.811123300000001},
                {110.3671905, -7.803542600000001},
                {110.3690731, -7.8012593},
                {110.373458, -7.8015667},
                {110.379205, -7.8018867},
                {110.3690333, -7.795751},
                {110.3780341, -7.8084322},
                {110.3771831, -7.809436099999999},
                {110.360383, -7.8044497999999995},
                {110.3744745, -7.801820400000001},
                {110.3690337, -7.8097639},
                {110.3607757, -7.806905},
                {110.3573754, -7.808720500000001},
                {110.3651656, -7.799567800000001},
                {110.3612018, -7.7942518},
                {110.3605635, -7.804402700000001},
                {110.3624255, -7.8139259},
                {110.3655601, -7.8022842}
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