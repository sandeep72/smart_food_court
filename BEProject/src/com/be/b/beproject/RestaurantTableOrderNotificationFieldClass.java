package com.be.b.beproject;

public class RestaurantTableOrderNotificationFieldClass {
	String TableOrderId,CustomerName,CustomerPhone,NoOfSeats,Time,Date,CustomerId,Flag,Amount,TypeOfPayment,ItemNames;

	

	public String getAmount() {
		return Amount;
	}

	public String getTypeOfPayment() {
		return TypeOfPayment;
	}

	public String getItemNames() {
		return ItemNames;
	}

	public RestaurantTableOrderNotificationFieldClass(String tableOrderId,
			String customerName, String customerPhone, String noOfSeats,
			String time, String date, String customerId, String flag,
			String amount, String typeOfPayment, String itemNames) {
		super();
		TableOrderId = tableOrderId;
		CustomerName = customerName;
		CustomerPhone = customerPhone;
		NoOfSeats = noOfSeats;
		Time = time;
		Date = date;
		CustomerId = customerId;
		Flag = flag;
		Amount = amount;
		TypeOfPayment = typeOfPayment;
		ItemNames = itemNames;
	}

	public String getFlag() {
		return Flag;
	}

	public String getCustomerId() {
		return CustomerId;
	}

	public String getTableOrderId() {
		return TableOrderId;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public String getCustomerPhone() {
		return CustomerPhone;
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
	
}
