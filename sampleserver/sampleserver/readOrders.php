<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["OrderList"]=array();

$RestId=$_POST["RestaurantId"];

$result = mysql_query("SELECT * FROM ordertableofrestaurant".$RestId." where flag=0");
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$tmp["OrderId"] = $row["OrderId"];
       	$tmp["ItemNames"] = $row["ItemNames"];
        $tmp["Amount"] = $row["Amount"];
        $tmp["CustomerNo"] = $row["CustomerNo"];
        $tmp["CustomerName"] = $row["CustomerName"];
	$tmp["OrderDate"]=$row["OrderDate"];
	$tmp["OrderTime"]=$row["OrderTime"];
	$tmp["TypeOfPayment"]=$row["TypeOfPayment"];
        array_push($output["OrderList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>