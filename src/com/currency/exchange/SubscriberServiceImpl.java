package com.currency.exchange;

import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Logger;

public class SubscriberServiceImpl implements SubscriberService{
	
	
	Logger logger = Logger.getAnonymousLogger();
	Map<String, MarketPrice> latestFXPriceMap = new HashMap<>();
	
	//VNK - method to segregate CSV values
	
	@Override
	public void onMessage(String input){
		List<String> resultList = new ArrayList<>();
		
		try {
			
			Long testUniqueId = 0L;
			Double testBid = 0.0;
			Double testAsk =0.0;
			String instrumentName = "NA/NA";
			String testTimestamp = "00-00-0000 00:00:00:000";
			
			if(input != null) {
				resultList = Arrays.asList(input.split("\\s*,\\s*"));
			}	
			else {
				resultList.add("");
			}
				
			
			
			if(resultList.get(0).equals("")) {
				testUniqueId = 0L;
				testBid = 0.0;
				testAsk =0.0;
				instrumentName = "NA/NA";
				testTimestamp = "00-00-0000 00:00:00:000";
				
			}
			else if(!resultList.isEmpty()) {
				
				testUniqueId = Long.valueOf(resultList.get(0));
				testBid = Double.valueOf(resultList.get(2));
				testAsk = Double.valueOf(resultList.get(3));
				instrumentName = resultList.get(1);
				testTimestamp = resultList.get(4);
				
			}
			
			
			
			/* setting latest ask and bid price for latest entry*/
			if(latestFXPriceMap.containsKey(instrumentName)) {
				MarketPrice latestMarketPrice = latestFXPriceMap.get(instrumentName);
				latestMarketPrice.setAsk(testAsk);
				latestMarketPrice.setBid(testBid);
				latestFXPriceMap.put(instrumentName, latestMarketPrice);
				
			}else {
				MarketPrice marketPrice = new MarketPrice(testUniqueId, instrumentName, testBid, testAsk, testTimestamp);
				latestFXPriceMap.put(instrumentName, marketPrice);
			}
			
			if(latestFXPriceMap.containsKey(instrumentName)) {
				addCommission(testBid, testAsk, instrumentName);
			//logger.info("Inside marketPriceList method");
			}
		
			
		}
		catch(IllegalArgumentException e){
			
			e.printStackTrace();
			logger.warning("Input CSV not formatted properly or have a null entry. " + e);
			
		}
		catch(NullPointerException e){
			
			e.printStackTrace();
			logger.warning("Input CSV not formatted properly or have a null entry. " + e);
				
		}
		catch(Exception e){
			
			e.printStackTrace();
			logger.warning("Exception occurred in marketPriceList() method. " + e);
			
			
		}
	
	}
	
	//VNK - method to add commission to the amount
	public void addCommission(Double bid, Double ask, String instrumentName){
		try {
			
			NumberFormat nf= NumberFormat.getInstance();
	        nf.setMaximumFractionDigits(4);
	        
			Double bidWithCommisson = bid - bid*0.001;
			Double askWithCommission = ask + ask*0.001; 
			
			System.out.println(instrumentName +" Sell @ : "+ nf.format(bidWithCommisson) + " | "+"Buy @ : "+ nf.format(askWithCommission));
			//logger.info("Inside addCommission method" );
			
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			logger.warning("Exception occurred in addCommission() method." + e);
			
		}
		
	}
	
	//VNK - Filtering latest market price for requested FX 
	
	public void latestFXPrice(String instrumentName) {
		
		
			if(!instrumentName.equals("") && latestFXPriceMap.containsKey(instrumentName)) {
				
					MarketPrice sampleMarketPrice = latestFXPriceMap.get(instrumentName);
					latestFXWithCommission(sampleMarketPrice.getBid(), sampleMarketPrice.getAsk(), instrumentName);
				
			}else {
				
				System.out.println("Please provide listed/known instrument name.");
			}
		
		
	}
	
	
	public void latestFXWithCommission(Double bid, Double ask, String instrumentName){
		try {
			
			NumberFormat nf= NumberFormat.getInstance();
	        nf.setMaximumFractionDigits(4);
	        
			Double bidWithCommisson = bid - bid*0.001;
			Double askWithCommission = ask + ask*0.001; 
			
			System.out.println("Latest price for " + instrumentName +" Sell @ : "+ nf.format(bidWithCommisson) + " | "+"Buy @ : "+ nf.format(askWithCommission));
			
			
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			logger.warning("Exception occurred in latestFXWithCommission() method." + e);
			
		}
		
	}
	
	
	

}
