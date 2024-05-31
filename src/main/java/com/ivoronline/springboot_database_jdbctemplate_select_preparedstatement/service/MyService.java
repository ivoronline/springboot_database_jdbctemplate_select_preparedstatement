package com.ivoronline.springboot_database_jdbctemplate_select_preparedstatement.service;

import com.ivoronline.springboot_database_jdbctemplate_select_preparedstatement.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MyService {

  //PROPERTIES
  @Autowired private JdbcTemplate jdbcTemplate;

  //=========================================================================================================
  // SELECT RECORD
  //=========================================================================================================
  public PersonDTO selectRecord(Integer id) {

    //PREPARED STATEMENT
    PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM PERSON WHERE ID = ?");
                          preparedStatement.setInt(1, id);
        return preparedStatement;
      }
    };

    //EXECUTE QUERY
    return jdbcTemplate.query(
      preparedStatementCreator,
      resultSet -> {
        resultSet.next();                         //Or rror => no data available
        PersonDTO personDTO = new PersonDTO(      //@AllArgsConstructor
          resultSet.getInt   ("ID"  ),
          resultSet.getString("NAME"),
          guardAgainstNullNumbers("AGE", resultSet)
        );
        return    personDTO;
      }
    );

  }

  //=========================================================================================================
  // SELECT RECORDS
  //=========================================================================================================
  //Automatically cycles through resultSet and adds personDTO to a List
  public List<PersonDTO> selectRecords(Integer id) {

    //PREPARED STATEMENT
    PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM PERSON WHERE ID >= ?");
                          preparedStatement.setInt(1, id);
        return preparedStatement;
      }
    };

    //EXECUTE QUERY
    return jdbcTemplate.query(
      preparedStatementCreator,
      (resultSet, rowNum) -> {                    //NEXT RECORD
        PersonDTO personDTO = new PersonDTO(      //@AllArgsConstructor
          resultSet.getInt   ("ID"  ),
          resultSet.getString("NAME"),
          guardAgainstNullNumbers("AGE", resultSet)
        );
        return personDTO;                         //ADD DTO TO LIST
      });
  }

  //=========================================================================================================
  // GUARD AGAINST NULL NUMBERS
  //=========================================================================================================
  public Integer guardAgainstNullNumbers(String columnName, ResultSet resultSet) throws SQLException {
    Integer value = resultSet.getInt(columnName); //It will be 0 for null
    return resultSet.wasNull() ? null : value;
  }

}



