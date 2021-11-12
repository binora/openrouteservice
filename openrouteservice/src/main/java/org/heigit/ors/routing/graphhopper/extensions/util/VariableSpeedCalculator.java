package org.heigit.ors.routing.graphhopper.extensions.util;

import com.graphhopper.routing.EdgeKeys;
import com.graphhopper.routing.util.AbstractAdjustedSpeedCalculator;
import com.graphhopper.routing.util.SpeedCalculator;
import com.graphhopper.util.EdgeIteratorState;
import org.heigit.ors.routing.graphhopper.extensions.storages.SpeedStorage;

public class VariableSpeedCalculator extends AbstractAdjustedSpeedCalculator {
    private SpeedStorage speedStorage;

    public VariableSpeedCalculator(SpeedCalculator superSpeedCalculator, SpeedStorage speedStorage) {
        super(superSpeedCalculator);
        this.speedStorage = speedStorage;
    }

    public double getSpeed(EdgeIteratorState edge, boolean reverse, long time) {
        int edgeId = EdgeKeys.getOriginalEdge(edge);
        double modifiedSpeed = speedStorage.getSpeed(edgeId, reverse);
        if (modifiedSpeed == Byte.MIN_VALUE || modifiedSpeed == 0.0)
            return this.superSpeedCalculator.getSpeed(edge, reverse, time);

        return modifiedSpeed;
    }

}
