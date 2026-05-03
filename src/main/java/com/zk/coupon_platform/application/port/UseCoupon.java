package com.zk.coupon_platform.application.port;

import com.zk.coupon_platform.application.dto.CouponId;
import com.zk.coupon_platform.domain.Coupon;

public interface UseCoupon {
  Coupon find(CouponId couponId);
  void save(Coupon c);
}
