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
import com.khit.library.dto.HopeBoardDTO;
import com.khit.library.entity.HopeBoard;
import com.khit.library.repository.HopeBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HopeBoardService {
	private final HopeBoardRepository hopeBoardRepository;

	public void save(HopeBoard hopeBoard, MultipartFile hopeBoardFile) throws Exception, IOException {

		if(!hopeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String hopeFilename = uuid + "_" + hopeBoardFile.getOriginalFilename();

            String hopeFilepath ="C:/projectfiles/" + hopeFilename;
            /*String hopeFilepath ="/Users/Healer/springfiles/" + hopeFilename; //희린 전용*/

            File savedHopeFile = new File(hopeFilepath); //실제 저장된 파일
            hopeBoardFile.transferTo(savedHopeFile);

            hopeBoard.setHopeFilename(hopeFilename);
            hopeBoard.setHopeFilepath(hopeFilepath); //파일 경로 설정
		}

		hopeBoardRepository.save(hopeBoard);
	}

	public List<HopeBoardDTO> findAll() {
		List<HopeBoard> hopeBoardList = hopeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "hbid"));
		List<HopeBoardDTO> hopeBoardDTOList = new ArrayList<>();

		for(HopeBoard hopeBoard : hopeBoardList) {
			HopeBoardDTO hopeBoardDTO = HopeBoardDTO.toSaveDTO(hopeBoard);
			hopeBoardDTOList.add(hopeBoardDTO);
		}

		return hopeBoardDTOList;
	}

	public HopeBoardDTO findById(Long hbid) {
		Optional<HopeBoard> findHopeBoard = hopeBoardRepository.findById(hbid);
		HopeBoardDTO hopeBoardDTO = HopeBoardDTO.toSaveDTO(findHopeBoard.get());
		return hopeBoardDTO;
	}

	public void deleteById(Long hbid) {
		hopeBoardRepository.deleteById(hbid);
	}

	public HopeBoardDTO update(HopeBoardDTO hopeBoardDTO, MultipartFile hopeBoardFile) throws Exception, IOException {
		if(!hopeBoardFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String hopeFilename = uuid + "_" + hopeBoardFile.getOriginalFilename();
            String hopeFilepath ="C:/projectfiles/" + hopeFilename;

            File savedHopeFile = new File(hopeFilepath); //실제 저장된 파일
            hopeBoardFile.transferTo(savedHopeFile);

			hopeBoardDTO.setHopeFilename(hopeFilename);
			hopeBoardDTO.setHopeFilepath(hopeFilepath);
		}else {
			hopeBoardDTO.setHopeFilename(findById(hopeBoardDTO.getHbid()).getHopeFilename());
			hopeBoardDTO.setHopeFilepath(findById(hopeBoardDTO.getHbid()).getHopeFilepath());
		}
		HopeBoard hopeBoard = HopeBoard.toUpdateEntity(hopeBoardDTO);
		hopeBoardRepository.save(hopeBoard);
		return findById(hopeBoardDTO.getHbid());
	}

	public Page<HopeBoardDTO> searchByTitle(String keyword, Pageable pageable) {
	    return hopeBoardRepository.findByHbtitleContaining(keyword, pageable)
	            .map(hopeBoard -> HopeBoardDTO.toSaveDTO(hopeBoard));
	}

	public Page<HopeBoardDTO> searchByContent(String keyword, Pageable pageable) {
	    return hopeBoardRepository.findByHbcontentContaining(keyword, pageable)
	            .map(hopeBoard -> HopeBoardDTO.toSaveDTO(hopeBoard));
	}
	
	

	@Transactional
	public void updateHits(Long hbid) {
		hopeBoardRepository.updateHits(hbid);
	}

	//페이징
	public Page<HopeBoardDTO> paging(Pageable pageable) {
	    Pageable reversePageable = PageRequest.of(
	            pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "hbid"));
	        Page<HopeBoard> hopeBoardPage = hopeBoardRepository.findAll(reversePageable);
        return hopeBoardPage.map(hopeBoard -> HopeBoardDTO.toSaveDTO(hopeBoard));
    }

}