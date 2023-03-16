package com.be.b.beproject;

public class CustomerTableOrderHistoryFieldClass {
	String TableOrderId,NoOfSeats,Time,Date,RestaurantId,RestaurantName,BasePayment,Flag;

	public CustomerTableOrderHistoryFieldClass(String tableOrderId,
			String noOfSeats, String time, String date, String restaurantId,
			String restaurantName, String basePayment,String flag) {
		super();
		TableOrderId = tableOrderId;
		NoOfSeats = noOfSeats;
		Time = time;
		Date = date;
		RestaurantId = restaurantId;
		RestaurantName = restaurantName;
		BasePayment = basePayment;
		Flag=flag;
	}

	public String getFlag() {
		return Flag;
	}

	public String getTableOrderId() {
		return TableOrderId;
	}

	public String getNoOfSeats() {
		return NoOfSeats;
	}

	public String getTime() {
		return Time;
	}

	public String getDate() {
		return Date;
	}

	public String getRestaurantId() {
		return RestaurantId;
	}

	public String getRestaurantName() {
		return RestaurantName;
	}

	public String getBasePayment() {
		return BasePayment;
	}
	
}
