echo "Building Local Dependencies"
mkdir local_dependencies
cd local_dependencies
echo "Cloning corpus.sinhala.wildcard.search"
git clone https://github.com/lasandun/corpus.sinhala.wildcard.search
cd corpus.sinhala.wildcard.search
echo "Building corpus wildcard search"
mvn clean install -DskipTests
echo "Adding Oracle Dependencies"
cd ..
cp ../lib/ojdbc7.jar .
mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=ojdbc -DartifactId=ojdbc -Dversion=7 -Dpackaging=jar
