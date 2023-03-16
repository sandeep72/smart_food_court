<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

 	 $response = array();

                  
$CustomerName =$_POST["CustomerName"];
		  
$CustomerPhoneNumber=$_POST["CustomerPhoneNo"];
                  
$CustomerEmail=$_POST["CustomerEmail"];
$CustomerPassword=$_POST["CustomerPassword"];		  
                  $db=new dbconnect();
       
// mysql query


$query = "INSERT INTO customermastertable(CustName,CustPhoneNO,CustEmail,CustPassword) VALUES('$CustomerName','$CustomerPhoneNumber','$CustomerEmail','$CustomerPassword')";

$result = mysql_query($query) or die(mysql_error());


$CustId = mysql_result(mysql_query("SELECT MAX(Id)  FROM customermastertable LIMIT 1"),0);
$sql = 'CREATE TABLE  OrderTableOfCustomer'.$CustId.' (RestName varchar(30),Amount Float,OrderId varchar(11),ItemNames text,OrderDate Date,RestId varchar(11),OrderTime Time,TypeOfPayment varchar(20) )';
       


$retval1 = mysql_query( $sql );


$sql = 'CREATE TABLE  TableOrderTableOfCustomer'.$CustId.' (TableOrderId varchar(11),NoOfSeats varchar(2),Time varchar(10),Date varchar(12),RestaurantId varchar(11),RestaurantName varchar(50),BasePayment int,Flag int default 0)';
       


$retval2 = mysql_query( $sql );


if ($result && $retval1 && $retval2) {
           $response["error"] = 0;
          
       } else {
           $response["error"] = 1;
           
      }

      	echo json_encode($response);
	
}
createNewPrediction();
?> 