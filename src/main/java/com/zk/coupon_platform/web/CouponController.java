package com.zk.coupon_platform.web;

import com.zk.coupon_platform.application.UseCouponService;
import com.zk.coupon_platform.application.dto.CouponId;
import com.zk.coupon_platform.application.dto.UserId;
import com.zk.coupon_platform.web.port.UseCouponRequest;
import com.zk.coupon_platform.web.port.UseCouponResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

  private final UseCouponService service;

  public  CouponController(UseCouponService service) {
    this.service = service;
  }

  @PostMapping("/coupons/use")
  public UseCouponResponse use(@RequestBody @Valid UseCouponRequest req) {

    String userId = req.getUserId();
    String couponId = req.getCouponId();
    var result = service.use( new UserId(userId), new CouponId(couponId));

    return new UseCouponResponse(result);
  }
}
