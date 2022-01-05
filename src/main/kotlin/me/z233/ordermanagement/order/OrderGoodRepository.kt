package me.z233.ordermanagement.order

import org.apache.ibatis.annotations.*

@Mapper
interface OrderGoodRepository {
  @Select("SELECT * FROM order_good WHERE orderId = #{orderId}")
  fun findByOrderId(orderId: Int): List<OrderGood>?

  @Select("SELECT * FROM order_good WHERE goodId = #{goodId}")
  fun findByGoodId(goodId: Int): List<OrderGood>?

  @Insert(
    "INSERT INTO order_good (orderId, goodId, quantity) " +
        "VALUES (#{orderId}, #{goodId}, #{quantity})"
  )
  fun insert(orderGood: OrderGood)

  @Delete("DELETE FROM order_good WHERE orderId = #{orderId} AND goodId = #{goodId}")
  fun delete(orderId: Int, goodId: Int)

  @Update("UPDATE order_good SET quantity = #{quantity} " +
            "WHERE orderId = #{orderId} AND goodId = #{goodId}")
  fun update(orderGood: OrderGood)
}