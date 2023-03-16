package com.be.b.beproject;

public class CustomerRestaurantFieldClass {
int RestId;
String RestName;
String RestOwnerName;
String RestEmail;
String RestLocation;
String RestPhoneNumber;
String AverageRating;
String RestSpeciality;
public CustomerRestaurantFieldClass(int restId, String restName,
		String restOwnerName, String restEmail, String restLocation,
		String restPhoneNumber, String averageRating,String restSpeciality) {
	super();
	RestId = restId;
	RestName = restName;
	RestOwnerName = restOwnerName;
	RestEmail = restEmail;
	RestLocation = restLocation;
	RestPhoneNumber = restPhoneNumber;
	AverageRating = averageRating;
	RestSpeciality=restSpeciality;
}
public int getRestId() {
	return RestId;
}
public String getRestSpeciality() {
	return RestSpeciality;
}
public String getRestName() {
	return RestName;
}
public String getRestOwnerName() {
	return RestOwnerName;
}
public String getRestEmail() {
	return RestEmail;
}
public String getRestLocation() {
	return RestLocation;
}
public String getRestPhoneNumber() {
	return RestPhoneNumber;
}
public String getAverageRating() {
	return AverageRating;
}


}
