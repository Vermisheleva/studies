var 24..2400: alex;
var 24..2400: mum;
var 24..2400: dad;
float: t = 70 * 24 / 3; 

constraint dad == alex * 6;
constraint alex + mum + dad == 70 * 24;
constraint dad + t == 2 * (alex + t);

output ["alex=", show(alex), "\t mum=", show(mum), "\t dad=", show(dad)]