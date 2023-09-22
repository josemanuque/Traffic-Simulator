#!/bin/bash

# Provide execution privileges

if [ ! -x "$0" ]; then
  chmod +x "$0"
  exec "$0" "$@"
fi

# Install Java and JDK

echo "Installing Java and JDK"

sudo dnf install java-latest-openjdk java-devel -y

echo "Successfully installed Java and JDK"

# Define directories

src_dir="src"
bin_dir="bin"
classes_dir="bin/classes"
jar_file="TrafficSimulator.jar"
manifest_file="bin/manifest.mf"


# Delete bin if existing

if [ -d "$bin_dir" ]; then
    rm -rf "$bin_dir"
fi


# Compiles all the Java files in src folder and puts them in bin/classes
echo "Compiling project..."
find "$src_dir" -name "*.java" -exec javac -cp "$src_dir" -d "$classes_dir" {} \;
echo "Successfully compiled project"

# Creates manifest file if not created

if [ ! -f "$manifest_file" ]; then
    echo "Main-Class: trafficsimulator.TrafficSimulator" > "$manifest_file"
fi

# Deletes Jar file if already exists, else creates a new one

echo "Bundling files to $jar_file  ..."
if [ -f "$jar_file" ]; then
    echo "Jar file already exists, removing existing one"
    rm "$jar_file"
fi

jar -cfm "$jar_file" "$manifest_file" -C "$classes_dir" .

echo "Successfully bundled"
