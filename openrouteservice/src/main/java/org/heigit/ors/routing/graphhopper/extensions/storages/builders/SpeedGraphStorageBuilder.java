/*  This file is part of Openrouteservice.
 *
 *  Openrouteservice is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.

 *  This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.

 *  You should have received a copy of the GNU Lesser General Public License along with this library;
 *  if not, see <https://www.gnu.org/licenses/>.
 */
package org.heigit.ors.routing.graphhopper.extensions.storages.builders;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.storage.GraphExtension;
import com.graphhopper.util.EdgeIteratorState;
import org.heigit.ors.routing.graphhopper.extensions.storages.SpeedStorage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class SpeedGraphStorageBuilder extends AbstractGraphStorageBuilder {
    private SpeedStorage storage;
    private ConcurrentHashMap edgeIdToSpeed = new ConcurrentHashMap();

    public GraphExtension init(GraphHopper graphhopper) throws Exception {
        if (storage != null)
            throw new IllegalStateException("GraphStorageBuilder has been already initialized.");

        // extract profiles from GraphHopper instance
        EncodingManager encMgr = graphhopper.getEncodingManager();
        List<FlagEncoder> encoders = encMgr.fetchEdgeEncoders();
        FlagEncoder flagEncoder = encoders.get(0);

        storage = new SpeedStorage(flagEncoder);

        populateCoreEdges(parameters.get("core_edges"));
        return storage;
    }

    private void populateCoreEdges(String coreEdgesFile) {
        try {
            InputStream inputStream = new FileInputStream(coreEdgesFile);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                String edgeDetails = scanner.nextLine();

                String[] columns = edgeDetails.split(",");
                String edgeId = columns[0];
                String speed = columns[1];
                edgeIdToSpeed.put(edgeId, speed);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processWay(ReaderWay way) {
        //No processing on input data
    }

    @Override
    public void processEdge(ReaderWay way, EdgeIteratorState edge) {
        Integer edgeID = edge.getEdge();
        if (edgeIdToSpeed.get(edgeID.toString()) == null) {
            return;
        }
        int speed = (int) Double.parseDouble(edgeIdToSpeed.get(edgeID.toString()).toString());

        storage.setSpeed(edge.getEdge(), false, speed);
        String oneWayTag = way.getTag("oneway");
        if (oneWayTag == null || !oneWayTag.equals("yes")) {
            storage.setSpeed(edge.getEdge(), true, speed);
        }
    }

    @Override
    public String getName() {
        return "Speed";
    }
}
