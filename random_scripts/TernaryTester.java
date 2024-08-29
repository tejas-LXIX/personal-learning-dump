import java.util.HashMap;
import java.util.Map;

public class TernaryTester {
    public static void main(String args[]) {
        Map<String,String> additionalHeaders = new HashMap<>();
        RequestParams requestParams = new RequestParams("B2B");
//        if(StringUtils.equals(String.valueOf(Mart.valueOf(requestParams.getMartId())), WMItemOrchAdapter.B2B)) {
//
//        }
        additionalHeaders.put("WM_MART_ID",(requestParams.getMartId().equals("B2B") ? "9" : "0"));
        System.out.println(additionalHeaders.get("WM_MART_ID"));
    }
}

class RequestParams {
    private String MartId;

    public RequestParams(String martId) {
        this.MartId = martId;
    }

    public String getMartId() {
        return MartId;
    }
}
