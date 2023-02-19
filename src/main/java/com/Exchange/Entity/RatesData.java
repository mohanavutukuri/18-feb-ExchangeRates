package com.Exchange.Entity;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatesData {
	String success;
	Timestamp timestamp;
	Boolean historical;
	String base;
	Date date;
	Rates rates;

	
}
