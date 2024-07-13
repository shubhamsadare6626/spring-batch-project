package com.java.spring.batch.statement.mapper;

import com.java.spring.batch.statement.entity.CustomerAccount;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerAccountMapper implements RowMapper<CustomerAccount> {

  @Override
  public CustomerAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
    CustomerAccount customerAccount = new CustomerAccount();
    customerAccount.setId(rs.getInt("id"));
    customerAccount.setAccountName(rs.getString("account_name"));
    customerAccount.setAddress(rs.getString("address"));
    customerAccount.setDate(rs.getDate("date"));
    customerAccount.setAccountNumber(rs.getString("account_number"));
    customerAccount.setAccountDescription(rs.getString("account_description"));
    customerAccount.setBranch(rs.getString("branch"));
    customerAccount.setCifNo(rs.getString("cif_no"));
    customerAccount.setIfsCode(rs.getString("ifs_code"));
    customerAccount.setMicrCode(rs.getString("micr_code"));
    customerAccount.setBalance(rs.getDouble("balance"));
    customerAccount.setEmail(rs.getString("email"));
    customerAccount.setGender(rs.getString("gender"));
    customerAccount.setContactNo(rs.getString("contact_no"));
    customerAccount.setCountry(rs.getString("country"));
    customerAccount.setDob(rs.getString("dob"));
    return customerAccount;
  }
}
