namespace java com.oyo.calculator


enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

exception DivisionByZeroException {
}

struct Point {
	1: required i32 x;
	2: required i32 y;
	3: optional i32 z; 
}

struct CalcPayload {
	1: required i32 num1;
	2: required i32 num2;
	3: required Operation op;
}

service CalculatorService {

   	i32 calculate(1:i32 num1, 2:i32 num2, 3:Operation op) throws (1:DivisionByZeroException divisionByZero);
   
   	list<i32> calculateBulk(1: list<CalcPayload> ops) throws (1:DivisionByZeroException divisionByZero);
   
   	double getDistance(1:Point src, 2:Point dest);
      
}