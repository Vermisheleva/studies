int: n = 5;
array[0..n,0..n] of var 1..151: answer;

constraint answer[0,0] = 151;
constraint answer[2,0] = 40;
constraint answer[4,1] = 11;
constraint answer[4,3] = 4;
constraint 5*answer[4,0] + 6*answer[4,4] == answer[0,2] - answer[2,1] - 4*answer[4,4] - 2*answer[4,1];
constraint 2*answer[4,0] + answer[4,4] == answer[2,1] - 2*answer[4,1];
constraint answer[4,2] == answer[4,0] + answer[4,4];
constraint forall(i in n-2..0, j in 0..i)(
                      answer[i,j] == answer[i-1,j] + answer[i-1, j+1]);
solve satisfy;

output ["X=", show(answer[4,0]), "\t Z=", show(answer[4,4])]

