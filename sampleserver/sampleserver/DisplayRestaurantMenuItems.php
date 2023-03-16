<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["MenuList"]=array();

$RestId=$_POST["RestaurantId"];

$result = mysql_query("SELECT * FROM menutableofrestaurant".$RestId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
       	$tmp["ItemName"] = $row["ItemName"];
        $tmp["ItemPrice"] = $row["ItemPrice"];
        $tmp["ItemDescription"] = $row["ItemDescription"];
        
        array_push($output["MenuList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>