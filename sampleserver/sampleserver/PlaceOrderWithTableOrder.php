<?php

include_once './DbConnect.php';
function createNewPrediction() {

	 $response = array();
	$RestId=$_POST["RestId"];
	$TableOrderId=$_POST["TableOrderId"];
	$ItemNames=$_POST["ItemNames"];
        $Amount=(float)$_POST["Amount"];
        $TypeOfPayment=$_POST["TypeOfPayment"];

	

	
                  $db = new DbConnect();
          
// mysql query

 $query = "update tableorderofrestaurant".$RestId." set ItemNames='$ItemNames',Amount=$Amount,TypeOfPayment='$TypeOfPayment',TableWithFoodFlag=1 where TableOrderId=$TableOrderId";

$result = mysql_query($query) or die(mysql_error());








if ($result ) {
           $response["error"] = 0;
	   
          $response["message"] = "order added successfully!";
	  	
       } else {
           $response["error"] = 1;
         $response["message"] = "Failed to add order for Table!";
      }
       	echo json_encode($response);
	
}
createNewPrediction() ;

?>