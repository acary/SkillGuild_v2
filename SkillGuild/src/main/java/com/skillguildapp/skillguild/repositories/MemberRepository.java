package com.skillguildapp.skillguild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillguildapp.skillguild.entities.Member;
import com.skillguildapp.skillguild.entities.MemberId;

public interface MemberRepository extends JpaRepository<Member, MemberId>{
	List<Member> findByGuild_id(int gid);
}
