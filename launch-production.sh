#Remove old log
rm nohup.out

#Launch jar
nohup java -jar crypto.jar --spring.profiles.active=prod &
