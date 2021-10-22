package org.heigit.ors.matrix.core;

import org.heigit.ors.api.requests.common.APIEnums;
import org.heigit.ors.api.requests.matrix.MatrixRequest;
import org.heigit.ors.api.requests.matrix.MatrixRequestHandler;
import org.heigit.ors.api.requests.matrix.MatrixRequestOptions;
import org.heigit.ors.exceptions.StatusCodeException;
import org.heigit.ors.matrix.MatrixMetricsType;
import org.heigit.ors.matrix.MatrixResult;
import org.heigit.ors.routing.RoutingProfileManager;
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
        MatrixResult matrixResult = MatrixRequestHandler.generateMatrixFromRequest(matrixRequest);

        float[] distances = matrixResult.getTable(MatrixMetricsType.DISTANCE);
        float[] durations = matrixResult.getTable(MatrixMetricsType.DURATION);

    }

    @Test
    public void TestMatrixCore() throws StatusCodeException {
        MatrixRequest matrixRequest = buildMatrixRequest();

        MatrixRequestOptions matrixRequestOptions = new MatrixRequestOptions();
        matrixRequestOptions.setDynamicSpeeds(true);

        matrixRequest.setMatrixOptions(matrixRequestOptions);

        MatrixResult matrixResult = MatrixRequestHandler.generateMatrixFromRequest(matrixRequest);

        float[] distances = matrixResult.getTable(MatrixMetricsType.DISTANCE);
        float[] durations = matrixResult.getTable(MatrixMetricsType.DURATION);

    }

    private List<List<Double>> generateLocations() {
        List<List<Double>> locations = new ArrayList<>();

        List<Double> origin = new ArrayList<>();
        origin.add(0, 110.3947846);
        origin.add(1,-7.786464199999999);

        List<Double> destination = new ArrayList<>();
        destination.add(0, 110.4012666);
        destination.add(1,-7.7938891);

        locations.add(0, origin);
        locations.add(1, destination);

        return locations;
    }

    private MatrixRequest buildMatrixRequest() {
        MatrixRequest matrixRequest = new MatrixRequest(generateLocations());

        matrixRequest.setProfile(APIEnums.Profile.DRIVING_CAR);
        matrixRequest.setSources(new String[]{"all"});
        matrixRequest.setDestinations(new String[]{"all"});
        return matrixRequest;
    }

}

