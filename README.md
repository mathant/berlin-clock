**Requirements**

Implement the Berlin Clock as a function of the three parameters : hours (24-based), minutes, seconds and return a 
multi line string. 

The time is calculated by adding the lit rectangular lamps. 

1) _The top lamp_ is a pump which is blinking on/off every two seconds. 

2) In _the topmost line of red lamps_ every lamp represents 5 hours. 

3) In _the second line of red lamps_ every lamp represents 1 hour. So if in the first line 2 lamps are lit and in the second 
line 3 lamps its 5+5+3=13h or 1 p.m. 

4) In _the third line_ with tall lamps every lamp represents 5 minutes. There are 11 lamps, the 3rd, 6th, and 9th are red
indicating the first quarter, half, and the last quarter of the hour. 

5) In _the last line with yellow lamps_ every lamp represents 1 minute.

More details can be referred from: https://en.wikipedia.org/wiki/Mengenlehreuhr

**Representation for the colors and states**

A lamp can be either lit or un-lit. The state is indicated as below:

<pre> 
Lamp 
   |--- is un-lit --  lamp state is indicated as O 
   `--- is lit    --|
                    |--- is Red color    -- lamp state is indicated as R
                    `--- is Yellow color -- lamp state is indicated as Y
</pre>


**Instructions to run**

This is a standard Scala project with sbt. The main class is located in `com.example.berlinclock.Main`.
Please use the `sbt run` command, to start the application.

_Input_: Command line will ask for the input as below:

```Please enter input in format 'hours(24-based):minutes:seconds' (example: '23:48:01') :```

_Output_ will be printed as:

<pre>
  O
  RRRR
  RRRO
  YYRYYRYYROO
  YYYO </pre>

**Assumptions**

1) The top lamp is said to blink on/off every two seconds. It is assumed that the lamp will be on every alternate seconds.
ie. it will be _on_ for even number of seconds and _off_ for odd number of seconds.

2) The last minute of the day is considered as 23:59 and the next minute is 00:00. 
Please note:  24:00 is considered as invalid input.

**Test coverage**
This code have 100% test coverage.
Please note: the 'Main' class is reading and writing to console, so it is tested manually and the test coverage is
 added to the automatic test coverage suite. 