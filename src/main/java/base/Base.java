package base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class Base {

	static String CSV_PATH = ".\\CSV\\dataDec-14-2022.csv";
	WebDriver driver;
	private static CSVReader csvReader;
	static String[] csvCell;

	public static ArrayList<Object[]> getDataRead_CSV() throws IOException, CsvException {
		// Create an object of CSVReader
		ArrayList<Object[]> data = new ArrayList<Object[]>();
		try {
			csvReader = new CSVReaderBuilder(new FileReader(CSV_PATH)).withSkipLines(1).build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while ((csvCell = csvReader.readNext()) != null) {
				String type = csvCell[0];
				String firstName = csvCell[1];
				String lastName = csvCell[2];
				String businessName = csvCell[3];
				String add1 = csvCell[4];
				String add2 = csvCell[5];
				String add3 = csvCell[6];
				String city = csvCell[7];
				String province = csvCell[8];
				String country = csvCell[9];
				String postalCode = csvCell[10];
				String email1 = csvCell[11];
				String email2 = csvCell[12];
				String email3 = csvCell[13];
				String phone1Type = csvCell[14];
				String phone1 = csvCell[15];
				String phone2Type = csvCell[16];
				String phone2 = csvCell[17];
				String phone3Type = csvCell[18];
				String phone3 = csvCell[19];
				String website1 = csvCell[20];
				String website2 = csvCell[21];
				String website3 = csvCell[22];

				Object ob[] = { type, firstName, lastName, businessName, add1, add2, add3, city, province, country,
						postalCode, email1, email2, email3, phone1Type, phone1, phone2Type, phone2, phone3Type, phone3,
						website1, website2, website3 };
				data.add(ob);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

}
