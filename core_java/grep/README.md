# Introduction

In the project an app named Grep was developed which will search for a text pattern *recursively* in a given directory, and output matched lines to a file. Lamdda API and Stream API were also introduced in this project to help us learn more efficient coding and advanced functional programming. 

# Usage

USAGE: regex rootPath outFile
- regex: a special text string for describing a search pattern
- rootPath: root directory path
- outFile: output file name

Similar to
egrep -r {regex} {rootPath} > {outFile}

# Pseudocode

public void process() {

  matchedLines = []
  for file in listFilesRecursively(rootDir)
    for line in readLines(file)
        if containsPattern(line)
          matchedLines.add(line)

  writeToFile(matchedLines)
}

# Performance Issue

A memory issue may occur for the reason that a file might exceed the maximum heap size so we need to fine-tune JVM heap size if OutOfMemoryError occurs. 

# Improvement
1. A better logging and debugging process can be implemented 
2. More accurate exceptions can be chosen instead of using IOException frequently
3. A few more complicated Regex pattern can be tested since unknown errors may occur. 
