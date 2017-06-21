package com.oyo.calculator;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;

public class CalculatorHTTPClient {

  private static int port = 8080;
  private static String host = "localhost";
  private static String uri = "calculator";

  public static void main(String[] args) {

    try {

      // create HTTP client
      THttpClient httpClient = new THttpClient("http://" + host + ":" + port + "/" + uri);
      httpClient.setConnectTimeout(3000);
      TProtocol protocol = new TJSONProtocol(httpClient);

      CalculatorService.Client client = new CalculatorService.Client(protocol);

      // simple operation using enum
      int num1 = 300;
      int num2 = 100;
      System.out.println("Adding " + (num1) + "," + (num2));
      System.out.println(client.calculate(num1, num2, Operation.ADD));

      // using a collection
      List<CalcPayload> ops = new ArrayList<CalcPayload>(3);
      CalcPayload addOp = new CalcPayload(20, 10, Operation.ADD);
      CalcPayload multiplyOp = new CalcPayload(20, 10, Operation.MULTIPLY);
      CalcPayload divideOp = new CalcPayload(20, 10, Operation.DIVIDE);
      ops.add(addOp);
      ops.add(multiplyOp);
      ops.add(divideOp);
      List<Integer> results = client.calculateBulk(ops);
      int resultSize = results.size();
      System.out.println("Doing bulk operations");
      for (int i = 0; i < resultSize; i++) {
        System.out.println(results.get(i));
      }

      // using optional member
      Point src = new Point(0, 0);
      Point dest = new Point(3, 4);
      System.out.println("Getting distance 2D");
      System.out.println(client.getDistance(src, dest));
      // set optional z coordinate as well
      src.setZ(0);
      dest.setX(3);
      dest.setY(4);
      dest.setZ(12);
      System.out.println("Getting distance 3D");
      System.out.println(client.getDistance(src, dest));


      // exception
      System.out.println(client.calculate(3, 0, Operation.DIVIDE));

      // close transport
      httpClient.close();
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }
}
