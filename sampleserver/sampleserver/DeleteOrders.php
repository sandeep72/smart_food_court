<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$response = array();


$RestId=$_POST["RestaurantId"];

$OrderId=$_POST["OrderId"];

$result =mysql_query("update ordertableofrestaurant".$RestId." set flag=1 where Orderid=".$OrderId);

if($result){

$response["error"] = 0;

}
else{
$response["error"]=1;
} 

 print(json_encode($response));

}
createNewPrediction() ;

?>