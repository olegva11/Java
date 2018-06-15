import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.fabric.xmlrpc.base.Data;

public class PersonAccount
{

	// Type registration
	public static final int FACEBOOK_ACCOUNT = 0;
	public static final int TWITTER_ACCOUNT = 1;
	public static final int GOOGLE_ACCOUNT = 2;
	public static final int SYSTEM_ACCOUNT = 3;

	public static final int GENDER_MALE = 0;
	public static final int GENDER_FEMALE = 1;
	public static final int GENDER_OTHER = 2;

	private long id;
	private String socialId;
	private String email;
	private String password;
	private String name;
	private int gender;
	private String birthday;
	private String country;
	private String city;

	private int height;
	private int weight;
	private int bmi;
	private double bfp;

	private int typeRegistration;

	public PersonAccount()
	{
		// TODO Auto-generated constructor stub
	}

	PersonAccount(JSONObject jsonObject)
	{
		// jsonObject.getJSONObject("personInfo").getInt("id");
		try
		{
			/*
			 * {"personInfo":{"id":1,"birthday":"27.5.1995","weight":94,
			 * "height":198,"bmi":24,"email":"olegva11@gmail.com", "name":
			 * "Юыху ","bfp":17,"gender":0,"type_registration":0,
			 * "social_id":"1321529537896151"}}
			 * 
			 */

			if (!jsonObject.isNull(UserTableJSONTitle.IdUsers))
			{
				this.id = jsonObject.getInt(UserTableJSONTitle.IdUsers);
			}

			if (!jsonObject.isNull(UserTableJSONTitle.Birthday))
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				// String[] dateParse = user.getBirthday().split("\\.");
				String[] dateParse = jsonObject.getString(UserTableJSONTitle.Birthday).split("-");

				int day = Integer.parseInt(dateParse[2]);
				int month = Integer.parseInt(dateParse[1]);
				int year = Integer.parseInt(dateParse[0]);

				this.birthday = year + "-" + month + "-" + day;

			}

			if (!jsonObject.isNull(UserTableJSONTitle.Weight))
			{
				this.weight = jsonObject.getInt(UserTableJSONTitle.Weight);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.Height))
			{
				this.height = jsonObject.getInt(UserTableJSONTitle.Height);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.BMI))
			{
				this.bmi = jsonObject.getInt(UserTableJSONTitle.BMI);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.Email))
			{
				this.email = jsonObject.getString(UserTableJSONTitle.Email);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.NameUser))
			{
				this.name = jsonObject.getString(UserTableJSONTitle.NameUser);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.BFP))
			{
				this.bfp = jsonObject.getDouble(UserTableJSONTitle.BFP);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.Gender))
			{
				this.gender = jsonObject.getInt(UserTableJSONTitle.Gender);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.TypeRegistration))
			{
				this.typeRegistration = jsonObject.getInt(UserTableJSONTitle.TypeRegistration);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.IdSocialNetwork))
			{
				this.socialId = jsonObject.getString(UserTableJSONTitle.IdSocialNetwork);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.City))
			{
				this.city = jsonObject.getString(UserTableJSONTitle.City);
			}
			if (!jsonObject.isNull(UserTableJSONTitle.Country))
			{
				this.country = jsonObject.getString(UserTableJSONTitle.Country);
			}

		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public long writeInDB()
	{
		// Insert the new row
		UserTableDBHelper userTable = new UserTableDBHelper();

		return userTable.insertUserData(this);
		// return db.writeDataToDB(fillValues());
	}

	public long updateInDB()
	{
		// Insert the new row
		UserTableDBHelper userTable = new UserTableDBHelper();

		// return how much rows updated
		return userTable.updateUserInfo(this);
	}

	// Create format JsonObject, client information, key: value, use for create
	// file and send to server
	public JSONObject getJSONObject()
	{
		JSONObject json = new JSONObject();
		JSONObject peopleJson = new JSONObject();
		try
		{
			peopleJson.put(UserTableJSONTitle.IdUsers, this.id);
			peopleJson.put(UserTableJSONTitle.IdSocialNetwork, this.socialId);
			peopleJson.put(UserTableJSONTitle.TypeRegistration, this.typeRegistration);
			peopleJson.put(UserTableJSONTitle.Email, this.email);
			peopleJson.put(UserTableJSONTitle.Password, this.password);
			peopleJson.put(UserTableJSONTitle.NameUser, this.name);
			peopleJson.put(UserTableJSONTitle.Birthday, this.birthday);
			peopleJson.put(UserTableJSONTitle.Gender, this.gender);
			peopleJson.put(UserTableJSONTitle.City, this.city);
			peopleJson.put(UserTableJSONTitle.Country, this.country);
			peopleJson.put(UserTableJSONTitle.BFP, this.bfp);
			peopleJson.put(UserTableJSONTitle.BMI, this.bmi);
			peopleJson.put(UserTableJSONTitle.Weight, this.weight);
			peopleJson.put(UserTableJSONTitle.Height, this.height);

			json.put(UserTableJSONTitle.NameTitle, peopleJson);
			return json;
		} catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static PersonAccount fillUserInfo(long userId)
	{
		UserTableDBHelper helper = new UserTableDBHelper();
		return helper.selectInfoFromUser(userId);
	}

	// Setter's and getter's
	public void setId(long id)
	{
		this.id = id;
	}

	public long getId()
	{
		return this.id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public int getTypeRegistration()
	{
		return typeRegistration;
	}

	public void setTypeRegistration(int typeRegistration)
	{
		this.typeRegistration = typeRegistration;
	}

	public void setSocialId(String socialId)
	{
		this.socialId = socialId;
	}

	public String getSocialId()
	{
		return socialId;
	}

	public void setGender(int gender)
	{
		this.gender = gender;
	}

	public int getGender()
	{
		return gender;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	public int getBmi()
	{
		return bmi;
	}

	public void setBmi(int bmi)
	{
		this.bmi = bmi;
	}

	public double getBfp()
	{
		return bfp;
	}

	public void setBfp(double bfp)
	{
		this.bfp = bfp;
	}

}
