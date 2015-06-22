mkdir -p bin
javac -classpath .:lib/guava-18.0.jar:lib/miningcommon.jar -d bin src/edu/nmsu/erikness/pointclickcluster/*.java
mkdir -p bin/edu/nmsu/erikness/datasets
cp src/edu/nmsu/erikness/datasets bin/edu/nmsu/erikness/datasets