package com.futurumtechnology.campaign_manager.repository;

import com.futurumtechnology.campaign_manager.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
