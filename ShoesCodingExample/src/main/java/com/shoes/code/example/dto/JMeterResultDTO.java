package com.shoes.code.example.dto;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class JMeterResultDTO {

	@CsvBindByName(column = "timeStamp", required = true)
	private String timeStamp;
	@CsvBindByName(column = "elapsed", required = true)
	private String elapsed;
	@CsvBindByName(column = "threadName", required = true)
	private String threadName;

}
