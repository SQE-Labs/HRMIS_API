package Api_Request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder

public class LeaveResponse {
    private int totalPages;
    private int totalLeave;
    private String status;
    private int pageSize;
    private int page;
    private List<LeaveData> data;

    // Getters and Setters
}
