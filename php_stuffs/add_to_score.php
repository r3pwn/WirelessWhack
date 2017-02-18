<?php

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
        $num++;
        if (is_writeable($filename)) {
                if (file_put_contents($filename, $num . "")) {
                        die($num . "");
                } else {
                        die($failure . "3");
                }
        } else {
                die($failure . "4");
        }
} catch (Exception $e) {
        die($failure . "5");
}?>
