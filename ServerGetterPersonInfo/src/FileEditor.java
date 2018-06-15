import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileEditor {

	private String character = "windows-1251";
	public static final String DEFAULT_DIRECTORY = "C:\\Users\\Oleg\\PersonInformation";
	private String folderDirectory;
	
	FileEditor(String folderDirectory)
	{
		this.folderDirectory = folderDirectory;
	}
	
	public void saveFile(String nameFolder, String nameFile, String information) {
		try {
			
			new File(folderDirectory).mkdir();
			new File(folderDirectory + "\\" + nameFolder).mkdir();

			PrintWriter fileWriter = new PrintWriter(folderDirectory + "\\" + nameFolder + "\\" + nameFile + ".txt", character);
			
			fileWriter.println(information);
			fileWriter.flush();
			fileWriter.close();
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
