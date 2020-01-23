$webclient = New-Object System.Net.WebClient
$mydocs = [environment]::getfolderpath("mydocuments")
$url = "https://raw.githubusercontent.com/Azure/usql/master/Examples/Samples/Data/SearchLog.tsv"
$basename = Split-Path $url -Leaf
$output_filename = Join-Path $mydocs $basename
$webclient.DownloadFile($url,$output_filename)