package com.be.b.beproject;

public class CustomerFoodOrderPlacementItemFieldClass {
String ItemName,ItemPrice,ItemDescription,Id,ImageURL;

public String getItemName() {
	return ItemName;
}

public String getItemPrice() {
	return ItemPrice;
}

public String getItemDescription() {
	return ItemDescription;
}

public CustomerFoodOrderPlacementItemFieldClass(String itemName,
		String itemPrice, String itemDescription, String id,String imageURL) {
	super();
	ItemName = itemName;
	ItemPrice = itemPrice;
	ItemDescription = itemDescription;
	Id = id;
	ImageURL=imageURL;
}

public String getImageURL() {
	return ImageURL;
}

public String getId() {
	return Id;
}



}
