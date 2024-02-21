package com.khit.library.service;

import org.springframework.stereotype.Service;

import com.khit.library.entity.FreeBoard;
import com.khit.library.entity.FreeReply;
import com.khit.library.repository.FreeBoardRepository;
import com.khit.library.repository.FreeReplyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeReplyService {
	private final FreeReplyRepository freeReplyRepository;
	private final FreeBoardRepository freeBoardRepository;
	
	public void insertReply(Long freeBoardFbid, FreeReply freeReply) {
		FreeBoard freeBoard = freeBoardRepository.findById(freeBoardFbid).get();
		freeReply.setFreeboard(freeBoard);
		freeReplyRepository.save(freeReply);
	}

	public void deleteById(Long freeReplyFrid) {
		freeReplyRepository.deleteById(freeReplyFrid);
	}

	public FreeReply findById(Long freeReplyFrid) {
		return freeReplyRepository.findById(freeReplyFrid).orElse(null);
	}

	public void save(FreeReply updateFreeReply) {
		freeReplyRepository.save(updateFreeReply);
	}
}