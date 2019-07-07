#Build (skip tests)
mvn -Dmaven.test.skip=true clean package

#Remove obsolete jar
rm crypto.jar

#Copy jar
cp ./target/crypto.jar crypto.jar
