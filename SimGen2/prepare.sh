ant jar -q
if [ $? == 0 ]
then 
	echo
	java -jar dist/SimGen3.jar
	if [ $? == 0 ]
	then 
		echo "Copying file to cilib directory"
		cp sims.xml ../simulator/xml/
	fi
fi
