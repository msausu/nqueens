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

Each line (stdout) is a solution array encoded by position starting at 0

Example:

`2 0 3 1 -> solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))`

Extra info (stderr) is provided concerning the execution:

```
Limit _______ reached
test count: ______ stores __ skips ___
```

- limit reached if maxTrials was reached
- test count: number of tests
- stores: number of results obtained
- skips: number of repeated boards (skipped)
