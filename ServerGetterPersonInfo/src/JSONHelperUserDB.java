import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelperUserDB
{
	private String IdSocialNetwork;
	private String TypeRegistration;
	private String NameUser;
	private String Email;
	private String Gender;
	private String Password;
	private String Birthday;
	private String Country;
	private String City;
	private String Height;
	private String Weight;
	private String BMI;
	private String BFP;
	
	public JSONHelperUserDB(JSONObject jsonObject)
	{
		try
		{
			this.IdSocialNetwork = jsonObject.getString(UserTableJSONTitle.IdSocialNetwork);
			this.TypeRegistration = jsonObject.getString(UserTableJSONTitle.TypeRegistration);
			this.NameUser = jsonObject.getString(UserTableJSONTitle.NameUser);
			this.Email = jsonObject.getString(UserTableJSONTitle.Email);
			this.Gender = jsonObject.getString(UserTableJSONTitle.Gender);
			this.Password = jsonObject.getString(UserTableJSONTitle.Password);
			this.Birthday = jsonObject.getString(UserTableJSONTitle.Birthday);
			this.Country = jsonObject.getString(UserTableJSONTitle.Country);
			this.City = jsonObject.getString(UserTableJSONTitle.City);
			this.Height = jsonObject.getString(UserTableJSONTitle.Height);
			this.Weight = jsonObject.getString(UserTableJSONTitle.Weight);
			this.BMI = jsonObject.getString(UserTableJSONTitle.BMI);
			this.BFP = jsonObject.getString(UserTableJSONTitle.BFP);
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public String getIdSocialNetwork()
	{
		return IdSocialNetwork;
	}
	public void setIdSocialNetwork(String idSocialNetwork)
	{
		IdSocialNetwork = idSocialNetwork;
	}
	public String getTypeRegistration()
	{
		return TypeRegistration;
	}
	public void setTypeRegistration(String typeRegistration)
	{
		TypeRegistration = typeRegistration;
	}
	public String getNameUser()
	{
		return NameUser;
	}
	public void setNameUser(String nameUser)
	{
		NameUser = nameUser;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String email)
	{
		Email = email;
	}
	public String getGender()
	{
		return Gender;
	}
	public void setGender(String gender)
	{
		Gender = gender;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String password)
	{
		Password = password;
	}
	public String getBirthday()
	{
		return Birthday;
	}
	public void setBirthday(String birthday)
	{
		Birthday = birthday;
	}
	public String getCountry()
	{
		return Country;
	}
	public void setCountry(String country)
	{
		Country = country;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String city)
	{
		City = city;
	}
	public String getHeight()
	{
		return Height;
	}
	public void setHeight(String height)
	{
		Height = height;
	}
	public String getWeight()
	{
		return Weight;
	}
	public void setWeight(String weight)
	{
		Weight = weight;
	}
	public String getBMI()
	{
		return BMI;
	}
	public void setBMI(String bMI)
	{
		BMI = bMI;
	}
	public String getBFP()
	{
		return BFP;
	}
	public void setBFP(String bFP)
	{
		BFP = bFP;
	}
}
