<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();

                $ItemName =$_POST["ItemName"];
                $ItemPrice=(integer)$_POST["ItemPrice"];
                
		$ItemDescription=$_POST["ItemDescription"];
		$TableId=$_POST["RestId"];
		$ItemId=(integer)$_POST["ItemId"];
                $db = new DbConnect();
       

// mysql query





$query = "update menutableofrestaurant".$TableId." set ItemName='$ItemName',ItemPrice=$ItemPrice,ItemDescription='$ItemDescription' where ItemId=$ItemId ";
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