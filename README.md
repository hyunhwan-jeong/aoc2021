Groovy Solutions of Advent of Code 2021
=======================================

## What is it?

I am currently learning [Apache Groovy language](https://groovy-lang.org), which is a python-type of java language. (Please correct me if I am wrong, and I am very new to it).
I believe solving problems of [Advent of Code](https://adventofcode.com/2021/) using Groovy will be a great chance to learn about the language. 

Here, I made some notes for the solutions that are something interesting or educational.

## Notes

### Day 14

I easily solved Part 1 using a simulation - generating the exact polymer strings. 
However, the simulation doesn't work for Part 2, and I spent a bit of time solving it. 

1. I initially thought keeping pairs of the string would be the key idea. However, I was facing a problem that it has some duplication of letters. 
For example, if a string is `NC`, and the rule generate `NCN`, I will have `NC` and `CN` as pairs, but I could count there are two `N`s and two `C`s from the kept pairs,
while the generated string has two `N`s and a `C`. 

2. After matching the generated pairs and the count of each character in the generated string, I found that we only need to count the second letter of each pair.

3. Another struggle point was that how to deal with the first character of the input string. My solution was making a pseudo rule with an empty character and the first character, like `" X" -> "X"`. 

### Day 19

This was the most annoying problem, but I finally solved it a couple of days later. 

#### Main Ideas

* Any 3D problems seems horrible, but for most of the problem, you define independent problems for each axis to solve the entire problem. I initially almost thought about a cubic-time complexity algorithm, but later I could realize that the x, y, and z-axes are independent. 
* If we don't need to worry about the rotations, we can think about each axis independently. We can convert this problem as a 1D problem - given A and B integer sets, find the best offset to maximize the number of intersections of two sets.
 * The easiest way to solve (but slow) is to iterate offset within a boundary, and test. If the boundary range is too big, the problem wouldn't be feasible. 
 * Calculating possible pairwise differences between two sets and only testing the calculated distances is a faster way since the sets only have a few values (25 beacons). 
  * But the approach can only solve the problem of each axis partially, and we have to combine them to determine whether two scanners have overlap. So, I implemented a two-step approach: 1)Take multiple offsets for each axis that are over a threshold, 2) Make (offset_x, offset_y, offset_z) by combining possible combinations, and test overlap of two scanners.
  
* Rotation is somewhat the first struggling point, and I am not good at that, so I get some hints from [a Reddit thread](https://www.reddit.com/r/adventofcode/comments/rk8qfd/2021_day_19math_some_helpful_matrices_for_solving/). To find all the possible rotations, you need to take rigid transformations. Here are the steps:
 *  Take all the permutation matrices of 3 by 3.
 * For each permutation matrix, you can replace any 1 to -1. I bruteforced for this. 
 * To confirm the changed matrix is valid, you need to calculate the determinant of the matrix. [Rule of Sarrus](https://en.wikipedia.org/wiki/Rule_of_Sarrus) can be applied to calculate them. 

* After I implemented those two modules, I ran BFS to search a solution.

#### Mistakes (and I still don't find why)

* To count unique beacons, I initially tried union-find algorithm, but unfortuntately it didn't work
* I only hold candidates of offsets from pairwise distances if they appears equal of greater than 12 times, but it didn't cover the entire input. I could solve the problem after I lowered the threshold (12 to 6). Not sure what I missed...

## TODO

* Fix Day 20 code (although I solved the Day 20)

