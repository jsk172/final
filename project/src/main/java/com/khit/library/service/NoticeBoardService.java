package com.khit.library.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.khit.library.dto.NoticeBoardDTO;
import com.khit.library.entity.NoticeBoard;
import com.khit.library.repository.NoticeBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeBoardService {

	private final NoticeBoardRepository noticeBoardRepository;

	public void save(NoticeBoard noticeBoard, MultipartFile noticeBoardFile) throws Exception, IOException {
		if(!noticeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String noticeFilename = uuid + "_" + noticeBoardFile.getOriginalFilename();
            
            //String noticeFilepath ="C:/springfiles/" + noticeFilename;
            String noticeFilepath ="/Users/Healer/springfiles/" + noticeFilename; //희린 전용

            File savednoticeFile = new File(noticeFilepath); //실제 저장된 파일
            noticeBoardFile.transferTo(savednoticeFile);

            noticeBoard.setNoticeFilename(noticeFilename);
            noticeBoard.setNoticeFilepath(noticeFilepath); //파일 경로 설정
		}
		noticeBoardRepository.save(noticeBoard);
	}

	public NoticeBoardDTO findById(Long nbid) {
		Optional<NoticeBoard> findNoticeBoard = noticeBoardRepository.findById(nbid);
		NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.toSaveDTO(findNoticeBoard.get());
		return noticeBoardDTO;
	}

	public NoticeBoardDTO update(NoticeBoardDTO noticeBoardDTO, MultipartFile noticeBoardFile) throws Exception, IOException {
		if(!noticeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String noticeFilename = uuid + "_" + noticeBoardFile.getOriginalFilename();
            //String noticeFilepath ="C:/springfiles/" + noticeFilename;
            String noticeFilepath ="Users/healer/springfiles/" + noticeFilename; //희린 전

            File savednoticeFile = new File(noticeFilepath); //실제 저장된 파일
            noticeBoardFile.transferTo(savednoticeFile);

			noticeBoardDTO.setNoticeFilename(noticeFilename);
			noticeBoardDTO.setNoticeFilepath(noticeFilepath);
		}else {
			noticeBoardDTO.setNoticeFilename(findById(noticeBoardDTO.getNbid()).getNoticeFilename());
			noticeBoardDTO.setNoticeFilepath(findById(noticeBoardDTO.getNbid()).getNoticeFilepath());
		}
		NoticeBoard noticeBoard = NoticeBoard.toUpdateEntity(noticeBoardDTO);
		noticeBoardRepository.save(noticeBoard);
		return findById(noticeBoardDTO.getNbid());
	}

	public void deleteById(Long nbid) {
		noticeBoardRepository.deleteById(nbid);
		
	}
	
	public List<NoticeBoardDTO> findAll() {
		List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "nbid"));
		List<NoticeBoardDTO> noticeBoardDTOList = new ArrayList<>();
		
		for(NoticeBoard noticeBoard : noticeBoardList) {
			NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.toSaveDTO(noticeBoard);
			noticeBoardDTOList.add(noticeBoardDTO);
		}
		
		return noticeBoardDTOList;
	}

	//페이징
	public Page<NoticeBoardDTO> paging(Pageable pageable) {
        Pageable reversePageable = PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "nbid"));
        Page<NoticeBoard> noticeBoardPage = noticeBoardRepository.findAll(pageable);
        return noticeBoardPage.map(noticeBoard -> NoticeBoardDTO.toSaveDTO(noticeBoard));
    }

	//조회수
	@Transactional
	public void updateHits(Long nbid) {
		noticeBoardRepository.updateHits(nbid);
	}

    public List<NoticeBoard> mainList(){
        return noticeBoardRepository.findTop5ByOrderByCreatedDateDesc();
    }

}