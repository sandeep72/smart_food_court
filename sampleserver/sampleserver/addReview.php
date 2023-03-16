<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();

        $ReviewText =$_POST["ReviewText"];
           
	$CustomerId =$_POST["CustomerId"];
	$CustomerName=$_POST["CustomerName"];
	$AmbienceRating=(float)$_POST["AmbienceRating"];
	$ServiceRating=(float)$_POST["ServiceRating"];
	$FoodRating=(float)$_POST["FoodRating"];
	$ValueForMoneyRating=(float)$_POST["ValueForMoneyRating"];
	$TableId=$_POST["TableId"];
	$Rating=($AmbienceRating+$ServiceRating+$FoodRating+$ValueForMoneyRating)/4;

	$Date=getdate();
                  $db = new DbConnect();
                  $ReviewDate=" ".$Date['year']."/".$Date['mon']."/".$Date['mday']; 
// mysql query

        	$query = "INSERT INTO reviewtableofrestaurant".$TableId."(Text,CustomerId,Date,Rating,CustomerName,AmbienceRating,ServiceRating,FoodRating,ValueForMoneyRating) VALUES('$ReviewText','$CustomerId','$ReviewDate',$Rating,'$CustomerName',$AmbienceRating,$ServiceRating,$FoodRating,$ValueForMoneyRating)";

$result = mysql_query($query) or die(mysql_error());

if ($result) {
           $response["error"] = 0;
          $response["message"] = "review added successfully!";
       } else {
           $response["error"] = 1;
         $response["message"] = "Failed to add review!";
      }
       	echo json_encode($response);
	
}
createNewPrediction();
?>