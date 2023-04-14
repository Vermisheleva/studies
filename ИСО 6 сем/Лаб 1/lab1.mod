param n, integer, >0;
param cost {1..n}, >0;
param a {1..n,1..n+1};
var x {1..n}, >=0;
minimize totalcost: sum{i in 1..n} cost[i]*x[i];
subject to nutrients {i in 1..n}: sum{j in 1..n} a[i,j]*x[j] >= a[i,n+1];
