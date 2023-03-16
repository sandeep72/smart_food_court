<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

 	 $response = array();

                   $RestPassword =$_POST["RestaurantPassword"];
	 $RestEmail =$_POST["RestaurantEmail"];
	
                $db = new DbConnect();

	


        	$query = "select * from restaurantmastertable where RestPassword='$RestPassword' and  restEmail='$RestEmail' ";
                  $result = mysql_query($query) or die(mysql_error());

$rowCount=mysql_num_rows($result);

if ($rowCount>0)  {
$val = mysql_result(mysql_query("select RestId from restaurantmastertable where RestPassword='$RestPassword' and  restEmail='$RestEmail'"),0);
$response["error"] = 0;
$response["message"] = "correct credentials!";            
$response["Id"]=$val;
} 


else {
            $response["error"] = 1;
            $response["message"] = "Incorrect email or password!";

 }
echo json_encode($response);
}
createNewPrediction();
?>