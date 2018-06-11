package com.shoes.code.example.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.shoes.code.example.csvreader.CSVFileReader;
import com.shoes.code.example.dto.JMeterResultDTO;

public class TestCases {
	
	private static Logger Log = Logger.getLogger(TestCases.class);

	static{
		DOMConfigurator.configure("log4j.xml");
	}
	
	CSVFileReader csvFileReader = null;
	private static String CSVFILENAME = "csv_files/jmeter_results.csv";
	Map<Integer, JMeterResultDTO> jMeterResult_Map = null;

	@BeforeSuite
	private void initSuit() throws URISyntaxException, IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		String csvFilePath = Paths.get(classLoader.getResource(CSVFILENAME).toURI()).toString();
		csvFileReader = new CSVFileReader();
		jMeterResult_Map = csvFileReader.getCSVFileData(csvFilePath, JMeterResultDTO.class);
	}

	@Test
	public void getTestRunBegan() {
		Collection<JMeterResultDTO> jMeterResult_Collection = jMeterResult_Map.values();

		List<JMeterResultDTO> jMeterResult_List = new ArrayList<JMeterResultDTO>(jMeterResult_Collection);
		Collections.sort(jMeterResult_List, TestCases.TimeStampComparator);
		System.out.println(" When the test run began ................. " + jMeterResult_List.get(0).getTimeStamp());
		Assert.assertTrue(true, " When the test run began ................. " + jMeterResult_List.get(0).getTimeStamp());
		Log.info(" When the test run began ................. " + jMeterResult_List.get(0).getTimeStamp());
	}

	@Test
	public void getTestRunEnded() {
		Collection<JMeterResultDTO> jMeterResult_Collection = jMeterResult_Map.values();
		List<JMeterResultDTO> jMeterResult_List = new ArrayList<JMeterResultDTO>(jMeterResult_Collection);
		Collections.sort(jMeterResult_List, Collections.reverseOrder(TestCases.TimeStampComparator));
		System.out.println(" When the test run ended ................. " + jMeterResult_List.get(0).getTimeStamp());
		Assert.assertTrue(true, " When the test run ended ................. " + jMeterResult_List.get(0).getTimeStamp());
		Log.info(" When the test run ended ................. " + jMeterResult_List.get(0).getTimeStamp());
	}

	@Test
	public void getAverageElapseTime() {

		double sum_ElapsedTime = 0;
		double avg_ElapsedTime = 0;
		int count_ElapsedTime = jMeterResult_Map.size();

		for (Entry<Integer, JMeterResultDTO> jMeterResult_Map_Entry : jMeterResult_Map.entrySet()) {
			sum_ElapsedTime += Double.parseDouble(jMeterResult_Map_Entry.getValue().getElapsed());
		}

		avg_ElapsedTime = sum_ElapsedTime / count_ElapsedTime;
		System.out.println(" The average elapse time ................. " + avg_ElapsedTime);
		Assert.assertTrue(true, " The average elapse time ................. " + avg_ElapsedTime);
		Log.info(" The average elapse time ................. " + avg_ElapsedTime);
	}

	@Test
	public void getLongestElapsedTime() {
		Collection<JMeterResultDTO> jMeterResult_Collection = jMeterResult_Map.values();

		List<JMeterResultDTO> jMeterResult_List = new ArrayList<JMeterResultDTO>(jMeterResult_Collection);
		Collections.sort(jMeterResult_List, TestCases.ELapsedComparator);
		System.out.println(" The longest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
		Assert.assertTrue(true, " The longest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
		Log.info(" The longest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
	}

	@Test
	public void getShortestElapsedTime() {
		Collection<JMeterResultDTO> jMeterResult_Collection = jMeterResult_Map.values();

		List<JMeterResultDTO> jMeterResult_List = new ArrayList<JMeterResultDTO>(jMeterResult_Collection);
		Collections.sort(jMeterResult_List, Collections.reverseOrder(TestCases.ELapsedComparator));
		System.out.println(" The shortest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
		Assert.assertTrue(true, " The shortest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
		Log.info(" The shortest elapse time ................. " + jMeterResult_List.get(0).getElapsed());
	}
	
	@Test
	public void getNumberOfQueriesPerSeconds() {
		Collection<JMeterResultDTO> jMeterResult_Collection = jMeterResult_Map.values();

		List<JMeterResultDTO> jMeterResult_List = new ArrayList<JMeterResultDTO>(jMeterResult_Collection);
		Collections.sort(jMeterResult_List, TestCases.TimeStampComparator);
		
		List<JMeterResultDTO> category_Concept_CSV_List = new ArrayList<JMeterResultDTO>();
		List<JMeterResultDTO> category_Shops_CSV_List = new ArrayList<JMeterResultDTO>();
		List<JMeterResultDTO> category_Thumbs_CSV_List = new ArrayList<JMeterResultDTO>();
		
		for (JMeterResultDTO jMeterResultDTO : jMeterResult_List) {
			if (jMeterResultDTO.getThreadName().contains("Category Concept CSV")) {
				category_Concept_CSV_List.add(jMeterResultDTO);
			} else if (jMeterResultDTO.getThreadName().contains("Category Shops CSV")) {
				category_Shops_CSV_List.add(jMeterResultDTO);
			} else if (jMeterResultDTO.getThreadName().contains("Category Thumbs CSV")) {
				category_Thumbs_CSV_List.add(jMeterResultDTO);
			}
		}
		
		double current_TimeStamp = Double.parseDouble(category_Concept_CSV_List.get(0).getTimeStamp());
		int query_Counter = 0;
		int second_Counter = 1;
		
		for (JMeterResultDTO category_Concept_CSV : category_Concept_CSV_List) {
			if (Double.parseDouble(category_Concept_CSV.getTimeStamp()) < current_TimeStamp + 1000 ) {
				query_Counter++ ;
			} else {
				System.out.println(second_Counter + "- th Second Counter 'Category Concept CSV' executed " +  query_Counter + " times.");
				Assert.assertTrue(true, second_Counter + "- th Second Counter 'Category Concept CSV' executed " +  query_Counter + " times.");
				Log.info(second_Counter + "- th Second Counter 'Category Concept CSV' executed " +  query_Counter + " times.");
				query_Counter = 0;
				second_Counter ++;
				current_TimeStamp = Double.parseDouble(category_Concept_CSV.getTimeStamp());
			}
		}
		
		current_TimeStamp = Double.parseDouble(category_Shops_CSV_List.get(0).getTimeStamp());
		query_Counter = 0;
		second_Counter = 1;
		
		for (JMeterResultDTO category_Shops_CSV : category_Shops_CSV_List) {
			if (Double.parseDouble(category_Shops_CSV.getTimeStamp()) < current_TimeStamp + 1000 ) {
				query_Counter++ ;
			} else {
				System.out.println(second_Counter + "- th Second Counter 'Category Shops CSV' executed " +  query_Counter + " times.");
				Assert.assertTrue(true, second_Counter + "- th Second Counter 'Category Shops CSV' executed " +  query_Counter + " times.");
				Log.info(second_Counter + "- th Second Counter 'Category Shops CSV' executed " +  query_Counter + " times.");
				query_Counter = 0;
				second_Counter ++;
				current_TimeStamp = Double.parseDouble(category_Shops_CSV.getTimeStamp());
			}
		}
		
		current_TimeStamp = Double.parseDouble(category_Thumbs_CSV_List.get(0).getTimeStamp());
		query_Counter = 0;
		second_Counter = 1;
		
		for (JMeterResultDTO category_Thumbs_CSV : category_Thumbs_CSV_List) {
			if (Double.parseDouble(category_Thumbs_CSV.getTimeStamp()) < current_TimeStamp + 1000 ) {
				query_Counter++ ;
			} else {
				System.out.println(second_Counter + "- th Second Counter 'Category Thumbs CSV' executed " +  query_Counter + " times.");
				Assert.assertTrue(true, second_Counter + "- th Second Counter 'Category Thumbs CSV' executed " +  query_Counter + " times.");
				Log.info(second_Counter + "- th Second Counter 'Category Thumbs CSV' executed " +  query_Counter + " times.");
				query_Counter = 0;
				second_Counter ++;
				current_TimeStamp = Double.parseDouble(category_Thumbs_CSV.getTimeStamp());
			}
		}	
	}
	
	

	/**
	 * Comparator to sort JMeterResultDTO list in order of Elaped Time.
	 */
	public static Comparator<JMeterResultDTO> ELapsedComparator = new Comparator<JMeterResultDTO>() {
		@Override
		public int compare(JMeterResultDTO e1, JMeterResultDTO e2) {
			return Double.compare(Double.parseDouble(e1.getElapsed()), Double.parseDouble(e2.getElapsed()));
		}
	};

	/**
	 * Comparator to sort JMeterResultDTO list in order of TimeStamp Time.
	 */
	public static Comparator<JMeterResultDTO> TimeStampComparator = new Comparator<JMeterResultDTO>() {
		@Override
		public int compare(JMeterResultDTO e1, JMeterResultDTO e2) {
			return Double.compare(Double.parseDouble(e1.getTimeStamp()), Double.parseDouble(e2.getTimeStamp()));
		}
	};
}
