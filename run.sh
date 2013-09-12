while [ 1 ]; do
	clear
	echo -ne "\e[1;30m"
	cat splash.txt
	echo -ne "\e[0m"
	export SIMULATION_START_TIME=$(date +%H:%M:%S)
	echo -e "\e[1;31mstart time: $SIMULATION_START_TIME\e[0m"
	java -Xms2g -Xmx6g -jar simulator/target/cilib-simulator-assembly-0.8-SNAPSHOT.jar simulator/xml/test.xml
	echo
	echo -e "\e[1;31mstart time: $SIMULATION_START_TIME"
	echo -n "  end time: "
	date +%H:%M:%S
	echo -e "\e[0m"
	read -p "continue..."
done

