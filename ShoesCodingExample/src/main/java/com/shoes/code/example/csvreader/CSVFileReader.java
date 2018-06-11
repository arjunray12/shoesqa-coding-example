package com.shoes.code.example.csvreader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVFileReader {

	public <T> Map<Integer, T> getCSVFileData(String csvFilePath, Class<T> classType) throws IOException {
		Map<Integer, T> dataInMap = new HashMap<>();
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
					.withType(classType).withIgnoreLeadingWhiteSpace(true).build();
			Iterator<T> csvUserIterator = csvToBean.iterator();
			int row_Number = 1;
			while (csvUserIterator.hasNext()) {
				T csvResult_Result = csvUserIterator.next();
				dataInMap.put(row_Number, csvResult_Result);
				row_Number++;
			}
		}
		return dataInMap;
	}
}
