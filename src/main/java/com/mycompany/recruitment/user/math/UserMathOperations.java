package com.mycompany.recruitment.user.math;

import com.mycompany.recruitment.user.exception.BusinessException;

public class UserMathOperations {

  public static double calculations(Long followers, Long publicRepos) {
    if (followers == 0L) {
      throw new BusinessException("Calculations cannot be performed because followers number is zero");
    }

    return 6.0 / followers * (publicRepos + 2);
  }
}
