package com.aluracursos.literalura.service;

public interface DataConvertable {
  <T> T getData(String json, Class<T> clazz);
}
