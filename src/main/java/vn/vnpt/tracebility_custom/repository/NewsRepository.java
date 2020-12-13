package vn.vnpt.tracebility_custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.vnpt.tracebility_custom.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
}
