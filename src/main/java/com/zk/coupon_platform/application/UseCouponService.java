package com.zk.coupon_platform.application;

import com.zk.coupon_platform.application.dto.CouponId;
import com.zk.coupon_platform.application.dto.UserId;
import com.zk.coupon_platform.application.port.UseCoupon;
import com.zk.coupon_platform.domain.Coupon;
import org.springframework.stereotype.Service;

@Service
public class UseCouponService {

  private final UseCoupon repo;

  public UseCouponService(UseCoupon repo) {
    this.repo = repo;
  }

  public Object use(UserId userId, CouponId couponId) {
    Coupon c = repo.find(couponId);
    String id = userId.getId();
    c.use(id);
    repo.save(c);

    return null;
  }
}
