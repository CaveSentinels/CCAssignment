# Text Processing

Andrew ID: **yaobinw**

## Files

The folder has the following files:

* pg45.txt: The document that contains the test data.
* prog.js: The main program.
* report.html: The result report.
* run.sh: The bash script that runs the program.
* README.md: This file.

## Remarks

* When separating the words, I keep the words like "don't" and "won't" as one word.


## Prerequisites

In order to run the program correctly, your system must meet the following prerequisites:

* Node.js is installed. If not, please download the installation package [here](https://nodejs.org/en/download/).
* If you want to use the ```run.sh``` script, you must have terminal where you can use bash.


## Run the Program with run.sh

Follow the steps below to run the program:

1. Open a terminal and navigate to the root folder of the program.
2. Enter ```sh run.sh <file path> <report name>``` to run the program. Note that an ".html" extension name will be automatically appended to the "report name", so you don't have to add that manually. For example: ```sh run.sh ./pg45.txt report```.
3. The result is written in the specified report. Double click the HTML file to open it in a browser.


## Run the Program without Using run.sh

Follow the steps below 

1. Open a terminal and navigate to the root folder of the program.
2. Enter ```npm install line-reader``` to install the dependent package.
3. Enter ```node ./prog.js <file path> > <report name>``` to run the program.