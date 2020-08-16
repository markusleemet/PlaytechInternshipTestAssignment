# Playtech internship test assignment


This is command line java program that can solve puzzle15's with dimensions 4*4.
Folder with puzzles must be provided as a command line argument. Puzzles must have .p15 extension, 
otherwise they are ignored. 

#### .p15 file correct syntax

1. File can only contain numbers that are greater or equal to 0 and lesser or equal to 15.
1. Numbers are separeted with at least one whitespace.
1. File has four rows.
1. Each row has four numbers.


#### Output of the program

For each file with .p15 extension program prints out file name and one of the following:

1. Puzzle has correct syntax and is solvable: -_turns it takes to solve this puzzle_
1. The puzzle is unsolvable: -1
1. The file has incorrect syntax: -2
1. Some other unexpected error: -3

#### Used materials

The function that solves puzzle is based on following algorithm: https://www.instructables.com/id/How-To-Solve-The-15-Puzzle/

To check whether puzzle is solvable or not following formula was used: https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html