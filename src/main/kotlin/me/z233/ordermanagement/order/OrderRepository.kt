package me.z233.ordermanagement.order

import org.apache.ibatis.annotations.*

@Mapper
interface OrderRepository {

  @Insert("INSERT INTO order(userId, createdAt, remark) VALUES(#{userId}, #{createdAt}, #{remark})")
  fun insert(order: Order): Int

  @Delete("DELETE FROM order WHERE id = #{id}")
  fun deleteById(id: Int): Int

  @Update("UPDATE order SET userId = #{userId}, createdAt = #{createdAt}, remark = #{remark} WHERE id = #{id}")
  fun update(order: Order): Int

  @Select("SELECT * FROM order")
  fun findAll(): List<Order>

}