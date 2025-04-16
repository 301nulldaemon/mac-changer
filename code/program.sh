#!/bin/bash

# Prompt for sudo once at the beginning
if sudo -v; then
    echo "✅ Sudo credentials cached. Launching GUI..."

    # Change to the code directory
    cd /home/maniac/Documents/mac-changer/code || exit 1

    # Run the Java GUI
    sudo java gui.java
else
    echo "❌ Failed to get sudo access. Exiting."
    exit 1
fi
