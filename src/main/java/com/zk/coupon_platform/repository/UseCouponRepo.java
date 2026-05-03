package com.zk.coupon_platform.repository;

import com.zk.coupon_platform.application.dto.CouponId;
import com.zk.coupon_platform.application.port.UseCoupon;
import com.zk.coupon_platform.domain.Coupon;
import org.springframework.stereotype.Repository;

@Repository
public class UseCouponRepo implements UseCoupon {
  public Coupon find(CouponId couponId) {
    return null;
  }

  public void save(Coupon c) {
  }
}
