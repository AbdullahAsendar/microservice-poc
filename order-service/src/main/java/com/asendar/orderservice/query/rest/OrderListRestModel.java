package com.asendar.orderservice.query.rest;

import com.asendar.orderservice.core.entity.OrderEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author asendar
 */
public record OrderListRestModel(List<OrderRestModel> orders) {

}
