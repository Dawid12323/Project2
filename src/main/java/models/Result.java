package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    int resultCount;

    List<Track> results;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<Track> getResults() {
        return results;
    }

    public void setResults(List<Track> results) {
        this.results = results;
    }

}
