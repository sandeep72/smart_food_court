<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$response = array();


$RestId=$_POST["RestaurantId"];
$TableOrderId=$_POST["OrderId"];
$CustomerId=$_POST["CustomerId"];
$FLAG=(integer)$_POST["FLAG"];



if($FLAG==1){

$result =mysql_query("update tableorderofrestaurant".$RestId." set Flag=1 where TableOrderId=".$TableOrderId);
$msg = array
(
	'message' 	=> 'Notification For Confirmed Table Order',
	'title'		=> 'BE Project ',
	'subtitle'	=> 'Table Order with Id :'.$TableOrderId.' confirmed.',
	'ordertype'=>'tableStatus'
);


$result1 =mysql_query("update tableordertableofcustomer".$CustomerId." set Flag=1 where RestaurantId='$RestId' and TableOrderId=".$TableOrderId);
}
else{

$result =mysql_query("update tableorderofrestaurant".$RestId." set Flag=2 where TableOrderId=".$TableOrderId);
$msg = array
(
	'message' 	=> 'Notification For Cancelled Table Order',
	'title'		=> 'BE Project ',
	'subtitle'	=> 'Table Order with Id :'.$TableOrderId.' cancelled.',
	'ordertype'=>'tableStatus'
);


$result1 =mysql_query("update tableordertableofcustomer".$CustomerId." set Flag=2 where RestaurantId='$RestId' and TableOrderId=".$TableOrderId);

}


if($result && $result1){

$response["error"] = 0;

$tempCustId=(integer)$CustomerId;
$present=mysql_query("select GCMRegId from customermastertable where id=$tempCustId and GCMRegId<>'' ");

while($row=mysql_fetch_assoc($present)){
	$gcmid=$row["GCMRegId"];
}

define( 'API_ACCESS_KEY', 'AIzaSyD6YAVYF-RYrTI8UgT_fNmF5lkmzDd9aaI' );
$registrationIds = array($gcmid);

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




}else{
$response["error"]=1;
} 

 print(json_encode($response));

}
createNewPrediction() ;

?>