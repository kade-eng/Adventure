# Adventure
A GUI constructed in Java to play the text game 'Adventure'
GameView is the main program, rest of the programs are object files used for the main program. 

## Author Information

* Name:Kade Burfoot-Clark
* Email:kburfoot@uoguelph.ca
* Student ID:1089070

## How to operate your program
use commands and follow instructions

### Running from the command line (without maven)
cmd line version
WHILE LINE 92 of pom.xml is "<mainClass>adventure.Game</mainClass>"
    mvn assembly:assembly
    java -jar ./target/2430_A2-1.0-jar-with-dependencies.jar -a ADVENDTUREJSON.json
    OR
    java -jar ./target/2430_A2-1.0-jar-with-dependencies.jar -l SAVEDGAMENAME

GUI
WHILE LINE 92 of pom.xml is "<mainClass>adventure.GameView</mainClass>"  //CURENTLY SET TO DO THIS
    mvn assembly:assembly
    java -jar ./target/2430_A2-1.0-jar-with-dependencies.jar 


### Instructions for using the program
usable commands:
commands - lists commands
go N/S/E/W/up/down - moves room
take ITEMN - adds item to inventory
look - shows room info
look ITEMN - shows item info
quit - quits game, gives save option in cmd line version
inventory - lists all items in inventory
eat - eats item, removes from inventory
toss - toesses item, removes from inventory, places in current room
wear - wear item, adds "equipped" tag to item
read - gain knowledge from reading

GUI USE

LOADING
- type name of file into text field located underneath "enter file below"
- click "Load Save" if file is previous save file
- click "Load New" if file is a new json file
SAVING
- type name of file to save into, into text field located underneath "enter file below"
- click "Save Game"
ADDING OR CHANGING NAME
- type name into text field located underneath "Edit character name below:"
- hit enter to submit
ENTERING COMMANDS
- enter commands in text field located to thr right of "Command line"
- hit enter to submit
QUITTING
hit the x or type quit in cmd bar



## Notes
No uploaded JSON file means attempting to load and run the game will not work, but source code is still useful.

