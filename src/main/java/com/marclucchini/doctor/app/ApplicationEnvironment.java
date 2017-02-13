package com.marclucchini.doctor.app;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationEnvironment {

  public Map<String, String> getEnv() {
    return System.getenv();
  }

  public String getEnv(String variable) {
    return System.getenv(variable);
  }
}
