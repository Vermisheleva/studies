param n, integer, >0;
param m, integer, >0;
param f {1..n}, >0;
param b {1..n+1}, >0;
param c {1..n,1..m};
param product_weight {1..m}, >0;
var x {1..n+1,1..m}, >=0;
var y {1..n}, binary;
var z, >= 0;
minimize totalcost: z + sum{i in 1..n, j in 1..m} (c[i,j]*x[i,j]*product_weight[j]) + sum{i in 1..n} (f[i]*y[i]) + sum{j in 1..m} (c[1,j]*x[n+1,j]);
subject to additional_warehouse_limit: sum{j in 1..m} x[n+1,j]*product_weight[j] <= b[n+1];
subject to additional_warehouse_used_capasity: sum{j in 1..m} x[n+1,j]*product_weight[j] == z;
subject to completed_order {j in 1..m}: sum{i in 1..n+1} x[i,j] * product_weight[j] == product_weight[j];
subject to used_capasity {i in 1..n}: sum{j in 1..m} x[i,j] * product_weight[j] <= b[i]*y[i]
