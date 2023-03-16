package com.be.b.beproject;

public class RestaurantNotificationFieldClass {
String ItemNames;
String Amount;
String CustomerNo;
String CustomerName;
String OrderId;
String OrderTime;
String OrderDate;
String TypeOfPayment;

public RestaurantNotificationFieldClass(String itemNames, String amount,
		String customerNo, String customerName, String orderId,String orderDate,String orderTime,String typeOfPayment) {
	super();
	ItemNames = itemNames;
	Amount = amount;
	CustomerNo = customerNo;
	CustomerName = customerName;
	OrderId = orderId;
	OrderTime = orderTime;
	OrderDate = orderDate;
	TypeOfPayment= typeOfPayment;
}
public String getTypeOfPayment() {
	return TypeOfPayment;
}
public String getOrderTime() {
	return OrderTime;
}
public String getOrderDate() {
	return OrderDate;
}
public String getItemNames() {
	return ItemNames;
}
public String getAmount() {
	return Amount;
}
public String getCustomerNo() {
	return CustomerNo;
}
public String getCustomerName() {
	return CustomerName;
}
public String getOrderId() {
	return OrderId;
}



}
