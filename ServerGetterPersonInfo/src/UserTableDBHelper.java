import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTableDBHelper
{

	private static String URL_DB = "jdbc:mysql://localhost:3306/userinformation";
	private static String USER_NAME = "root";
	private static String USER_PASSWORD = "27051995";

	private static Connection connection;

	public UserTableDBHelper()
	{
		connectToDB();
	}

	public boolean isUserInDB(String email)
	{
		String query = "Select Count(" + FeedReaderContract.Users.IdSocialNetwork + ") from "
				+ FeedReaderContract.Users.NameTable + " WHERE " + FeedReaderContract.Users.Email + "='" + email + "'";

		System.out.println(query);

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			result.next();
			int count = result.getInt(1);
			if (count != 0)
			{
				System.out.println("Account in DB");
				return true;
			} else
			{
				System.out.println("Account not in DB");
				return false;
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean isUserInDB(String idSocial, int typeRegistration)
	{
		String query = "Select Count(" + FeedReaderContract.Users.IdSocialNetwork + ") from "
				+ FeedReaderContract.Users.NameTable + " WHERE " + FeedReaderContract.Users.IdSocialNetwork + "='"
				+ idSocial + "' and " + FeedReaderContract.Users.TypeRegistration + "=" + typeRegistration;

		System.out.println(query);

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			result.next();

			int count = result.getInt(1);
			System.out.println("Count:" + count);
			if (count != 0)
			{
				System.out.println("Account in DB");
				return true;
			} else
			{
				System.out.println("Account not in DB");
				return false;
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	// Connection
	public void connectToDB()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// String driver = "com.mysql.jbdc.Driver";
			connection = DriverManager.getConnection(URL_DB, USER_NAME, USER_PASSWORD);
			System.out.println("Connection success");

		} catch (Exception ex)
		{
			System.out.println(ex);
		}

	}

	public long getUserId(String email)
	{
		String query = "Select " + FeedReaderContract.Users.IdUsers + " from " + FeedReaderContract.Users.NameTable
				+ " WHERE " + FeedReaderContract.Users.Email + "='" + email + "'";

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			if (!result.next())
			{
				System.out.println("Id:" + result.getString(FeedReaderContract.Users.IdUsers));
				return result.getLong(FeedReaderContract.Users.IdUsers);
			} else
			{
				return -1;
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public long getUserId(String email, String password)
	{
		String query = "Select " + FeedReaderContract.Users.IdUsers + " from " + FeedReaderContract.Users.NameTable
				+ " WHERE " + FeedReaderContract.Users.Email + "=" + email + " and " + FeedReaderContract.Users.Password
				+ "=" + password;

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			if (!result.next())
			{
				System.out.println("Id:" + result.getString(FeedReaderContract.Users.IdUsers));
				return result.getLong(FeedReaderContract.Users.IdUsers);
			} else
			{
				return -1;
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public long getUserId(String idSocial, int typeRegistration)

	{
		String query = "Select " + FeedReaderContract.Users.IdUsers + " from " + FeedReaderContract.Users.NameTable
				+ " WHERE " + FeedReaderContract.Users.IdSocialNetwork + "='" + idSocial + "' and "
				+ FeedReaderContract.Users.TypeRegistration + "=" + typeRegistration;

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			// if (!result.next())
			// {
			result.next();
			System.out.println("Id:" + result.getString(FeedReaderContract.Users.IdUsers));
			return result.getLong(FeedReaderContract.Users.IdUsers);
			// } else
			// {
			// System.out.println("Id not found");
			// return -1;
			// }
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public long updateUserInfo(PersonAccount userInfo)
	{
		try
		{
			if ((userInfo.getSocialId() != null || userInfo.getSocialId().isEmpty())
					&& userInfo.getTypeRegistration() != -1)
			{

				String query = "Update " + " userinformation." + FeedReaderContract.Users.NameTable + " set "
						+ FeedReaderContract.Users.Email + "='" + userInfo.getEmail() + "',"
						+ FeedReaderContract.Users.NameUser + "='" + userInfo.getName() + "',"
						+ FeedReaderContract.Users.Password + "='" + userInfo.getPassword() + "',"
						+ FeedReaderContract.Users.Gender + "=" + userInfo.getGender() + ","
						+ FeedReaderContract.Users.Birthday + "='" + userInfo.getBirthday() + "',"
						+ FeedReaderContract.Users.Country + "='" + userInfo.getCountry() + "',"
						+ FeedReaderContract.Users.City + "='" + userInfo.getCity() + "',"
						+ FeedReaderContract.Users.Height + "=" + userInfo.getHeight() + ","
						+ FeedReaderContract.Users.Weight + "=" + userInfo.getWeight() + ","
						+ FeedReaderContract.Users.BMI + "=" + userInfo.getBmi() + "," + FeedReaderContract.Users.BFP
						+ "=" + userInfo.getBfp() + " WHERE " + FeedReaderContract.Users.IdSocialNetwork + "='"
						+ userInfo.getSocialId() + "' AND " + FeedReaderContract.Users.TypeRegistration + " = "
						+ userInfo.getTypeRegistration() + " ";
				;
				//
				System.out.println("Sql update:" + query);

				Statement stmt;

				stmt = connection.createStatement();

				return stmt.executeUpdate(query);
			} else
			{
				return 0;
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public PersonAccount selectInfoFromUser(long idUser)
	{

		String query = "Select * from " + FeedReaderContract.Users.NameTable + " where "
				+ FeedReaderContract.Users.IdUsers + " = " + idUser;

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			ResultSet result = stmt.executeQuery(query);

			result.next();
			// Create personAccount, fill param and return object

			PersonAccount pAccount = new PersonAccount();

			pAccount.setId(result.getInt(FeedReaderContract.Users.IdUsers));
			pAccount.setName(result.getString(FeedReaderContract.Users.NameUser));
			pAccount.setPassword(result.getString(FeedReaderContract.Users.Password));
			pAccount.setBirthday(result.getString(FeedReaderContract.Users.Birthday));
			pAccount.setEmail(result.getString(FeedReaderContract.Users.Email));
			pAccount.setCountry(result.getString(FeedReaderContract.Users.Country));
			pAccount.setCity(result.getString(FeedReaderContract.Users.City));
			pAccount.setGender(result.getInt(FeedReaderContract.Users.Gender));
			pAccount.setTypeRegistration(result.getInt(FeedReaderContract.Users.TypeRegistration));
			pAccount.setHeight(result.getInt(FeedReaderContract.Users.Height));
			pAccount.setWeight(result.getInt(FeedReaderContract.Users.Weight));
			pAccount.setSocialId(result.getString(FeedReaderContract.Users.IdSocialNetwork));
			pAccount.setBmi(result.getInt(FeedReaderContract.Users.BMI));
			pAccount.setBfp(result.getInt(FeedReaderContract.Users.BFP));

			return pAccount;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public long insertUserData(PersonAccount userInfo)
	{
		userInfo.setBirthday("2000-01-01");
		String query = "INSERT INTO " + "userinformation." + FeedReaderContract.Users.NameTable
				+ " ( userIdSocialNetwork, typeRegistration, name, email, gender, password, birthday, country, city, height, weight, bmi, bfp)"
				+ " VALUES ('" + userInfo.getSocialId() + "'," + userInfo.getTypeRegistration() + ",'"
				+ userInfo.getName() + "','" + userInfo.getEmail() + "'," + userInfo.getGender() + ",'"
				+ userInfo.getPassword() + "','" + userInfo.getBirthday() + "','" + userInfo.getCountry() + "','"
				+ userInfo.getCity() + "'," + userInfo.getHeight() + "," + userInfo.getWeight() + ","
				+ userInfo.getBmi() + "," + userInfo.getBfp() + ")";
		//
		System.out.println("Sql insert:" + query);

		Statement stmt;
		try
		{
			stmt = connection.createStatement();

			return stmt.executeUpdate(query);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		}
	}

}
