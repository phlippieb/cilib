STARTTIME="$(date +%H:%M:%S)"
echo "The time is $STARTTIME"
echo -en "\e[34;1m"
java -jar simulator/target/cilib-simulator-assembly-0.8-SNAPSHOT.jar simulator/xml/sims.xml
echo -en "\e[0m"
echo "Total running time: $STARTTIME - $(date +%H:%M:%S) "
