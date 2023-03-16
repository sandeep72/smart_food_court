<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

	 $response = array();
		$base=$_REQUEST["Image"];
                $ItemName =$_POST["ItemName"];
                $ItemPrice=(integer)$_POST["ItemPrice"];
                $ItemCategory =$_POST["ItemCategory"];
		$ItemDescription=$_POST["ItemDescription"];
		$TableId=$_POST["RestId"];
	
                $db = new DbConnect();
       
// mysql query


$CategoryId = mysql_result(mysql_query("SELECT CategoryId  FROM categorytableofrestaurant".$TableId." where CategoryName='$ItemCategory' LIMIT 1"),0);


$suffix = createRandomID();

$image_name = "img_".$suffix."_".date("Y-m-d-H-m-s").".png";
 
$binary=base64_decode($base);
header('Content-Type: bitmap; charset=utf-8');
 
$file = fopen("uploadedImages/Restaurant".$TableId."/".$image_name, "wb");
 
fwrite($file, $binary);
 
fclose($file);

$path="uploadedImages/Restaurant".$TableId."/".$image_name;



        	$query = "INSERT INTO menutableofrestaurant".$TableId."(ItemName,ItemPrice,CategoryId,ItemDescription,ImageURL) VALUES ('$ItemName',$ItemPrice,$CategoryId,'$ItemDescription','$path' )";
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

function createRandomID() {
 
$chars = "abcdefghijkmnopqrstuvwxyz0123456789?";
//srand((double) microtime() * 1000000);
 
$i = 0;
 
$pass = "";
 
while ($i <= 5) {
 
$num = rand() % 33;
 
$tmp = substr($chars, $num, 1);
 
$pass = $pass . $tmp;
 
$i++;
}
return $pass;
}

?>