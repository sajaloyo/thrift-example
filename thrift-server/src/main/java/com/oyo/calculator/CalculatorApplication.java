package com.oyo.calculator;

import javax.servlet.Servlet;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.oyo.calculator.handler.CalculatorServiceHandler;

@SpringBootApplication
public class CalculatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(CalculatorApplication.class, args);
  }

  @Bean
  public ServletRegistrationBean servletRegistrationBean() {

    TProcessor processor =
        new CalculatorService.Processor<CalculatorServiceHandler>(new CalculatorServiceHandler());
    TProtocolFactory protoFactory = new TJSONProtocol.Factory();
    Servlet calculatorServlet = new TServlet(processor, protoFactory);
    return new ServletRegistrationBean(calculatorServlet, "/calculator/*");
  }
}
