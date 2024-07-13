package com.java.spring.batch.statement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccount {

  @Id
  @Column(name = "customer_id")
  private int id;

  @Column(name = "account_name")
  private String accountName;

  @Column(name = "address")
  private String address;

  @Column(name = "date")
  private Date date;

  @Column(name = "account_number")
  private String accountNumber;

  @Column(name = "account_description")
  private String accountDescription;

  @Column(name = "branch")
  private String branch;

  @Column(name = "cif_no")
  private String cifNo;

  @Column(name = "ifs_code")
  private String ifsCode;

  @Column(name = "micr_code")
  private String micrCode;

  @Column(name = "balance")
  private double balance;

  @Column(name = "email")
  private String email;

  @Column(name = "gender")
  private String gender;

  @Column(name = "contact_no")
  private String contactNo;

  @Column(name = "country")
  private String country;

  @Column(name = "dob")
  private String dob;
}
