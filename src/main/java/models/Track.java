package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    @JsonProperty("trackName")
    private String trackName;

    public String getTrackName() {
        return trackName;
    }
}
