package com.server.shopclt.payload.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.server.shopclt.enums.StatusOrder;

import lombok.Data;

@Data
public class OrderRequest {
	private BigDecimal totalAmount;
	private String address;
	private String phone;	
	private StatusOrder status;
	private LocalDate eddate;
	private String cancelReason;

	private List<OrderDetailRequest> orderDetail;
}


