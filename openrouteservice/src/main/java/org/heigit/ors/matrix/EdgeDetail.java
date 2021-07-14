package org.heigit.ors.matrix;

import com.graphhopper.util.PointList;
import org.geotools.geojson.GeoJSON;
import org.heigit.ors.geojson.GeometryJSON;

public class EdgeDetail {
    private int edgeId;
    private PointList geometry;

    public EdgeDetail(int edgeId, PointList geometry) {
        this.edgeId = edgeId;
        this.geometry = geometry;
    }


    public int getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(int edgeId) {
        this.edgeId = edgeId;
    }

    public String getGeometry() {
        return geometry.toLineString(false).toString();
    }

    public void setGeometry(PointList geometry) {
        this.geometry = geometry;
    }
}

