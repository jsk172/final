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

import com.khit.library.dto.FreeBoardDTO;
import com.khit.library.dto.NoticeBoardDTO;
import com.khit.library.entity.FreeBoard;
import com.khit.library.entity.FreeBoard;
import com.khit.library.entity.NoticeBoard;
import com.khit.library.repository.FreeBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeBoardService {
	private final FreeBoardRepository freeBoardRepository;

	public void save(FreeBoard freeBoard, MultipartFile freeBoardFile) throws Exception, IOException {
		
		if(!freeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String freeFilename = uuid + "_" + freeBoardFile.getOriginalFilename();

            String freeFilepath ="C:/projectfiles/" + freeFilename;
            /*String freeFilepath ="/Users/Healer/springfiles/" + freeFilename; //희린 전용*/

            File savedFreeFile = new File(freeFilepath); //실제 저장된 파일
            freeBoardFile.transferTo(savedFreeFile);

            freeBoard.setFreeFilename(freeFilename);
            freeBoard.setFreeFilepath(freeFilepath); //파일 경로 설정
		}
		
		freeBoardRepository.save(freeBoard);
	}

	public List<FreeBoardDTO> findAll() {
		List<FreeBoard> freeBoardList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "fbid"));
		List<FreeBoardDTO> freeBoardDTOList = new ArrayList<>();

		for (FreeBoard freeBoard : freeBoardList) {
			FreeBoardDTO freeBoardDTO = FreeBoardDTO.toSaveDTO(freeBoard);
			freeBoardDTOList.add(freeBoardDTO);
		}

		return freeBoardDTOList;
	}

	public FreeBoardDTO findById(Long fbid) {
		Optional<FreeBoard> findFreeBoard = freeBoardRepository.findById(fbid);
		FreeBoardDTO freeBoardDTO = FreeBoardDTO.toSaveDTO(findFreeBoard.get());
		return freeBoardDTO;
	}

	public void deleteById(Long fbid) {
		freeBoardRepository.deleteById(fbid);
	}

	public FreeBoardDTO update(FreeBoardDTO freeBoardDTO, MultipartFile freeBoardFile) throws Exception, IOException {
		if(!freeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String freeFilename = uuid + "_" + freeBoardFile.getOriginalFilename();
            String freeFilepath ="C:/projectfiles/" + freeFilename;

            File savedFreeFile = new File(freeFilepath); //실제 저장된 파일
            freeBoardFile.transferTo(savedFreeFile);

			freeBoardDTO.setFreeFilename(freeFilename);
			freeBoardDTO.setFreeFilepath(freeFilepath);
		}else {
			freeBoardDTO.setFreeFilename(findById(freeBoardDTO.getFbid()).getFreeFilename());
			freeBoardDTO.setFreeFilepath(findById(freeBoardDTO.getFbid()).getFreeFilepath());
		}
		FreeBoard freeBoard = FreeBoard.toUpdateEntity(freeBoardDTO);
		freeBoardRepository.save(freeBoard);
		return findById(freeBoardDTO.getFbid());
		
	}
	
	public Page<FreeBoardDTO> searchByTitle(String keyword, Pageable pageable) {
	    return freeBoardRepository.findByFbtitleContaining(keyword, pageable)
	            .map(freeBoard -> FreeBoardDTO.toSaveDTO(freeBoard));
	}

	public Page<FreeBoardDTO> searchByContent(String keyword, Pageable pageable) {
	    return freeBoardRepository.findByFbcontentContaining(keyword, pageable)
	            .map(freeBoard -> FreeBoardDTO.toSaveDTO(freeBoard));
	}

	
	
	
	@Transactional
	public void updateHits(Long fbid) {
		freeBoardRepository.updateHits(fbid);
	}

	//페이징
	public Page<FreeBoardDTO> paging(Pageable pageable) {
	    Pageable reversePageable = PageRequest.of(
	            pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "fbid"));
        Page<FreeBoard> freeBoardPage = freeBoardRepository.findAll(reversePageable);
        return freeBoardPage.map(freeBoard -> FreeBoardDTO.toSaveDTO(freeBoard));
    }

    public List<FreeBoard> mainList() {
        return freeBoardRepository.findTop5ByOrderByCreatedDateDesc();
    }
}

