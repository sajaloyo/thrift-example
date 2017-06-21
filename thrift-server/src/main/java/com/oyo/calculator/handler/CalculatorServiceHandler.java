package com.oyo.calculator.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.oyo.calculator.CalcPayload;
import com.oyo.calculator.CalculatorService;
import com.oyo.calculator.DivisionByZeroException;
import com.oyo.calculator.Operation;
import com.oyo.calculator.Point;

@Component
public class CalculatorServiceHandler implements CalculatorService.Iface {

  @Override
  public int calculate(int num1, int num2, Operation op) throws TException {
    switch (op) {
      case ADD:
        return num1 + num2;
      case SUBTRACT:
        return num1 - num2;
      case MULTIPLY:
        return num1 * num2;
      case DIVIDE:
        if (num2 == 0) {
          throw new DivisionByZeroException();
        } else {
          return num1 / num2;
        }
      default:
        throw new TException("Unknown operation " + op);
    }
  }

  @Override
  public List<Integer> calculateBulk(java.util.List<CalcPayload> ops)
      throws DivisionByZeroException, org.apache.thrift.TException {

    int numOps = ops.size();
    List<Integer> results = new ArrayList<Integer>(numOps);
    for (int i = 0; i < numOps; i++) {
      results.add(calculate(ops.get(i).getNum1(), ops.get(i).getNum2(), ops.get(i).getOp()));
    }
    return results;
  }

  @Override
  public double getDistance(Point src, Point dest) throws org.apache.thrift.TException {

    // check is z is set in both points
    if (src.isSetZ() && src.isSetZ()) {
      return Math.sqrt(Math.pow(src.getX() - dest.getX(), 2) + Math.pow(src.getY() - dest.getY(), 2)
          + Math.pow(src.getZ() - dest.getZ(), 2));
    } else {
      return Math
          .sqrt(Math.pow(src.getX() - dest.getX(), 2) + Math.pow(src.getY() - dest.getY(), 2));
    }
  }

}
