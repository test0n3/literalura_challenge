package com.aluracursos.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
                @JsonAlias("id") Long oldId,
                @JsonAlias("title") String title,
                @JsonAlias("authors") List<AuthorData> authors,
                @JsonAlias("languages") List<String> languages,
                @JsonAlias("download_count") Double downloadCount) {
}
