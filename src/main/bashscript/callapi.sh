#!/bin/bash
# Try 50 time
echo $parentdir
counter=1
while [ $counter -le 50 ]
do
echo $counter
echo Calling WAN COL
echo -n '|' >> WANCOL.txt
curl --user 'sb-622adaa6-3712-4d19-ab94-55a0fb591b14!b319524|it-rt-a4c2be65trial!b26655:af2ddbff-01a5-4528-9063-46e3babd6e98$wAIznDh7382kh0xlSkA-b6L_sCX94wIdE2lt4SxpKpE=' https://a4c2be65trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFCWANCOL >> WANCOL.txt

echo Calling LAN COL
echo -n '|' >> LANCOL.txt
curl --user 'sb-622adaa6-3712-4d19-ab94-55a0fb591b14!b319524|it-rt-a4c2be65trial!b26655:af2ddbff-01a5-4528-9063-46e3babd6e98$wAIznDh7382kh0xlSkA-b6L_sCX94wIdE2lt4SxpKpE=' https://a4c2be65trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFCLANCOL >> LANCOL.txt
echo Calling ROW
echo -n '|' >> ROW.txt
curl --user 'sb-622adaa6-3712-4d19-ab94-55a0fb591b14!b319524|it-rt-a4c2be65trial!b26655:af2ddbff-01a5-4528-9063-46e3babd6e98$wAIznDh7382kh0xlSkA-b6L_sCX94wIdE2lt4SxpKpE=' https://a4c2be65trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFCs >> ROW.txt
((counter++))
done
echo All done