package com.Exchange.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.Exchange.Entity.Audit_Info;
import com.Exchange.Entity.RatesData;
import com.Exchange.ExcelWrite.ExcelWriter;
import com.Exchange.Repository.Audit_InfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataFetchService {
	@Autowired
	private Audit_InfoRepo repo;
	String arr[]= {"AED","CAD","EUR","INR","JPY"};
	public void getApiData(String date) {
		FutureTask<RatesData>[] tasks = new FutureTask[6];
		Audit_Info[] infos=new Audit_Info[6];
		try {
			
			for(int i=0;i< arr.length;i++) {
				String st="https://api.apilayer.com/exchangerates_data/"+date+"?symbols=USD&base="+arr[i];

				Callable<RatesData> clble = new DataFetchApi(st);
				tasks[i] = new FutureTask<RatesData>(clble);

				Audit_Info info=new Audit_Info();
				info.setRequest(st);
				info.setCreate_ts(new Timestamp(new Date().getTime()));
				info.setStatus("SENT_THE_REQ");
				infos[i]=repo.save(info);

			    Thread th = new Thread(tasks[i]);  
			    th.start();  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int j = 0; j < arr.length; j++)
	    {

			try {
				Audit_Info info=infos[j];
				info.setReponse(tasks[j].get().toString());
				info.setUpdate_ts(tasks[j].get().getTimestamp());
				info.setStatus("RECEIVED_RESPONSE");
				new ExcelWriter(tasks[j].get());
				repo.save(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }  
	}
	
}
