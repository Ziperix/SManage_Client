package helpers;

import org.apache.commons.codec.binary.Base64;
import org.json.*;

public class User
{
    private String token;

    public User(String jwt)
    {
        token = jwt;
    }

    public String GetID()
    {
        String[] split_string = token.split("\\.");
        String base64EB = split_string[1];
        Base64 base64U = new Base64(true);
        JSONObject res = new JSONObject(new String(base64U.decode(base64EB)));
        return res.getString("_id");
    }
}
