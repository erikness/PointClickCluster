if not exist bin mkdir bin
javac -cp .;lib/guava-18.0.jar;lib/miningcommon.jar -d bin src/edu/nmsu/erikness/pointclickcluster/*.java
if not exist "bin\edu\nmsu\erikness\datasets" mkdir "bin\edu\nmsu\erikness\datasets"
xcopy src\edu\nmsu\erikness\datasets bin\edu\nmsu\erikness\datasets /Y