<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();

                
		$TableId=$_POST["RestId"];
		$ItemId=(integer)$_POST["ItemId"];
                $db = new DbConnect();
       
// mysql query


//$CategoryId = mysql_result(mysql_query("SELECT CategoryId  FROM categorytableofrestaurant".$TableId." where //CategoryName='$ItemCategory' LIMIT 1"),0);



$query = "delete from menutableofrestaurant".$TableId." where ItemId=$ItemId";
$result = mysql_query($query) or die(mysql_error());

if ($result) {
           $response["error"] = 0;
          $response["message"] = "item added successfully!";
       } else {
           $response["error"] = 1;
          $response["message"] = "Failed to add item!";
      }
       	echo json_encode($response);
	
}
createNewPrediction();
?>