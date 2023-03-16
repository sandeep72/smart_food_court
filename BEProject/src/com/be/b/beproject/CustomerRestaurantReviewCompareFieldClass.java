package com.be.b.beproject;

public class CustomerRestaurantReviewCompareFieldClass {
	String RestName,RestId,AmbienceRating,FoodRating,ValueForMoneyRating,ServiceRating;

	public String getRestName() {
		return RestName;
	}

	public String getRestId() {
		return RestId;
	}

	public String getAmbienceRating() {
		return AmbienceRating;
	}

	public String getFoodRating() {
		return FoodRating;
	}

	public String getValueForMoneyRating() {
		return ValueForMoneyRating;
	}

	public String getServiceRating() {
		return ServiceRating;
	}

	public CustomerRestaurantReviewCompareFieldClass(String restName,
			String restId, String ambienceRating, String foodRating,
			String valueForMoneyRating, String serviceRating) {
		super();
		RestName = restName;
		RestId = restId;
		AmbienceRating = ambienceRating;
		FoodRating = foodRating;
		ValueForMoneyRating = valueForMoneyRating;
		ServiceRating = serviceRating;
	}
	
}
