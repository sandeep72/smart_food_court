<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["OrderList"]=array();

$RestId=$_POST["RestaurantId"];

$result = mysql_query("SELECT * FROM tableorderofrestaurant".$RestId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$tmp["TableOrderId"] = $row["TableOrderId"];
       	$tmp["CustomerName"] = $row["CustomerName"];
	$tmp["CustomerId"] = $row["CustomerId"];
        $tmp["CustomerPhone"] = $row["CustomerPhone"];
        $tmp["NoOfSeats"] = $row["NoOfSeats"];
	$tmp["BasePayment"] = $row["BasePayment"];
	$tmp["ItemNames"]=$row["ItemNames"];
	$tmp["Amount"]=$row["Amount"];
	$tmp["TypeOfPayment"]=$row["TypeOfPayment"];
        $tmp["Time"] = $row["Time"];
	$tmp["Date"]=$row["Date"];
	$tmp["Flag"]=$row["Flag"];
        array_push($output["OrderList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>