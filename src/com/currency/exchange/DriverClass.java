package com.currency.exchange;

import java.util.*;

public class DriverClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<String> inputCsv = new ArrayList<>();
		inputCsv.add("106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:01:001");
		inputCsv.add("107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
		inputCsv.add("108, GBP/USD, 1.2500,1.2560,01-06-2020 12:01:02:002");
		inputCsv.add("109, GBP/USD, 1.2499,1.2561,01-06-2020 12:01:02:100");
		inputCsv.add("110, EUR/JPY, 119.61,119.91,01-06-2020 12:01:02:110");
		inputCsv.add("111, GBP/USD, 1.2498,1.2562,01-06-2020 12:01:02:120");
		inputCsv.add("112, EUR/JPY, 119.63,119.90,01-06-2020 12:01:02:125");
		//inputCsv.add("");
		//inputCsv.add(null);
		
		//calling subscriber service while continuously listening to market prices
		SubscriberServiceImpl testCall = new SubscriberServiceImpl();
		
		/* REST end point can be defined here to get live stream of all FX with adjusted price 
		---- GET  /subscribeToFXLivestream/getAll */
		
		inputCsv.forEach(i -> testCall.onMessage(i));
		
		/* REST end point can be defined here to get latest price of requested FX i.e inputFX 
		---- GET  /ssubscribeToFXLivestream/getLatestMarketPrice/{fxInstrumentName} */
		
		//String fxInstrumentName = null;
		//String fxInstrumentName = "";
		//String fxInstrumentName = "EUR/JP";
		//String fxInstrumentName = "EUR/JPY";
		while(true) {
			@SuppressWarnings("resource")
			Scanner userInput = new Scanner(System.in);  
		    System.out.println("Enter FX instrument name: ");

		    String fxInstrumentName = userInput.nextLine();
		    
			testCall.latestFXPrice(fxInstrumentName != null ? fxInstrumentName.toUpperCase() : "");
		}
		
		
		
	}

}
