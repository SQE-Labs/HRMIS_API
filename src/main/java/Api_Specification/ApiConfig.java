package Api_Specification;

public interface ApiConfig {

     String BASE_URI = "https://topuptalent.com/HRMBackendTest";
    String LOGIN = "/api/auth/signin";
     String LEAVE_APPLY = "/leave/ood/leaveApply";
     String DL_APPROVER = "/leave/ood/approver/dl";
     String LEAVE_APPROVER = "/leave/ood/approver/{approverType}";
     String LeaveFetch = "/leave/ood/approver/dl";
     String LeavesHr = "leave/ood/approver/hr";


    String EMPLOYEE_EMAIL = "asset-l3@caeliusconsulting.com";
        String EMPLOYEE_PASSWORD = "Test@123";
        String token = null;
        String DL_EMAIL = "vishal.thakur@caeliusconsulting.com";
        String DL_PASSWORD = "Test@123";

        String HR_EMAIL = "emp1@caeliusconsulting.com";
        String HR_PASSWORD = "Test@123";

        String INVALID_EMAIL = "wrong@example.com";
        String INVALID_PASSWORD = "wrongPass";
    }



