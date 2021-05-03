# nqueens-nocollinear-rnd

Since this is a (Las Vegas) randomized version, the number of solutions may vary, so it's indicated to perform multiple runs.

Program parameters:

- boardSize 
- minSolutions (default 1)
- maxTrials (default 1000000)

## java artifact (after build)

```
java -jar build/libs/nqueens-nocollinear-rnd-all.jar boardSize minSolutions? maxTrials?
```

## output

Each line is a solution array encoded by position starting at 0

Example:

`2 0 3 1 -> solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))`


