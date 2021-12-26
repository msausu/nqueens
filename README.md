# nqueens

> NQueens variants 

## [nqueens-nocollinear-rec](nqueens-nocollinear-rec)

> NQueensNoCollinear recursive, simple, approach 

Based on:

- [wikipedia's Heap's algorithm page](https://en.wikipedia.org/wiki/Heap%27s_algorithm)
- Robert Sedgewick [algs4](https://algs4.cs.princeton.edu/home/)

Segewick hypothesis: NQueens(n) ~ n! / 2.5<sup>n</sup>

Solve NQueens, pruning Queens in same diagonal, for each solution filter collinear Queens. 

## [nqueens-nocollinear-rnd](nqueens-nocollinear-rnd)

> NQueensNoCollinear random  approach 

Based on:

- [wikipedia's Las Vegas algorithm page](https://en.wikipedia.org/wiki/Las_Vegas_algorithm)
- Robert Sedgewick [algs4](https://algs4.cs.princeton.edu/home/)

Generate random boards and verify NQueens constraints + no collinear Queens. 
