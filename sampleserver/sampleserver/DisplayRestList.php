<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["RestList"]=array();
$result = mysql_query("SELECT * FROM restaurantmastertable");

if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$val=$row["RestId"];
	$val=mysql_result(mysql_query("SELECT round(avg(Rating),2)  FROM reviewtableofrestaurant".$val),0);
	if($val==null){
	$val="0";
}
	$tmp["AverageRating"]=$val;
       	$tmp["RestId"] = $row["RestId"];
        $tmp["RestName"] = $row["RestName"];
        $tmp["RestEmail"] = $row["RestEmail"];
        $tmp["RestOwnerName"] = $row["RestOwnerName"]; 
        $tmp["RestPhoneNumber"]=$row["RestPhoneNumber"];
        $tmp["RestLocation"]=$row["RestLocation"];
	$tmp["RestSpeciality"]=$row["RestSpeciality"];	
        array_push($output["RestList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>