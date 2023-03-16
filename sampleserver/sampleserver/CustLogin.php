<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

 	 $response = array();

         $CustPassword =$_POST["CustomerPassword"];
	 $CustEmail =$_POST["CustomerEmail"];
	
                $db = new DbConnect();
               

	

        	$query = "select * from customermastertable where CustPassword='$CustPassword' and  CustEmail='$CustEmail' ";
                  $result = mysql_query($query) or die(mysql_error());

$rowCount=mysql_num_rows($result);

if ($rowCount>0)  {
$val = mysql_result(mysql_query("select Id from customermastertable where CustPassword='$CustPassword' and  CustEmail='$CustEmail'"),0);

$val1 = mysql_result(mysql_query("select CustName from customermastertable where CustPassword='$CustPassword' and  CustEmail='$CustEmail'"),0);

$val2 = mysql_result(mysql_query("select CustPhoneNo from customermastertable where CustPassword='$CustPassword' and  CustEmail='$CustEmail'"),0);

$response["error"] = 0;
$response["message"] = "correct credentials!";            
$response["Id"]=$val;
$response["CustomerName"]=$val1;
$response["CustPhoneNo"]=$val2;
} 


else {
            $response["error"] = 1;
            $response["message"] = "Incorrect email or password!";

 }
echo json_encode($response);
}
createNewPrediction();
?>