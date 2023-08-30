% Use this editor as a MiniZinc scratch book
var 1..6: grandparents;
var 1..10: parents;
var 1..40: children;

int: gpcost = 3;
int: pcost = 2;
float: chcost = 0.5;

constraint grandparents + parents + children == 20;
constraint grandparents * gpcost + parents * pcost + children * chcost == 20;
solve satisfy;

output ["grandparents=", show(grandparents), "\t parents=", show(parents), "\t children=", show(children)]
