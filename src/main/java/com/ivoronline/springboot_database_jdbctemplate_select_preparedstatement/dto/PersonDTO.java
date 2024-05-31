package com.ivoronline.springboot_database_jdbctemplate_select_preparedstatement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
  private Integer id;
  private String  name;
  private Integer age;
}
