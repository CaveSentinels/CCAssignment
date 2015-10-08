# Chapter 06

## Solution 01

Take one pill from Bottle #1, two pills from Bottle #2, three pills from Bottle #3, and so on. Weigh this mix of pills. If all pills were one gram each, the scale would read 210 grams (1 + 2 + ... + 20 = 20 * 21 / 2 = 210). Any "overage" must come from the extra 0.1 gram pills.

This formula tells us the bottle number:

	Bottle # = (weight - 210g) / 0.1g

## Solution 02

Thought process:

1. Use *E* to denote an event and *P(E)* the probability of event E. Use *a^b* to denote power b of a, in other words, a multiplies itself b times.

2. Define three events:
	* e: Make one shot;
	* E1: Win game 1;
	* E2: Win game 2;

3. According to the question description:

	P(E1) = P(e) = p

4. Winning game 2 means make at least two shots. There are totally 4 cases:
	1. Make: first, second; Miss: third;
	2. Make: first, third; Miss: second;
	3. Make: second, third; Miss: first;
	4. Make: all three.
   So the probability is:

	P(E2) = P(e) * P(e) * (1 - P(e)) + P(e) * (1 - P(e)) * P(e) + (1 - P(e)) * P(e) * P(e) + P(e) * P(e) * P(e) = p * p * (1-p) + p * (1-p) * p + (1-p) * p * p + p * p * p = 3(p^2)(1-p) + p^3

5. Therefore:
	1. When P(E1) > P(E2), we should play game 1. In this case, we have
	
		p > 3(p^2)(1-p) + p^3
		
		Assuming 0 < p < 1, we can get p < 0.5.
	2. When P(E1) < P(E2), we should play game 2. In this case, we have p > 0.5 if we assume 0 < p < 1.
	3. If p == 0.5, or p == 0 or p == 1, it makes no different to play which game.


## Solution 03

The chessboard initially has 32 black and 32 white squares. By removing opposite corners (which must be the same color), we're left with 30 of one color and 32 of the other color. Let's say, for the sake of argument, that we have 30 black and 32 white squares.

Each domino we set on the board will always take up one white and one black square. Therefore, 31 dominos will take up 31 white squares and 31 black squares exactly. On this board, however, we must have 30 black squares and 32 white squares. Hence, it is impossible.

## Solution 04

Thought process:

1. In a N-vertex polygon, there are too many cases that at least two ants may colide. However, there are only 2 cases where the ants don't colide: All the ants go either the left direction or the right direction.

2. Use *E* to denote an event and *P(E)* the probability of event E. Use *a^b* to denote power b of a, in other words, a multiplies itself b times.

3. Define two events: 
	* E1: all ants go left; 
	* E2: all ants go right;

   It's easy to see that E1 and E2 are mutually exclusive events.

4. In a triangle (or we say it's a 3-vertex polygon), the probability that all the 3 ants go the left or right direction is: 

	** P(E1 OR E2) = P(E1) + P(E2) = (1/2) * (1/2) * (1/2) + (1/2) * (1/2) * (1/2) = 2 * ((1/2)^3) = 1/4 **
	
   So the probability that ants go collision is:
   
    ** 1 - P(E1 OR E2) = 1 - 1/4 = 3/4 **

5. In a N-vertex polygon, there are N ants, so the probability of all the N ants go either direction is:

	** P(E1 OR E2) = P(E1) + P(E2) = (1/2)^N + (1/2)^N = 2 * ((1/2)^N) **
	
   So the probability that ants go collision is:
   
    ** 1 - P(E1 OR E2) = 1 - 2 * ((1/2)^N) = 1 - (1/2)^(N-1) **

## Solution 05

Steps to get 4 quarts of water:

1. Make sure both the 5- and 3-quart jugs are empty.
2. Fill in the 5-quart jug.
3. Fill in the 3-quart jug with the water in the 5-quart jug.
4. Checkpoint: 
	1. 5-quart jug: 2 quarts of water;
	2. 3-quart jug: Empty;
5. Dump the water in 3-quart jug.
6. Fill in the 3-quart jug with the remaining 2 quarts of water in the 5-quart jug.
7. Checkpoint: 
 	1. 5-quart jug: Empty;
	2. 3-quart jug: 2 quarts of water;
8. Fill in the 5-quart jug.
9. Fill in the 3-quart jug with the water in the 5-quart jug.
10. Checkpoint: 
 	1. 5-quart jug: **4 quarts of water**;
	2. 3-quart jug: Full;

## Solution 06

Assume that there are N people on the island and C of them have blue eyes, where C >= 1.

* If C == 1, the blue-eyed person looks around and realizes that nobody else is blue-eyed so he/she must be the only person with the blue eye. He/she will leave on the first day.

* If C == 2, the two blue-eyed people can see the other one has blue eyes, but they are not sure if him-/herself is blue-eyed or not. What he/she knows is that if that person is the only blue-eyed one, he/she will leave on the first day. After day 1, they see the other one is still on the island, so they know there are 2 blue-eyed people and he/she is one of them. Both will leave on day 2.

* If C == 3, those 3 people will realize there are 2 or 3 blue-eyed people. They are only not sure whether him-/herself is blue-eyed or not. They know that, if there were only 2 blue-eyed people, the other two will leave the island on day 2. So if on day 3 the other two people are still on island, all of them will realize that there are 3 blue-eyed people and he/she is one of them. They will leave the island on day 3.

* In general, if C == M, these M people are initially not sure whether there are (M-1) or M blue-eyed people. What they know is that if there were only (M-1) blue-eyed people, all of them will leave the island on day (M-1). So if they see each other still on island on day M, they will realize that they are all blue-eyed people and will leave on day M.


## Solution 07


## Solution 08


## Solution 09

1. A door is toggled once for each factor of n, including itself and 1. For example, door 15 is toggled on round #1, #3, #5 and #15.

2. A door is left open if the number of factors (which we will call x) is an odd number, because initially the door is closed. An even number of toggles will close it again, so an odd number of toggles will leave it open.

3. The value x is odd if n is a perfect square. Here's why: pair n's factors by their complements. For example, if n is 36, the factors are (1, 36), (2, 18), (3, 12), (4, 9), (6, 6). Note that (6, 6) only contributes one factor, thus giving n an odd number of factors.

4. There are 10 perfect squares in [1, 100]: 1, 4, 9, 16, 25, 36, 49, 64, 81, 100. Therefore, there will be 10 lockers open at the end of this process.

## Solution 10

