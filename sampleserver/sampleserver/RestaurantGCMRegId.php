<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

 	 $response = array();

         $Id =(integer)$_POST["RestaurantId"];
	 $GCMRegId =$_POST["GCMRegId"];
	
                $db = new DbConnect();

        	$query = "update restaurantmastertable set GCMRegID='$GCMRegId' where RestId=$Id ";
                $result = mysql_query($query) or die(mysql_error());

//AIzaSyD6YAVYF-RYrTI8UgT_fNmF5lkmzDd9aaI

}
createNewPrediction();
?>

