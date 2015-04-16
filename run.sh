STARTTIME="$(date +%H:%M:%S)"
echo "start time: $STARTTIME"
echo -en "\e[33;1m"
java -jar simulator/target/cilib-simulator-assembly-0.8-SNAPSHOT.jar simulator/xml/sims.xml
echo -en "\e[0m\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b\b \b"
echo "running time: $STARTTIME - $(date +%H:%M:%S) "
