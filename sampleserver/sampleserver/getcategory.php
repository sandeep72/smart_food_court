<?php

include_once './DbConnect.php';
function createNewPrediction() {
$db = new DbConnect();

$output=array();
$output["categoryList"]=array();

$TableId=$_POST["RestId"];

$result = mysql_query("SELECT * FROM categorytableofrestaurant".$TableId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        
	$tmp = array(); 
	$tmp["CategoryId"] = $row["CategoryId"];
       	$tmp["CategoryName"] = $row["CategoryName"];
        
        array_push($output["categoryList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>