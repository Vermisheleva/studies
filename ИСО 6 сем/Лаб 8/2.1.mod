param n, integer, >0;
param m, integer, >0;
param a {1..n,1..m};
var y {1..n}, >=0;
#var x {1..m}, >=0;
minimize sum_y: sum{i in 1..n} y[i];
#maximize sum_x: sum{j in 1..m} x[j];
#subject to x_lim {i in 1..n}: sum{j in 1..m} a[i,j]*x[j] <= 1;
subject to y_lim {j in 1..m}: sum{i in 1..n} a[i,j]*y[i] >= 1;