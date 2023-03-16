<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();

                $ItemNames=$_POST["ItemNames"];
                $Amount=(float)$_POST["Amount"];
                $CustomerNo =$_POST["CustomerNo"];
		$CustomerName=$_POST["CustomerName"];
		$RestId=$_POST["RestId"];
		$RestName=$_POST["RestName"];
		$CustomerId =$_POST["CustomerId"];
		$TypeOfPayment=$_POST["TypeOfPayment"];
                $db = new DbConnect();
       		
$tempRestId=(integer)$RestId;
$present=mysql_query("select GCMRegId from restaurantmastertable where RestId=$tempRestId and GCMRegId<>'' ");

if($present){

// mysql query
date_default_timezone_set("Asia/Calcutta");

$Date=getdate();
$ReviewDate=" ".$Date['year']."/".$Date['mon']."/".$Date['mday'];
$time=time();
$cur_time=date("H:i:s",$time);



        	$query = "INSERT INTO ordertableofrestaurant".$RestId."(ItemNames,Amount,CustomerNo,CustomerName,CustomerId,OrderDate,OrderTime,TypeOfPayment) VALUES ('$ItemNames',$Amount,'$CustomerNo','$CustomerName','$CustomerId','$ReviewDate','$cur_time','$TypeOfPayment' )";
$result = mysql_query($query) or die(mysql_error());


$val = mysql_result(mysql_query("select max(OrderId) from ordertableofrestaurant".$RestId),0);





$query="insert into OrderTableOfCustomer".$CustomerId." (RestName,Amount,OrderId,ItemNames,OrderDate,RestId,OrderTime,TypeOfPayment) values('$RestName',$Amount,'$val','$ItemNames','$ReviewDate','$RestId','$cur_time','$TypeOfPayment')";

$result = mysql_query($query) or die(mysql_error());


if ($result)  {



while($row=mysql_fetch_assoc($present)){
	$gcmid=$row["GCMRegId"];
}

define( 'API_ACCESS_KEY', 'AIzaSyD6YAVYF-RYrTI8UgT_fNmF5lkmzDd9aaI' );
$registrationIds = array($gcmid);

$msg = array
(
	'message' 	=> 'Notification For Food Order',
	'title'		=> 'BE Project ',
	'subtitle'	=> 'new Food Order ID '.$val,
	'ordertype'=>'food'
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
$response["message"] = "correct credentials!";            
$response["Id"]=$val;
}
else{
	  $response["error"] = 1 ;
          $response["message"] = "Failed to add item!";

}
} 
 else {
           $response["error"] = 2 ;
          $response["message"] = "restaurant not accepting orders ";
      }
       	echo json_encode($response);
	
}
createNewPrediction();
?>

