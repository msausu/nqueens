# nqueens

> NQueens variants 

## [nqueens-nocollinear-rec](nqueens-nocollinear-rec)

> NQueensNoCollinear recursive, simple, approach 

Based on:

- wikipedia's Heap's Algorithm page
- Robert Sedgewick [algs4](https://algs4.cs.princeton.edu/home/)

Segewick hypothesis: NQueens(n) ~ n! / 2.5<sup>n</sup>

Solve NQueens, pruning Queens in same diagonal, for each solution filter colinear Queens. 
