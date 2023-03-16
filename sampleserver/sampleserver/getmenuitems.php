<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
$db = new DbConnect();

$output=array();
$output["menulist"]=array();
$TableId=$_POST["RestId"];


$result = mysql_query("SELECT * FROM menutableofrestaurant".$TableId);
if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 
	$tmp["Id"] = $row["ItemId"];
       	$tmp["CategoryId"] = $row["CategoryId"];
        $tmp["ItemName"] = $row["ItemName"];
        $tmp["ItemPrice"] = $row["ItemPrice"];
        $tmp["ItemDescription"] = $row["ItemDescription"];
	$tmp["ImageURL"]=$row["ImageURL"];
        array_push($output["menulist"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>