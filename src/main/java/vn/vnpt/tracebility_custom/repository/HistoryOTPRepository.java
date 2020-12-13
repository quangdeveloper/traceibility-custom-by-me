package vn.vnpt.tracebility_custom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.vnpt.tracebility_custom.entity.HistoryOTP;

public interface HistoryOTPRepository extends JpaRepository<HistoryOTP, Long> {
}
