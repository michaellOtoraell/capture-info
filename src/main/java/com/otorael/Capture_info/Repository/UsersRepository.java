package com.otorael.Capture_info.Repository;

import com.otorael.Capture_info.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);
}
