import com.alibaba.fastjson.JSONObject;

import java.util.Set;

public class JSONUtils {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("123","321");
        Set<String> stringSet = jsonObject.keySet();
    }
}
