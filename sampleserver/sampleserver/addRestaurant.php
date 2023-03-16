<?php

include_once './DbConnect.php';
function createNewPrediction() {
      

 	 $response = array();

                  $RestOwnerName = $_POST["RestaurantOwnerName"];
	$RestPhoneNumber=$_POST["RestaurantPhoneNumber"];
                  $RestEmail = $_POST["RestaurantEmail"];
	$RestName=$_POST["RestaurantRestaurantName"];
	$RestLocation = $_POST["RestaurantLocation"];
	$RestPassword=$_POST["RestaurantPassword"];
	$RestSpeciality=$_POST["RestaurantSpeciality"];
                $db = new DbConnect();
       
// mysql query

        	$query = "INSERT INTO restaurantmastertable(RestOwnerName,RestPhoneNumber,RestEmail,RestName,RestLocation,RestPassword,RestSpeciality) VALUES ('$RestOwnerName','$RestPhoneNumber','$RestEmail','$RestName','$RestLocation','$RestPassword','$RestSpeciality')";
$result1 = mysql_query($query) or die(mysql_error());

if ($result1) {
           $response["error"] = false;
          $response["message"] = "Prediction added successfully!";
       } else {
           $response["error"] = true;
           $response["message"] = "Failed to add prediction!";
      }
       

$maxItemNum = mysql_result(mysql_query("SELECT MAX(RestId)  FROM restaurantmastertable LIMIT 1"),0);
$sql = 'CREATE TABLE  MenuTableOfRestaurant'.$maxItemNum.' ( ItemId int auto_increment primary key, ItemName VARCHAR(20) NOT NULL, ItemPrice INT NOT NULL, ItemDescription   VARCHAR(50) NOT NULL,CategoryId int,ImageURL varchar(100) )';
       


$retval1 = mysql_query( $sql );
if ($retval1) {
           $response["error"] = false;
          $response["message"] = "Prediction added successfully!";
       } else {
           $response["error"] = true;
           $response["message"] = "Failed to add prediction!";
      }


mkdir("uploadedimages/restaurant".$maxItemNum, 0777,true);




$sql = 'CREATE TABLE  OrderTableOfRestaurant'.$maxItemNum.' (OrderId int auto_increment primary key, CustomerNo VARCHAR(20) NOT NULL, Amount float NOT NULL,ItemNames  text NOT NULL,CustomerName varchar(30),CustomerId varchar(11),flag int default 0,OrderDate Date,OrderTime Time,TypeOfPayment varchar(20))';

$retval2 = mysql_query( $sql );
if ($retval2) {
           $response["error"] = false;
          $response["message"] = "Prediction added successfully!";
       } else {
           $response["error"] = true;
           $response["message"] = "Failed to add prediction!";
      }

$sql = 'CREATE TABLE  ReviewTableOfRestaurant'.$maxItemNum.' (Text varchar(100) NOT NULL,CustomerId varchar(30)NOT NULL,Date Date NOT NULL,AmbienceRating float NOT NULL,CustomerName varchar(30),ServiceRating float,FoodRating float,ValueForMoneyRating float,Rating float)';

$retval3 = mysql_query( $sql );
if ($retval3) {
           $response["error"] = false;
          $response["message"] = "Prediction added successfully!";
       } else {
           $response["error"] = true;
           $response["message"] = "Failed to add prediction!";
      }

$sql = 'CREATE TABLE  TableOrderOfRestaurant'.$maxItemNum.' (TableOrderId int auto_increment primary key,CustomerName varchar(100),CustomerPhone varchar(30),NoOfSeats varchar(2),Time varchar(10),Date varchar(12),CustomerId varchar(11),BasePayment int,Flag int default 0,Amount int default 0,ItemNames varchar(500),TypeOfPayment varchar(25),TableWithFoodFlag int default 0)';

$retval4 = mysql_query( $sql );

$sql ='CREATE TABLE  categorytableofrestaurant'.$maxItemNum.' (CategoryId int auto_increment primary key,CategoryName varchar(25))';

$retval5 = mysql_query( $sql );








if ($retval1 && $retval2 && $retval3 && $retval4 && $result1 && $retval5) {
           $response["error"] = 0;
          $response["message"] = "Restaurant  added successfully!";
       } else {
           $response["error"] = 1;
           $response["message"] = "Failed to add restaurant!";
      }


 	echo json_encode($response);
	
}
createNewPrediction();
?>