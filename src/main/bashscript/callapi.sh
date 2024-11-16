#!/bin/bash

counter=1
while [ $counter -le 50 ]
do
echo  "Call # "$counter

echo Calling WAN COL
if [ $counter -ne 1 ]
then
echo -n '|' >> _WANCOL.txt
fi
curl --data @input.xml --user 'sb-xxx17799-89f9-45b0-8810-f74c9e79b13a!b357032|it-rt-49425266trial!b26655:xxxxc301-fb06-4299-b764-9681a6ff6268$wr56omcAP52950UtPeW-U4ttUrHenBydOQtGF9j6PEE=' https://xxx25266trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFCWANCOL -X GET >> _WANCOL.txt

echo Calling LAN COL
if [ $counter -ne 1 ]
then
echo -n '|' >> _LANCOL.txt
fi
curl --data @input.xml --user 'sb-xxx17799-89f9-45b0-8810-f74c9e79b13a!b357032|it-rt-49425266trial!b26655:xxxxc301-fb06-4299-b764-9681a6ff6268$wr56omcAP52950UtPeW-U4ttUrHenBydOQtGF9j6PEE=' https://xxx25266trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFCLANCOL -X GET >> _LANCOL.txt

echo Calling ROW
if [ $counter -ne 1 ]
then
echo -n '|' >> _ROW.txt
fi
curl --data @input.xml --user 'sb-xxx17799-89f9-45b0-8810-f74c9e79b13a!b357032|it-rt-49425266trial!b26655:xxxxc301-fb06-4299-b764-9681a6ff6268$wr56omcAP52950UtPeW-U4ttUrHenBydOQtGF9j6PEE=' https://xxx25266trial.it-cpitrial05-rt.cfapps.us10-001.hana.ondemand.com/http/callRFC -X GET >> _ROW.txt
((counter++))
done
echo All DONE
