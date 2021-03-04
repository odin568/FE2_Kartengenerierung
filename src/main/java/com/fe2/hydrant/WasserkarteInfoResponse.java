
package com.fe2.hydrant;

import java.util.List;

public class WasserkarteInfoResponse {

    private List<SourceType> sourceTypes;

    private List<WaterSource> waterSources;

    public List<SourceType> getSourceTypes() {
        return sourceTypes;
    }

    public void setSourceTypes(List<SourceType> sourceTypes) {
        this.sourceTypes = sourceTypes;
    }

    public List<WaterSource> getWaterSources() {
        return waterSources;
    }

    public void setWaterSources(List<WaterSource> waterSources) {
        this.waterSources = waterSources;
    }

}
