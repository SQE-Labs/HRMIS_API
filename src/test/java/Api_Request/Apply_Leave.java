package Api_Request;

import Api_Specification.ApiConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@Jacksonized
public class Apply_Leave  {
    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("reasonOfLeave")
    private String reasonOfLeave;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("approverId")
    private int approverId;

    @JsonProperty("taskList")
    private List<Task> taskList;
}
