package me.z233.ordermanagement.good

import org.apache.ibatis.annotations.*

@Mapper
interface GoodRepository {
  @Insert("INSERT INTO good (name, price, description) VALUES (#{name}, #{price}, #{description})")
  fun insert(good: Good): Int

  @Delete("DELETE FROM good WHERE id = #{id}")
  fun deleteById(id: Int): Int

  @Update("UPDATE good SET name = #{name}, price = #{price}, description = #{description} WHERE id = #{id}")
  fun update(good: Good): Int

  @Select("SELECT * FROM good")
  fun findAll(): List<Good>

  @Select("SELECT * FROM good WHERE id = #{id}")
  fun findById(id: Int): Good
}