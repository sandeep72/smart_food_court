package com.be.b.beproject;



public class ReviewFields {
String Text;
String Rating;
String Date;
String UserId;
String AmbienceRating;
String ServiceRating;
String FoodRating;
String ValueForMoneyRating;
public ReviewFields(String text, String rating, String date, String userId,
		String ambienceRating, String serviceRating, String foodRating,
		String valueForMoneyRating) {
	super();
	Text = text;
	Rating = rating;
	Date = date;
	UserId = userId;
	AmbienceRating = ambienceRating;
	ServiceRating = serviceRating;
	FoodRating = foodRating;
	ValueForMoneyRating = valueForMoneyRating;
	
}
public String getText() {
	return Text;
}
public String getRating() {
	return Rating;
}
public String getDate() {
	return Date;
}
public String getUserId() {
	return UserId;
}
public String getAmbienceRating() {
	return AmbienceRating;
}
public String getServiceRating() {
	return ServiceRating;
}
public String getFoodRating() {
	return FoodRating;
}
public String getValueForMoneyRating() {
	return ValueForMoneyRating;
}


}
