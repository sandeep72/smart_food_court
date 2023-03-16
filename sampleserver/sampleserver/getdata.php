

<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
         	 $db = new DbConnect();
		$RestId=$_POST["RestId"];
$output=array();
$output["Review"]=array();
$result = mysql_query("SELECT * FROM reviewtableofrestaurant".$RestId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array();        
        $tmp["Text"] = $row["Text"];
        $tmp["userId"] = $row["CustomerName"];
        $tmp["Date"] = $row["Date"]; 
        $tmp["Rating"]=$row["Rating"];
	$tmp["AmbienceRating"]=$row["AmbienceRating"];
	$tmp["ServiceRating"]=$row["ServiceRating"];
	$tmp["FoodRating"]=$row["FoodRating"];
	$tmp["ValueForMoneyRating"]=$row["ValueForMoneyRating"];
        array_push($output["Review"], $tmp);





 
}
 print(json_encode($output));
}

}
createNewPrediction();
?>
