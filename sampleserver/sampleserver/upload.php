<?php
 	// Get image string posted from Android App
$base=$_REQUEST["Image"];
    
	
    
if (isset($base)) {
 
$suffix = createRandomID();

$image_name = "img_".$suffix."_".date("Y-m-d-H-m-s").".png";
 
$binary=base64_decode($base);
header('Content-Type: bitmap; charset=utf-8');
 
$file = fopen("uploadedimages/".$image_name, "wb");
 
fwrite($file, $binary);
 
fclose($file);
 
echo 'Image upload complete, Please check your php file directory';
 
} else {
 
echo 'Image upload incomplete';
}
 
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