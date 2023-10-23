package com.mycompany.recruitment.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "USER_STATISTIC")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserStatistic {

  @Id
  @Column(name = "LOGIN")
  String login;

  @Column(name = "REQUEST_COUNT")
  Integer requestCount;

  @Version
  @Column(name = "VERSION")
  long version;

  public void incrementRequestCount() {
    requestCount++;
  }
}
