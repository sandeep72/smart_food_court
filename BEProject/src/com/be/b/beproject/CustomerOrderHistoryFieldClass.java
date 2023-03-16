package com.be.b.beproject;

public class CustomerOrderHistoryFieldClass {
	String ItemNames;
	String Amount;
	String OrderDate;
	String RestName;
	String OrderId;
	String OrderTime;
	String TypeOfPayment;
	public CustomerOrderHistoryFieldClass(String itemNames, String amount,
			String orderDate, String restName, String orderId,String orderTime,String typeOfPayment) {
		super();
		ItemNames = itemNames;
		Amount = amount;
		OrderDate = orderDate;
		RestName = restName;
		OrderId = orderId;
		OrderTime=orderTime; 
		TypeOfPayment=typeOfPayment;
	}
	
	public String getTypeOfPayment() {
		return TypeOfPayment;
	}

	public String getOrderTime() {
		return OrderTime;
	}
	public String getItemNames() {
		return ItemNames;
	}
	public void setItemNames(String itemNames) {
		ItemNames = itemNames;
	}
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(String orderDate) {
		OrderDate = orderDate;
	}
	public String getRestName() {
		return RestName;
	}
	public void setRestName(String restName) {
		RestName = restName;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
}
