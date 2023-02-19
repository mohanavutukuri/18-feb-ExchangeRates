package com.Exchange.Repository;

import com.Exchange.Entity.Audit_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Audit_InfoRepo extends JpaRepository<Audit_Info,Long> {
}
