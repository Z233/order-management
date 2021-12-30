package me.z233.ordermanagement.user

import org.apache.ibatis.annotations.*

@Mapper
interface UserRepository {
  @Select("SELECT * FROM user")
  fun findAll(): List<User>;

  @Select("SELECT * FROM user WHERE phone = #{phone}")
  fun findByPhone(phone: Number): User?;

  @Select("SELECT * FROM user WHERE name LIKE CONCAT('%', #{key}, '%')")
  fun searchByName(key: String): List<User>?;

  @Insert("INSERT INTO user(name, sex, phone) VALUES(#{name}, #{sex}, #{phone})")
  fun insert(user: User): Int;

  @Delete("DELETE FROM user WHERE id = #{id}")
  fun deleteById(id: Int): Int;

  @Update("UPDATE user SET name = #{name}, sex = #{sex}, phone = #{phone} " +
          "WHERE id = #{id}")
  fun update(user: User): Int;

}