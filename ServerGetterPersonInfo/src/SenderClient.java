import org.json.JSONException;
import org.json.JSONObject;

public class SenderClient
{

	public JSONObject generateJson(String commandForClient, JSONObject jsonInformation)
	{

		System.out.println("com" + commandForClient + "json:"+jsonInformation);
		JSONObject commandJson = new JSONObject();
        try
        {
            commandJson.put("command", commandForClient);

            // compare to one json
            JSONObject jsonForClient = jsonInformation.put("clientCommand", commandJson);
            
            return jsonForClient;
        }catch(JSONException ex)
        {
        	System.out.println("Error:" + ex.getMessage());
        	return null;
        }
		
	}
}
