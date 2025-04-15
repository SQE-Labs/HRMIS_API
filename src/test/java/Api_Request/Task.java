package Api_Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Data
@Getter
@Setter
@Builder
@Jacksonized
public class Task {
    @JsonProperty("taskName")
    private String taskName;

    @JsonProperty("hours")
    private int hours;
}
