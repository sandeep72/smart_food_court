

<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
         	 $db = new DbConnect();
		$RestId=$_POST["RestId"];
$output=array();

//$result = mysql_query("SELECT round(avg(AmbienceRating),2),round(avg(ServiceRating),2),round(avg(FoodRating),2),round(avg(ValueForMoneyRating),2) FROM reviewtableofrestaurant".$RestId);

$val1=mysql_result(mysql_query("SELECT round(avg(AmbienceRating),2)  FROM reviewtableofrestaurant".$RestId),0);

$val2=mysql_result(mysql_query("SELECT round(avg(ValueForMoneyRating),2)  FROM reviewtableofrestaurant".$RestId),0);

$val3=mysql_result(mysql_query("SELECT round(avg(ServiceRating),2)  FROM reviewtableofrestaurant".$RestId),0);

$val4=mysql_result(mysql_query("SELECT round(avg(FoodRating),2)  FROM reviewtableofrestaurant".$RestId),0);

$val5=mysql_result(mysql_query("SELECT RestName  FROM restaurantmastertable where RestId=$RestId"),0);


	if($val1==null){
	$output["AmbienceRating"]="0";
       	}else{
	$output["AmbienceRating"]=$val1;
	}
	if($val1==null){
	$output["ServiceRating"]="0";
       	}else{
	$output["ServiceRating"]=$val3;
	}
	if($val1==null){
	$output["FoodRating"]="0";
       	}else{
	$output["FoodRating"]=$val4;
	}
	if($val1==null){
	$output["ValueForMoneyRating"]="0";
       	}else{
	$output["ValueForMoneyRating"]=$val2;
	}	
        $output["RestName"]=$val5;


 print(json_encode($output));


}
createNewPrediction();
?>
