if [ -f sims.xml ]; then rm sims.xml; fi
echo -ne '\e[1;30m'
ant jar -q
java -jar dist/SimGen3.jar
echo -ne '\e[0m';
cat sims.xml | less
echo
