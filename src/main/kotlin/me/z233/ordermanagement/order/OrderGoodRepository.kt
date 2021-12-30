package me.z233.ordermanagement.order

import org.apache.ibatis.annotations.*

@Mapper
interface OrderGoodRepository {
  @Select("SELECT * FROM order_good WHERE id = #{id}")
  fun findByOrderId(orderId: Int): List<OrderGood>

  @Insert(
    "INSERT INTO order_good (orderId, goodId, quantity) " +
        "VALUES (#{orderId}, #{goodId}, #{quantity})"
  )
  fun insert(orderGood: OrderGood)

  @Delete("DELETE FROM order_good WHERE orderId = #{orderId}, goodId = #{goodId}")
  fun delete(orderId: Int, goodId: Int)

  @Update("UPDATE order_good SET quantity = #{quantity} " +
            "WHERE orderId = #{orderId}, goodId = #{goodId}")
  fun update(orderGood: OrderGood)
}