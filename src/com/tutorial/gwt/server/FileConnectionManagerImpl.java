package com.tutorial.gwt.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tutorial.gwt.client.FileConnectionManager;
import com.tutorial.gwt.client.Location;
import com.tutorial.gwt.client.SchoolClient;

//import au.com.bytecode.opencsv.CSVReader;

public class FileConnectionManagerImpl extends RemoteServiceServlet implements FileConnectionManager {
	private final String csv = "Number,Name,Address,URL,IsPrivate,HasEngLangLearnerProg,HasFrenchImmersionProg,HasAborigSuppServices,HasContinuingEdProgram,HasDistributedLearnProg,HasCareerPrepProg,HasCoopProg,HasApprenticeshipProg,HasCareerTechnicalProg,Lat,Long \n" +
"3939032,Admiral Seymour Elementary,1130 Keefer St,http://www.vsb.bc.ca/schools/admiral-seymour,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2786,-123.0803 \n" +
"6868018,Bayview Elementary,2251 Collingwood St,http://www.vsb.bc.ca/schools/bayview,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2665,-123.1823 \n" +
"3939004,Britannia Community Secondary,1001 Cotton Drive,http://www.vsb.bc.ca/schools/britannia-secondary,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,TRUE,FALSE,TRUE,TRUE,49.2752,-123.0719 \n" +
"3939088,Captain James Cook Elementary,3340 E 54th Av,http://www.vsb.bc.ca/schools/captain-james-cook,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2194,-123.0358 \n" +
"3939137,Champlain Heights Annex,7835 Champlain Crescent,http://www.vsb.bc.ca/schools/champlain-heights-annex,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2141,-123.0305 \n" +
"3939117,Chief Maquinna Annex,2882 E 4th Av,http://www.vsb.bc.ca/schools/chief-maquinna,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2664,-123.0451 \n" +
"3939139,Collingwood Neighbourhood School,3417 Euclid Av,http://www.vsb.bc.ca/schools/collingwood-neighbourhood-bruce-annex,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2363,-123.0323 \n" +
"3939035,David Lloyd George Elementary,8370 Cartier St,http://www.vsb.bc.ca/schools/david-lloyd-george,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.21,-123.1363 \n" +
"7324058,David Thompson Elementary,1755 E 55th Av,http://www.vsb.bc.ca/schools/david-thompson,FALSE,FALSE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2204,-123.0706 \n" +
"3939071,Edith Cavell Elementary,500 W 20th Av,http://www.vsb.bc.ca/schools/edith-cavell,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2529,-123.1172 \n" +
"3939140,Elsie Roy Elementary,150 Drake St,http://www.vsb.bc.ca/schools/elsie-roy,FALSE,TRUE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,49.2722,-123.1225 \n" +
"3939022,Eric Hamber Secondary,5025 Willow St,http://www.vsb.bc.ca/,FALSE,TRUE,FALSE,TRUE,FALSE,FALSE,TRUE,FALSE,TRUE,FALSE,49.2396,-123.1258 \n";

	
	//private List<School> schools;

	/*private List readCSV() {
		List<String[]> myEntries = new ArrayList<String[]>();
		try {
			CSVReader csvReader = openCSVFile("C:\\Users\\Peter\\Documents\\UBC\\CPSC 310\\Eclipse Projects\\ELT GWT Tutorial\\src\\Schools.csv");
			myEntries = csvReader.readAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myEntries; // schools;
	}

	private CSVReader openCSVFile(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		Reader reader = new FileReader(file);
		CSVReader csvReader = new CSVReader(reader);
		return csvReader;
	}*/
	
	private List<List<String>> readCSV() { 
		/*List<List<String>> schools = new ArrayList<List<String>>();
		String server = "ftp.bcitwebdev.com";
		int port = 21;
		String user = "postrovsky";
		String pass = "peter_ostrovsky1";

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			// APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile1 = "Schools.csv";
           // File downloadFile1 = new File("D:/Downloads/video.mp4");
            InputStream stream = ftpClient.retrieveFileStream(remoteFile1);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            int counter = 0;
            while((line=reader.readLine())!=null){
            if (counter != 0){
            	System.out.println("Buffer Reader Line");
            	System.out.println(line);
            	List<String> list = Arrays.asList(line.split(","));
            	schools.add(list);
            }
            counter++;
            }
			
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return schools;*/
		List<List<String>> returnList = new ArrayList<List<String>>();
		String lines[] = csv.split("\\r?\\n");
		System.out.println("No of lines in CSV: " + lines.length);
		for(String l : lines){			
			String fields[] = l.split(",");
			List<String> list = Arrays.asList(fields);
			returnList.add(list);
		}
		return returnList;
	}

	public List<School> parseData() {
		//List<String[]> data = readCSV();
		List<List<String>> schools = readCSV();
		return parseData(schools);//data);
	}

	private List<School> parseData(List<List<String>> entries) {
		List<School> schools = new ArrayList<School>();
		for (int i = 1; i < entries.size(); i++) {
			List<String> line = entries.get(i);

			School school = new School(Long.parseLong(line.get(0)), line.get(1),
					line.get(2), line.get(3), parseBool(line.get(4)), parseBool(line.get(5)),
					parseBool(line.get(6)), parseBool(line.get(7)), parseBool(line.get(8)),
					parseBool(line.get(9)), parseBool(line.get(10)),
					parseBool(line.get(11)), parseBool(line.get(12)),
					parseBool(line.get(13)), 
					Double.parseDouble(line.get(14)), Double.parseDouble(line.get(15)));
			schools.add(school);
		}
		return schools;
	}

	private boolean parseBool(String s) {
		return s.trim().toUpperCase().equals("TRUE");
	}
}
