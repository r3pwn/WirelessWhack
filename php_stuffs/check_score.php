<html>
<head> <meta http-equiv="refresh" content="5" /> </head>
<b>
<p style="text-align:center">
<font size="40"><?php

$filename = $_GET['sessid'];;
$failure = "failure";

try {
        $fileData = file_get_contents($filename);
        $num = (int) $fileData;
        if ($fileData == "") {
                $num = 0;
        }
        if ($fileData != $num) {
                die($failure . "1");
        }
        die($num . "");
} catch (Exception $e) {
        die($failure . "5");
} ?></font></p></b>
</html>
