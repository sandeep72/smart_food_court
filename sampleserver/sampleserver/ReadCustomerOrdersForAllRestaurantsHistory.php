<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["OrderList"]=array();

$CustomerId=$_POST["CustomerId"];

$result = mysql_query("SELECT * FROM ordertableofcustomer".$CustomerId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$tmp["OrderId"] = $row["OrderId"];
       	$tmp["ItemNames"] = $row["ItemNames"];
        $tmp["Amount"] = $row["Amount"];
        $tmp["OrderDate"] = $row["OrderDate"];
        $tmp["RestName"] = $row["RestName"];
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