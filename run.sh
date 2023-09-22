#!/bin/bash

# Provide execution privileges

if [ ! -x "$0" ]; then
  chmod +x "$0"
  exec "$0" "$@"
fi

sudo dnf install java-latest-openjdk java-devel -y

# Define directories
jar_file="TrafficSimulator.jar"

if [ -f "$jar_file" ]; then
    java -jar "$jar_file"
else
    echo "El archivo $jar_file no existe. Ejecutando compile_bundle.sh..."
    sh ./compilebundle.sh
fi
