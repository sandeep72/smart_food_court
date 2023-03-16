<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();

                $CategoryName =$_POST["CategoryName"];
                
		$TableId=$_POST["RestId"];
	
                $db = new DbConnect();
       
// mysql query

        	$query = "INSERT INTO categorytableofrestaurant".$TableId."(CategoryName) VALUES ('$CategoryName')";

$result = mysql_query($query) or die(mysql_error());

if ($result) {
           $response["error"] = 0;
          $response["message"] = "category added successfully!";
       } else {
           $response["error"] = 1;
          $response["message"] = "Failed to add category!";
      }
       	echo json_encode($response);
	
}
createNewPrediction();
?>