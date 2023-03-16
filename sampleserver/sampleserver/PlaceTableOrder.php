<?php

include_once './DbConnect.php';
function createNewPrediction() {

	 $response = array();
	$RestId=$_POST["RestId"];

        $CustomerName=$_POST["CustomerName"];
	$CustomerPhone =$_POST["CustomerPhone"];

//new added
	$CustomerId=$_POST["CustomerId"];
	$BasePayment=(integer)$_POST["BasePayment"];
	$RestaurantName=$_POST["RestaurantName"];

	$NoOfSeats=$_POST["TableOrderNoOfSeats"];
	$Time=$_POST["TableOrderTime"];
	$Date=$_POST["TableOrderDate"];

	
                  $db = new DbConnect();
          //      $ReviewDate=" ".$Date['year']."/".$Date['mon']."/".$Date['mday']; 

$tempRestId=(integer)$RestId;
$present=mysql_query("select GCMRegId from restaurantmastertable where RestId=$tempRestId and GCMRegId<>'' ");

if($present){

// mysql query

 $query = "INSERT INTO tableorderofrestaurant".$RestId."(CustomerName,CustomerId,CustomerPhone,NoOfSeats,Time,Date,BasePayment) VALUES('$CustomerName','$CustomerId','$CustomerPhone','$NoOfSeats','$Time','$Date',$BasePayment)";

$result = mysql_query($query) or die(mysql_error());


$val = mysql_result(mysql_query("select max(TableOrderId) from tableorderofrestaurant".$RestId),0);


$query="insert into TableOrderTableOfCustomer".$CustomerId." (TableOrderId,NoOfSeats,Time,Date,RestaurantId,RestaurantName,BasePayment) values('$val','$NoOfSeats','$Time','$Date','$RestId','$RestaurantName',$BasePayment)";


$retval1 = mysql_query($query) or die(mysql_error());





if ($result && $retval1) {

$val = mysql_result(mysql_query("select max(TableOrderId) from tableorderofrestaurant".$RestId),0);



while($row=mysql_fetch_assoc($present)){
	$gcmid=$row["GCMRegId"];
}

define( 'API_ACCESS_KEY', 'AIzaSyD6YAVYF-RYrTI8UgT_fNmF5lkmzDd9aaI' );
$registrationIds = array($gcmid);

$msg = array
(
	'message' 	=> 'Notification For Food Order',
	'title'		=> 'BE Project ',
	'subtitle'	=> 'new Table Order...ID: '.$val,
	'ordertype'=>'table'
);


$fields = array
(
	'registration_ids' 	=> $registrationIds,
	'data'			=> $msg
);
$headers = array
(
	'Authorization: key=' . API_ACCESS_KEY,
	'Content-Type: application/json'
);
 
$ch = curl_init();
curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
curl_setopt( $ch,CURLOPT_POST, true );
curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
$result = curl_exec($ch );
curl_close( $ch );



           $response["error"] = 0;
	   
          $response["message"] = "order added successfully!";
	  $response["TableOrderId"]=$val; 

	
       } else {
           $response["error"] = 1;
         $response["message"] = "Failed to add order for Table!";
      }
}else {
         $response["error"] = 2;
         $response["message"] = "The restaurant is not accepting orders currently.";
      }
       	echo json_encode($response);
	
}
createNewPrediction() ;

?>