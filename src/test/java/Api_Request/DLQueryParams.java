package Api_Request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DLQueryParams {
    private String status;
    private int pageSize;
    private int page;
}
