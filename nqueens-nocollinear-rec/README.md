# nqueens-nocollinear-rec

The single file is a `direct` approach, the project is easier to understand and has some tests.

## single file use

```
java NQueensNoCollinear.java boardSize
```

boardSize has to be small (varies on hw)

## java artifact (after build)

```
java -jar build/libs/nqueens-nocollinear-rec.jar boardSize P?
boardSize: integer between 2 and 25
P?: optional board output, if absent numeric otherwise graphic
```

## output

Each line is a solution array encoded by position starting at 0

> Numeric size 4:

`2 0 3 1 -> solution tuple ((1, 3), (2, 1), (3, 4), (4, 2))`

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

