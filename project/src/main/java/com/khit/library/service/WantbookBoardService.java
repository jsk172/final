package com.khit.library.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.khit.library.dto.WantbookBoardDTO;
import com.khit.library.entity.WantbookBoard;
import com.khit.library.repository.WantbookBoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WantbookBoardService {
	private final WantbookBoardRepository wantbookBoardRepository;

	public void save(WantbookBoard wantbookBoard)//MultipartFile wantbookBoardFile)
					throws Exception, IOException {
		wantbookBoardRepository.save(wantbookBoard);
	}

	public List<WantbookBoardDTO> findAll() {
		List<WantbookBoard> wantbookBoardList = wantbookBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "wbid"));
		List<WantbookBoardDTO> wantbookBoardDTOList = new ArrayList<>();

		for(WantbookBoard wantbookBoard : wantbookBoardList) {
			WantbookBoardDTO wantbookBoardDTO = WantbookBoardDTO.toSaveDTO(wantbookBoard);
			wantbookBoardDTOList.add(wantbookBoardDTO);
		}

		return wantbookBoardDTOList;
	}

	public WantbookBoardDTO findById(Long wbid) {
		Optional<WantbookBoard> findwantbookBoard = wantbookBoardRepository.findById(wbid);
		WantbookBoardDTO wantbookBoardDTO = WantbookBoardDTO.toSaveDTO(findwantbookBoard.get());
		return wantbookBoardDTO;
	}

	public void deleteById(Long wbid) {
		wantbookBoardRepository.deleteById(wbid);
	}
}