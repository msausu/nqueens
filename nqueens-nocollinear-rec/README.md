# nqueens-nocollinear-rec

The single file is a `direct` approach, the project is easier to understand and has some tests.

boardSize has to be small (varies on hw)

## single file use

```
java NQueensNoCollinear.java boardSize
```

## java artifact (after build)

```
java -jar build/libs/nqueens-nocollinear-rec.jar boardSize P?
boardSize: integer between 2 and 25
P?: optional board output, if absent numeric otherwise graphic
```

## output

> Numeric size 4 (each line is a solution array encoded by position starting at 0):

`2 0 3 1 &rarr; represents the solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))`

> Graphic size 12

```
____________
_ _ _ _ _ _♕
 _ _ _ _ ♕ _
_♕_ _ _ _ _ 
 _ ♕ _ _ _ _
_ _ _ _ ♕ _ 
 _ _ _ _ _♕_
_ ♕ _ _ _ _ 
♕_ _ _ _ _ _
_ _ _ ♕ _ _ 
 _ _♕_ _ _ _
_ _ _ _♕_ _ 
 _ _ ♕ _ _ _
____________
```

