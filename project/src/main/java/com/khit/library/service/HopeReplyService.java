package com.khit.library.service;

import org.springframework.stereotype.Service;

import com.khit.library.entity.HopeBoard;
import com.khit.library.entity.HopeReply;
import com.khit.library.repository.HopeBoardRepository;
import com.khit.library.repository.HopeReplyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HopeReplyService {
	private final HopeReplyRepository hopeReplyRepository;
	private final HopeBoardRepository hopeBoardRepository;
	
	public void insertReply(Long hopeBoardHbid, HopeReply hopeReply) {
		HopeBoard hopeBoard = hopeBoardRepository.findById(hopeBoardHbid).get();
		hopeReply.setHopeboard(hopeBoard);
		hopeReplyRepository.save(hopeReply);
	}

	public void deleteById(Long hopeReplyHrid) {
		hopeReplyRepository.deleteById(hopeReplyHrid);
	}

	public HopeReply findById(Long hopeReplyHrid) {
		return hopeReplyRepository.findById(hopeReplyHrid).orElse(null);
	}

	public void save(HopeReply updateHopeReply) {
		hopeReplyRepository.save(updateHopeReply);
	}
}