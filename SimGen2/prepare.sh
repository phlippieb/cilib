clear
ant jar -q
if [ $? == 0 ]
then 
	echo
	java -jar dist/SimGen3.jar
	if [ $? == 0 ]
	then 
		cp sims.xml ../simulator/xml/
	fi
fi
