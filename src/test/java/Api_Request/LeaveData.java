package Api_Request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder

public class LeaveData {
    private int laveId;
    private String startDate;
    private String endDate;
    private String reasonOfLeave;
    private String typeOfLeave;
    private boolean considerLeave;
    private int noOfDays;
    private String employeeName;
    private String managerStatus;
    private String approverStatus;
    private String leaveFinalStatus;
    private List<Task> taskList;

    // Getters and Setters
}
