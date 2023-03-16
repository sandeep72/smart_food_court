<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["OrderList"]=array();

$CustomerId=$_POST["CustomerId"];

$result = mysql_query("SELECT * FROM tableordertableofcustomer".$CustomerId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$tmp["TableOrderId"] = $row["TableOrderId"];
       	$tmp["NoOfSeats"] = $row["NoOfSeats"];
        $tmp["Time"] = $row["Time"];
        $tmp["Date"] = $row["Date"];
        $tmp["BasePayment"] = $row["BasePayment"];
	$tmp["RestaurantName"]=$row["RestaurantName"];
	$tmp["RestaurantId"]=$row["RestaurantId"];
	//$tmp["TypeOfPayment"]=$row["TypeOfPayment"];
	$tmp["Flag"]=$row["Flag"];
        array_push($output["OrderList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>