package com.server.shopclt.payload.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.server.shopclt.entity.Order;
import com.server.shopclt.entity.OrderDetail;
import com.server.shopclt.entity.Product;
import com.server.shopclt.entity.User;
import com.server.shopclt.enums.StatusOrder;

import lombok.Data;

@Data
public class OrderResponse {

	private Long orderId;
	private StatusOrder status;
	private BigDecimal totalAmount;
	private String address;	
	
	private LocalDate date;
	
	private LocalDate eddate;
	
	private String cancelReason;
	
	private List<OrderDetailResponse> orderDetail;
	private UserOrderResponse user;
	
	public OrderResponse(Order order) {
		super();
		this.orderId = order.getOrderId();
		this.totalAmount = order.getTotalAmount();
		this.address = order.getShippingAddress();
		this.status = order.getStatus();
		this.date = order.getCreateAt();
		this.eddate = order.getExpectedDeliveryDate();
		this.cancelReason = order.getCancellationReason();
		if(order.getOrderDetails()!= null) {
			List<OrderDetail> details = order.getOrderDetails();
			List<OrderDetailResponse> lst  = new ArrayList<>();
			for(OrderDetail i:details) {
				lst.add(new OrderDetailResponse(i));
			}
			this.orderDetail = lst;			
		}
		this.user = new UserOrderResponse(order.getUser());
	}		
}

@Data
class OrderDetailResponse{
	
	private int quantity;
	private ProductOrderResponse product;
	
	
	public OrderDetailResponse(OrderDetail orderDetail) {
		super();
		this.product = new ProductOrderResponse(orderDetail.getProduct());
		this.quantity = orderDetail.getQuantity();
	}	
}

@Data
class UserOrderResponse{
	private Long id;
	private String fullname;
	private String address;
	private String phone;
    
	
	public UserOrderResponse(User user) {
		super();
		this.id = user.getUserId();
		this.fullname = user.getFullname();
		this.address = user.getAddress();
		this.phone = user.getPhoneNumber();
	}
}

@Data
class ProductOrderResponse{
	private Long productId;
	private String name;
	private String image;
	private BigDecimal price;
    private int quantity;
	public ProductOrderResponse(Product product) {
		super();
		this.productId = product.getProductId();
		this.name = product.getName();
		this.image = product.getImage();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
	}
}
