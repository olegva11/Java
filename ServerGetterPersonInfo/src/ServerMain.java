import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerMain
{

	static String WIN1251 = "windows-1251";
	static String UTF8 = "UTF-8";

	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static DataOutputStream dataOutputStream;
	private static PrintWriter out;

	static Date date;

	private static String messageFromPort; // all information from client
	private static String commandFromClient; // command from all information
	private static JSONObject informationFromClient;

	private static JSONObject jsonObject;
	private static SenderClient senderClient;

	private static final String SAVE_INFO_FILE = "save_file";
	private static final String REGISTER_USER = "register_user";
	private static final String MODEL_PROPERTIES = "get_model_proporties";
	private static final String UPDATE_USER = "update_user";
	private static final String LOGIN_USER = "login_user";
	private static final String GET_ID = "get_id";

	public static void main(String[] args)
	{
		long idUser;

		try
		{
			serverSocket = new ServerSocket(4444); // Server socket

		} catch (IOException e)
		{
			System.out.println("Could not listen on port: 4444");
		}

		System.out.println("Server started. Listening to the port 4444");

		// init block
		{
			senderClient = new SenderClient();
		}

		testConnection();

		while (true)
		{
			try
			{
				// init socket
				clientSocket = serverSocket.accept(); // accept the client
														// connection

				// init in from server

				InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader); // get

				messageFromPort = bufferedReader.readLine();

				// init out from server
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				System.out.println(clientSocket.getInetAddress());

				date = new Date();

				// show message to console

				if (messageFromPort != null && !messageFromPort.isEmpty())
				{
					// Show message from port
					System.out.println("win1251->UTF8 " + new String(messageFromPort.getBytes(WIN1251), UTF8));

					messageFromPort = new String(messageFromPort.getBytes(WIN1251), UTF8);

					// Create json from message
					jsonObject = new JSONObject(messageFromPort);

					// Get command from json
					commandFromClient = jsonObject.getJSONObject("serverCommand").getString("command");
					System.out.println("Command: " + commandFromClient);

						// Get information from json
						informationFromClient = jsonObject.getJSONObject("personInfo");
						System.out.println("Information: " + informationFromClient);

						if (commandFromClient.equals(LOGIN_USER))
						{
							idUser = loginUser(informationFromClient);

							if (idUser != -1)
							{
								// send message to client

								PersonAccount pAccount = PersonAccount.fillUserInfo(idUser);

								JSONObject jsonToClient = senderClient.generateJson(LOGIN_USER,
										pAccount.getJSONObject());

								System.out.println(jsonToClient);

								String infoToClient = new String(jsonToClient.toString().getBytes(UTF8), WIN1251);
								System.out.println(infoToClient);
								out.println(infoToClient);
								out.flush();

								System.out.println("Return info to client");
							} else
							{
								System.out.println("Message not send");
							}
						}

						if (commandFromClient.equals(REGISTER_USER))
						{
							idUser = registerNewUser(informationFromClient);

							if (idUser != -1)
							{
								PersonAccount pAccount = PersonAccount.fillUserInfo(idUser);

								JSONObject jsonFromClient = pAccount.getJSONObject();
								JSONObject jsonToClient = senderClient.generateJson(REGISTER_USER, jsonFromClient);

								System.out.println(jsonToClient.toString());

								String infoToClient = new String(jsonToClient.toString().getBytes(UTF8), WIN1251);
								System.out.println(infoToClient);
								out.println(infoToClient);
								out.flush();

								System.out.println("Send message to client");
							} else
							{
								System.out.println("Message not send");
							}
						}

						if (commandFromClient.equals(UPDATE_USER))
						{
							long countUpdateRows;
							countUpdateRows = updateUserInformation(informationFromClient);

							saveInfoToFile(jsonObject);
							JSONObject jsonInfo = new JSONObject();
							JSONObject json = new JSONObject();

							if (countUpdateRows > 0)
							{
								jsonInfo.put("result", "true");
							} else
							{
								jsonInfo.put("result", "false");
							}

							json.put(UserTableJSONTitle.NameTitle, jsonInfo);

							System.out.println("Update count rows:" + countUpdateRows + "\n json: " + json.toString());

							JSONObject jsonToClient = senderClient.generateJson(UPDATE_USER, json);

							System.out.println(jsonToClient.toString());
							out.println(jsonToClient.toString());
							out.flush();

							System.out.println("Return info to client");
						}

						// GET_ID for new user, and send to client(mobile)
						if (commandFromClient.equals(GET_ID))
						{
							// init output stream(for send information to
							// client)
							dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

							// dataOutputStream.write(randNumber);
						}

						// Save information from user to file
						if (commandFromClient.equals(SAVE_INFO_FILE))
						{
							// Get date now

							if (jsonObject != null)
							{
								saveInfoToFile(jsonObject);
							}
						}

						if (commandFromClient.equals(MODEL_PROPERTIES))
						{
							saveModelProportiesToFile(jsonObject);
							JSONObject smallJson = jsonObject.getJSONObject("ModelProporties");
							try
							{

								saveInfoToFile(jsonObject);
								System.out.println("Model properties");

								int id = jsonObject.getJSONObject("personInfo").getInt("id");
								
								DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
								
								String dat = dateFormat.format(date).toString();
								dat = dat + ".txt";
								Process p = Runtime.getRuntime()
										.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + "D:\\test\\UM_mhealth.exe "
												+ FileEditor.DEFAULT_DIRECTORY + "\\" + id + "\\" + dat);
								// Process p = new
								// ProcessBuilder("D:\\test\\ServerTest.exe").start();
								System.out.println("Run exe");
							} catch (Exception ex)
							{
								System.out.println("error" + ex);
							}
						}
					}
					out.close();
					inputStreamReader.close();
					bufferedReader.close();
					clientSocket.close();

					System.out.println("Message Empty");
					out.close();
					inputStreamReader.close();
					bufferedReader.close();
					clientSocket.close();
				
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error" + e.getMessage());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error" + e.getMessage());
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error" + e.getMessage());
			}

			// Close All
		}
	}

	private static long registerNewUser(JSONObject infoFromClient)
	{
		// initialization dbHelper, for use functional database (ORM)
		UserTableDBHelper userDBHelper = new UserTableDBHelper();

		try
		{
			long idUser = loginUser(infoFromClient);
			if (idUser != -1)
			{
				System.out.println("Find id:" + idUser);
				return idUser;
			} else // if user not have account, write info to database and
					// return id
			{
				// initialization new user, use information from socket
				PersonAccount newUser = new PersonAccount(infoFromClient);
				// write to database
				newUser.writeInDB();
				System.out.println("id social: " + newUser.getSocialId() + ", type: " + newUser.getTypeRegistration());

				long idNewUser = userDBHelper.getUserId(newUser.getSocialId(), newUser.getTypeRegistration());
				System.out.println("id:" + idNewUser);

				if (idNewUser != -1)
				{
					return idNewUser;
				} else
				{
					return -1;
				}

			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	private static long loginUser(JSONObject infoFromClient)
	{
		UserTableDBHelper userDBHelper = new UserTableDBHelper();

		try
		{
			if (!infoFromClient.isNull(UserTableJSONTitle.IdSocialNetwork))
			{
				String idSocial = infoFromClient.getString(UserTableJSONTitle.IdSocialNetwork);
				int typeRegistration = infoFromClient.getInt(UserTableJSONTitle.TypeRegistration);

				if (userDBHelper.isUserInDB(idSocial, typeRegistration))
				{
					return userDBHelper.getUserId(idSocial, typeRegistration);
				}
			}

			if (!infoFromClient.isNull(UserTableJSONTitle.Email))
			{
				String email = infoFromClient.getString(UserTableJSONTitle.Email);

				if (userDBHelper.isUserInDB(email))
				{
					return userDBHelper.getUserId(email);
				}
			}

			// if user have account, return your id from DB
			{
				return -1;
			}
		} catch (JSONException e)
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

	private static long updateUserInformation(JSONObject infoFromClient)
	{
		PersonAccount updateUser = new PersonAccount(infoFromClient);

		// write to database
		return updateUser.updateInDB();

	}

	private static void testConnection()
	{
		UserTableDBHelper userDBHelper = new UserTableDBHelper();
	}

	private static void saveInfoToFile(JSONObject jsonObject)
	{
		if (jsonObject != null)
		{
			int id;
			try
			{
				id = jsonObject.getJSONObject("personInfo").getInt("id");

				FileEditor fileEditor = new FileEditor(FileEditor.DEFAULT_DIRECTORY);

				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
				Date date = new Date();
				// name folder and name file
				fileEditor.saveFile(String.valueOf(id), dateFormat.format(date).toString(), jsonObject.toString());

			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	private static void saveModelProportiesToFile(JSONObject jsonObject)
	{
		if (jsonObject != null)
		{
			try
			{
				FileEditor fileEditor = new FileEditor(FileEditor.DEFAULT_DIRECTORY);
				long id = jsonObject.getJSONObject("personInfo").getLong("id");

				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
				Date date = new Date();
				// name folder and name file
				fileEditor.saveFile(id + "", dateFormat.format(date).toString(), jsonObject.toString());
				System.out.println("file saved");

			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

}
