<?php

include_once './DbConnect.php';
function createNewPrediction() {
      
                $db = new DbConnect();

$output=array();
$output["SpecialityList"]=array();
$result = mysql_query("SELECT * FROM restaurantspecialitytable");

if ($result) {
 while($row=mysql_fetch_assoc($result)){
        $tmp = array(); 

	
       	$tmp["SpecialityId"] = $row["SpecialityId"];
        $tmp["SpecialityName"] = $row["SpecialityName"];
        
        array_push($output["SpecialityList"], $tmp);
}
 

 print(json_encode($output));
}
}
createNewPrediction() ;

?>